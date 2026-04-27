package dev.chinh.itcanclick.task.condition

import ai.djl.Application
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.DetectedObjects
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ZooModel
import jakarta.annotation.PreDestroy
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import kotlin.math.max

class TextCondition : Condition, AutoCloseable {

    private val robot : Robot

    constructor(robot: Robot) {
        this.robot = robot
    }

    override fun check(conditionInfo: ConditionInfo): Condition.Result {
        if (conditionInfo.conditionType != ConditionType.TEXT) {
            throw IllegalArgumentException("Condition type must be TEXT")
        }

        val similarity = checkInRect(conditionInfo.rect, conditionInfo.originalImage)
        val passed = similarity >= conditionInfo.similarity
        val conditionResult = if (passed) ConditionResult.PASS else {
            if (conditionInfo.isCore)
                ConditionResult.SKIPPABLE
            ConditionResult.FAIL
        }

        return Condition.Result(conditionResult, similarity, conditionInfo.rect)
    }

    fun checkInRect(rect: Rectangle, sourceImage: BufferedImage) : Double {
        val targetImage = captureCurrentScreen(robot, rect)

        val originalText = extractText(sourceImage)
        val targetText = extractText(targetImage)
        return getSimilarity(originalText, targetText)
    }

    fun getSimilarity(s1: String, s2: String): Double {
        // Normalize both strings
        val norm1 = s1.normalizeSpaces()
        val norm2 = s2.normalizeSpaces()

        // Handle edge cases
        if (norm1 == norm2) return 1.0
        if (norm1.isEmpty() || norm2.isEmpty()) return 0.0

        // Calculate Levenshtein distance
        val distance = levenshteinDistance(norm1, norm2)

        // Normalize the score between 0.0 and 1.0
        val maxLength = max(norm1.length, norm2.length)
        return 1.0 - (distance.toDouble() / maxLength)
    }

    fun String.normalizeSpaces(): String {
        return this.trim().replace("\\s+".toRegex(), " ")
    }

    fun levenshteinDistance(s1: String, s2: String): Int {
        val m = s1.length
        val n = s2.length
        val dp = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) dp[i][0] = i
        for (j in 0..n) dp[0][j] = j

        for (i in 1..m) {
            for (j in 1..n) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,      // Deletion
                    dp[i][j - 1] + 1,      // Insertion
                    dp[i - 1][j - 1] + cost // Substitution
                )
            }
        }
        return dp[m][n]
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
    fun extractText(bufferedImage: BufferedImage): String {
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

        return extractedStrings.joinToString(" ")
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