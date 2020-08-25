package errors

import kotlin.random.Random

class SymbolErrorEmulator : IErrorEmulator<String> {
    override fun distort(message: String): String {
        val sb = StringBuilder(message)
        var allowError = true
        for (i in sb.indices) {
            if (allowError) {
                val replacementIdx = (i + Random.Default.nextInt(3))
                    .coerceAtMost(sb.length - 1)
                var replacementChar = ' '
                do {
                    replacementChar = replacements.random()
                } while (replacementChar == sb[replacementIdx])
                sb.setCharAt(replacementIdx, replacementChar)
                allowError = false
                continue
            }
            if ((i + 1) % 3 == 0) {
                allowError = true
            }
        }
        return sb.toString()
    }

    override fun restore(message: String): String {
        // restore not needed
        return message
    }

    companion object {
        private const val replacements = """abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 """
    }
}