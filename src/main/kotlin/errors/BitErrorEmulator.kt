package errors

import BitUtils

class BitErrorEmulator : IErrorEmulator<ByteArray> {
    override fun distort(message: ByteArray): ByteArray {
        return BitUtils.randomDataDistort(message)
    }

    override fun restore(message: ByteArray): ByteArray {
        val outBytes = message.clone()
        for (i in outBytes.indices) {
            var errorIndex = -1
            var correctBit = 0
            var j = 0
            while (j < 6) {
                if (BitUtils.getBit(outBytes[i], j) xor BitUtils.getBit(outBytes[i], j + 1) == 1) {
                    errorIndex = j / 2
                } else {
                    correctBit += BitUtils.getBit(outBytes[i], j)
                }
                j += 2
            }
            if (errorIndex != -1) {
                val parityBit: Int = BitUtils.getBit(outBytes[i], BitUtils.MAX_BIT_INDEX)
                correctBit = correctBit % 2 xor parityBit
                outBytes[i] = BitUtils.setBit(outBytes[i], correctBit, errorIndex * 2)
                outBytes[i] = BitUtils.setBit(outBytes[i], correctBit, errorIndex * 2 + 1)
            } else {
                val parityBit = correctBit % 2
                outBytes[i] = BitUtils.setBit(outBytes[i], parityBit, BitUtils.MAX_BIT_INDEX - 1)
                outBytes[i] = BitUtils.setBit(outBytes[i], parityBit, BitUtils.MAX_BIT_INDEX)
            }
        }
        return outBytes
    }
}