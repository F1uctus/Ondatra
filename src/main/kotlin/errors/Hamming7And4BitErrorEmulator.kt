package errors

import BitUtils

class Hamming7And4BitErrorEmulator : IErrorEmulator<ByteArray> {
    override fun distort(message: ByteArray): ByteArray {
        return BitUtils.randomDataDistort(message)
    }

    override fun restore(message: ByteArray): ByteArray {
        val outBytes = message.clone()
        for (i in outBytes.indices) {
            val bitIndex: Int =
                ((BitUtils.getBit(outBytes[i], 0) xor BitUtils.getBit(outBytes[i], 2) xor BitUtils.getBit(
                    outBytes[i], 4
                ) xor BitUtils.getBit(outBytes[i], 6))
                        + (BitUtils.getBit(outBytes[i], 1) xor BitUtils.getBit(outBytes[i], 2) xor BitUtils.getBit(
                    outBytes[i],
                    5
                ) xor BitUtils.getBit(
                    outBytes[i], 6
                )) * 2 + (BitUtils.getBit(outBytes[i], 3) xor BitUtils.getBit(outBytes[i], 4) xor BitUtils.getBit(
                    outBytes[i], 5
                ) xor BitUtils.getBit(outBytes[i], 6)) * 4) - 1
            outBytes[i] = BitUtils.flipBit(outBytes[i], bitIndex)
        }
        return outBytes
    }
}