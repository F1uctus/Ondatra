package coders

interface ICoder<T> {
    fun encode(input: T): T
    fun decode(input: T): T
}