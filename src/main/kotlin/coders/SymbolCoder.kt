package coders

class SymbolCoder : ICoder<String> {
    override fun encode(input: String): String {
        return input.map { c -> "$c$c$c" }.joinToString()
    }

    override fun decode(input: String): String {
        val sb = StringBuilder()
        var i = 0
        while (i < input.length) {
            val piece = input.substring(i, i + 3)
            if (piece[0] == piece[1]) { // aab
                sb.append(piece[0])
            } else if (piece[1] == piece[2]) { // abb
                sb.append(piece[1])
            } else { // aba
                sb.append(piece[2])
            }
            i += 3
        }
        return sb.toString()
    }
}