import coders.Hamming7And4BitCoder
import errors.Hamming7And4BitErrorEmulator
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.experimental.and

object Main {
    private val inputFile = Path.of("send.txt")
    private val encodedFile = Path.of("encoded.txt")
    private val outputFile = Path.of("received.txt")
    private val decodedFile = Path.of("decoded.txt")

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val consoleScanner = Scanner(System.`in`)
        print("Write a mode: ")
        when (consoleScanner.nextLine()) {
            "encode" -> {
                println(inputFile.fileName + ":")
                val inputBytes = Files.readAllBytes(inputFile)
                println("txt view: " + String(inputBytes))
                println("hex view: " + toHexString(inputBytes))
                println("bin view: " + toBinString(inputBytes))
                val encoder = Hamming7And4BitCoder()
                val encodedBytes: ByteArray = encoder.encode(inputBytes)
                Files.write(encodedFile, encodedBytes)
                println()
                println(encodedFile.fileName + ":")
                println("bin view: " + toBinString(encodedBytes))
                println("hex view: " + toHexString(encodedBytes))
            }
            "send" -> {
                println(encodedFile.fileName + ":")
                val encodedBytes = Files.readAllBytes(encodedFile)
                println("hex view: " + toHexString(encodedBytes))
                println("bin view: " + toBinString(encodedBytes))
                val errorEmulator = Hamming7And4BitErrorEmulator()
                val distortedBytes: ByteArray = errorEmulator.distort(encodedBytes)
                Files.write(outputFile, distortedBytes)
                println()
                println(outputFile.fileName + ":")
                println("bin view: " + toBinString(distortedBytes))
                println("hex view: " + toHexString(distortedBytes))
            }
            "decode" -> {
                println(outputFile.fileName + ":")
                val distortedBytes = Files.readAllBytes(outputFile)
                println("hex view: " + toHexString(distortedBytes))
                println("bin view: " + toBinString(distortedBytes))

                // fix errors
                val errorEmulator = Hamming7And4BitErrorEmulator()
                val restoredBytes: ByteArray = errorEmulator.restore(distortedBytes)
                // decode
                val encoder = Hamming7And4BitCoder()
                val decodedBytes: ByteArray = encoder.decode(restoredBytes)
                Files.write(decodedFile, decodedBytes)
                println()
                println(decodedFile.fileName + ":")
                println("correct:  " + toBinString(restoredBytes))
                println("decoded:  " + toBinString(decodedBytes))
                println("hex view: " + toHexString(decodedBytes))
                println("txt view: " + String(decodedBytes))
            }
        }
    }

    private fun toBinString(bytes: ByteArray): String {
        val result = arrayOfNulls<String>(bytes.size)
        for (i in bytes.indices) {
            result[i] = String
                .format("%8s", Integer.toBinaryString((bytes[i] and 0xFF.toByte()).toInt()))
                .replace(' ', '0')
        }
        return result.joinToString(" ")
    }

    private fun toHexString(bytes: ByteArray): String {
        val result = arrayOfNulls<String>(bytes.size)
        for (i in bytes.indices) {
            result[i] = String.format("%02x", bytes[i]).toUpperCase()
        }
        return result.joinToString(" ")
    }
}