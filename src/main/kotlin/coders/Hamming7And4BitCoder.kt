package coders

import BitUtils

class Hamming7And4BitCoder : ICoder<ByteArray> {
    override fun encode(input: ByteArray): ByteArray {
        val encodedBytes = ByteArray(input.size * 2)
        for (i in encodedBytes.indices) {
            val dataBits = IntArray(4)
            val byteIdx = i / 2
            for (j in 0..3) {
                val idxBit = i % 2 * 4 + j
                dataBits[j] = BitUtils.getBit(input[byteIdx], idxBit)
            }
            encodedBytes[i] = BitUtils.byteFromBits(
                intArrayOf(
                    dataBits[0] xor dataBits[1] xor dataBits[3],  // parity
                    dataBits[0] xor dataBits[2] xor dataBits[3],  // parity
                    dataBits[0],
                    dataBits[1] xor dataBits[2] xor dataBits[3],  // parity
                    dataBits[1],
                    dataBits[2],
                    dataBits[3],
                    0
                )
            )
        }
        return encodedBytes
    }

    override fun decode(input: ByteArray): ByteArray {
        val decoded = ByteArray(input.size / 2)
        var i = 0
        while (i < input.size) {
            val bits = IntArray(8)
            for (j in 0..1) {
                bits[j * 4] = BitUtils.getBit(input[i + j], 2)
                bits[j * 4 + 1] = BitUtils.getBit(input[i + j], 4)
                bits[j * 4 + 2] = BitUtils.getBit(input[i + j], 5)
                bits[j * 4 + 3] = BitUtils.getBit(input[i + j], 6)
            }
            decoded[i / 2] = BitUtils.byteFromBits(bits)
            i += 2
        }
        return decoded
    }
}