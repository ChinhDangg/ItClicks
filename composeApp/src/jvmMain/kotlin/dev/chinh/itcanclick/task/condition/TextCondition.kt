package dev.chinh.itcanclick.task.condition

import org.bytedeco.javacpp.BytePointer
import org.bytedeco.leptonica.global.leptonica.pixDestroy
import org.bytedeco.leptonica.global.leptonica.pixReadMem
import org.bytedeco.tesseract.TessBaseAPI
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO
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

    private fun levenshteinDistance(s1: String, s2: String): Int {
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


    private val api = TessBaseAPI().also { api ->
        if (api.Init("tessdata/", "eng") != 0) {
            api.close()
            error("Failed to initialize Tesseract for language eng. " +
                    "Make sure tessdata is available.")
        }
    }

    fun extractText(image: BufferedImage): String {
        // 1. Encode BufferedImage → PNG bytes in memory
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "png", baos)
        val imageBytes = baos.toByteArray()

        // 2. Decode bytes → Leptonica PIX (no disk I/O)
        val byteBuffer = ByteBuffer.wrap(imageBytes)
        val pix = pixReadMem(byteBuffer, imageBytes.size.toLong())
            ?: error("Leptonica could not decode the image.")

        return try {
            // 3. Hand PIX directly to Tesseract
            api.SetImage(pix)
            api.SetSourceResolution(300) // hint: 300 DPI improves accuracy

            // 4. Run recognition and read result
            val outText: BytePointer = api.GetUTF8Text() ?: return ""

            outText.getString(Charsets.UTF_8).trim().also {
                outText.deallocate()
            }
        } finally {
            // 5. Free the PIX — Tesseract made its own copy so safe to free now
            pixDestroy(pix)
            api.Clear() // reset internal state for next call, keeps Init() alive
        }
    }

    override fun close() {
        api.End()
        api.close()
    }
}