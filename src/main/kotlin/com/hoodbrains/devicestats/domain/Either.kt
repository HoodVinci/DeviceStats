package com.hoodbrains.devicestats.domain


sealed class Either<out E, out V> {
    data class Error<out E>(val error: E) : Either<E, Nothing>()
    data class Value<out V>(val value: V) : Either<Nothing, V>()
}

fun <V> value(value: V): Either<Nothing, V> = Either.Value(value)
fun <E> error(value: E): Either<E, Nothing> = Either.Error(value)

fun <V> either(action: () -> V): Either<Exception, V> =
    try {
        value(action())
    } catch (e: Exception) {
        error(e)
    }

inline fun <E, V, A> Either<E, V>.fold(onError: (E) -> A, onSuccess: (V) -> A): A = when (this) {
    is Either.Error -> onError(this.error)
    is Either.Value -> onSuccess(this.value)
}

inline infix fun <E, V, V2> Either<E, V>.chain(f: (V) -> Either<E, V2>): Either<E, V2> =
    when (this) {
        is Either.Error -> this
        is Either.Value -> f(value)
    }
