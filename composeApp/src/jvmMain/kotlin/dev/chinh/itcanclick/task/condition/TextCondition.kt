package dev.chinh.itcanclick.task.condition

import ai.djl.Application
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.DetectedObjects
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import jakarta.annotation.PreDestroy
import java.awt.image.BufferedImage

class TextCondition : Condition, AutoCloseable {

    override fun check(conditionInfo: ConditionInfo): Condition.Result {
        TODO("Not yet implemented")
    }

    @Volatile private var isDetectionLoaded = false

    // Configure the Detection Criteria
    // ZooModel is thread-safe, so it can be shared across all requests.
    private val detectionModel: ZooModel<Image, DetectedObjects> by lazy {
        val criteria = Criteria.builder()
            .optEngine("PaddlePaddle")
            .setTypes(Image::class.java, DetectedObjects::class.java)
            .optApplication(Application.CV.OBJECT_DETECTION)
            // Use "server" for maximum accuracy, or "mobile" for a faster, lighter model
            .optFilter("flavor", "server")
            .build()

        println("Loading PaddleOCR Detection Model into memory...")
        val model = criteria.loadModel()
        isDetectionLoaded = true
        model
    }

    @Volatile private var isRecognitionLoaded = false

    // Recognition Model (Reads the text inside the boxes)
    private val recognitionModel: ZooModel<Image, String> by lazy {
        val criteria = Criteria.builder()
            .optEngine("PaddlePaddle")
            .setTypes(Image::class.java, String::class.java)
            .optApplication(Application.CV.WORD_RECOGNITION)
            .optFilter("flavor", "server")
            .build()

        val model = criteria.loadModel()
        isRecognitionLoaded = true
        model
    }

    // 3. The Extraction Function
    fun extractText(bufferedImage: BufferedImage): List<String> {
        // Convert java.awt.image.BufferedImage to DJL's internal Image format
        val image: Image = ImageFactory.getInstance().fromImage(bufferedImage)
        val extractedStrings = mutableListOf<String>()

        // Open both predictors. They are lightweight and scoped to this request.
        detectionModel.newPredictor().use { detector ->
            recognitionModel.newPredictor().use { recognizer ->

                // Detect where the text is
                val detectedText: DetectedObjects = detector.predict(image)

                // Iterate through each found box
                for (item in detectedText.items<DetectedObjects.DetectedObject>()) {
                    val rect = item.boundingBox.bounds

                    // DJL bounding boxes are often returned as relative coordinates (0.0 to 1.0).
                    // must convert them to absolute pixels to crop the image.
                    val x = (rect.x * image.width).toInt()
                    val y = (rect.y * image.height).toInt()
                    val width = (rect.width * image.width).toInt()
                    val height = (rect.height * image.height).toInt()

                    // Ensure bounds don't bleed outside the image limits
                    val safeX = x.coerceAtLeast(0)
                    val safeY = y.coerceAtLeast(0)
                    val safeW = width.coerceAtMost(image.width - safeX)
                    val safeH = height.coerceAtMost(image.height - safeY)

                    if (safeW > 0 && safeH > 0) {
                        // Crop the original image to just the text box
                        val subImage = image.getSubImage(safeX, safeY, safeW, safeH)

                        // Recognize the text inside the cropped box
                        val text = recognizer.predict(subImage)

                        if (text.isNotBlank()) {
                            extractedStrings.add(text)
                        }
                    }
                }
            }
        }

        return extractedStrings
    }

    @PreDestroy
    override fun close() {
        if (isDetectionLoaded) {
            detectionModel.close()
        }
        if (isRecognitionLoaded) {
            recognitionModel.close()
        }
    }
}