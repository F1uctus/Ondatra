import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

object BitUtils {
    const val MAX_BIT_INDEX = 7

    fun randomDataDistort(bytes: ByteArray): ByteArray {
        val outBytes = bytes.clone()
        val rnd = Random()
        for (i in outBytes.indices) {
            // make mask with random flipped bit
            val mask = 1 shl rnd.nextInt(7)
            // apply mask
            outBytes[i] = outBytes[i] xor mask.toByte()
        }
        return outBytes
    }

    fun getBit(bits: Byte, index: Int): Int {
        return bits.toInt() ushr 7 - index and 1
    }

    fun setBit(bits: Byte, state: Int, index: Int): Byte {
        return if (state == 1) bits or ((1 shl MAX_BIT_INDEX - index).toByte())
        else bits and (1 shl MAX_BIT_INDEX - index).inv().toByte()
    }

    fun flipBit(bits: Byte, index: Int): Byte {
        return bits xor (1 shl MAX_BIT_INDEX - index).toByte()
    }

    fun byteFromBits(bits: IntArray): Byte {
        var result: Byte = 0
        for (i in 0..7) {
            result = if (bits[MAX_BIT_INDEX - i] == 1) result or ((1 shl i).toByte())
            else result and (1 shl i).inv().toByte()
        }
        return result
    }
}
