package coders

import BitUtils
import kotlin.math.ceil

class DoubleBitCoder : ICoder<ByteArray> {
    override fun encode(input: ByteArray): ByteArray {
        val encodedBytes = ByteArray(
            ceil(input.size * 8 / 3f.toDouble()).toInt()
        )
        for (i in encodedBytes.indices) {
            var parity = 0
            // write data bits
            for (j in 0..2) {
                val totalBitIdx = i * 3 + j
                val bitIdx = totalBitIdx % 8
                val byteIdx = totalBitIdx / 8
                val curBit = if (input.size <= byteIdx) 0 else BitUtils.getBit(input[byteIdx], bitIdx)
                parity = parity xor curBit
                encodedBytes[i] = BitUtils.setBit(encodedBytes[i], curBit, j * 2)
                encodedBytes[i] = BitUtils.setBit(encodedBytes[i], curBit, j * 2 + 1)
            }
            // write parity bits
            encodedBytes[i] = BitUtils.setBit(encodedBytes[i], parity, BitUtils.MAX_BIT_INDEX)
            encodedBytes[i] = BitUtils.setBit(encodedBytes[i], parity, BitUtils.MAX_BIT_INDEX - 1)
        }
        return encodedBytes
    }

    override fun decode(input: ByteArray): ByteArray {
        val decoded = ByteArray((input.size / 8f * 3).toInt())
        var currentByte = 0
        val curBits = IntArray(8)
        var curBitIdx = 0
        for (b in input) {
            var j = 0
            while (j < 6) {
                curBits[curBitIdx++] = BitUtils.getBit(b, j)
                if (curBitIdx == 8) {
                    decoded[currentByte++] = BitUtils.byteFromBits(curBits)
                    curBitIdx = 0
                }
                j += 2
            }
        }
        return decoded
    }
}