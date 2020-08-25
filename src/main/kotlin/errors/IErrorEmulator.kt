package errors

interface IErrorEmulator<T> {
    fun distort(message: T): T
    fun restore(message: T): T
}