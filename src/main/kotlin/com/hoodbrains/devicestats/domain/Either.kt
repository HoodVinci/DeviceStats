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

suspend fun <V> suspendEither(action: suspend () -> V): Either<Exception, V> =
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

inline fun <E, E1, V> Either<E, V>.mapError(f: (E) -> E1): Either<E1, V> = when (this) {
    is Either.Error -> Either.Error(f(this.error))
    is Either.Value -> this
}

inline fun <E, V, V1> Either<E, V>.mapValue(errorMapper: (Exception) -> E, f: (V) -> V1): Either<E, V1> = chain {
    when (this) {
        is Either.Error -> this
        is Either.Value -> try {
            value(f(value))
        } catch (e: Exception) {
            error(errorMapper(e))
        }
    }
}

inline fun <V, V1> Either<Exception, V>.mapValue(f: (V) -> V1): Either<Exception, V1> =
    mapValue(errorMapper = { it }, f)


fun <E, V> Either<E, V>.onErrorReturn(default: V): V = when (this) {
    is Either.Error -> default
    is Either.Value -> this.value
}

fun <E> Either<E, Unit>.ignoreErrors(): Unit = when (this) {
    is Either.Error -> Unit
    is Either.Value -> this.value
}
