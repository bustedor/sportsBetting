package com.example.sportsbetting.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<out V> {

    abstract operator fun component1(): V?
    abstract operator fun component2(): String?

    class Success<out V>(val value: V) : Result<V>() {

        override fun component1(): V = value
        override fun component2(): Nothing? = null
    }

    class Error(val message: String) : Result<Nothing>() {

        override fun component1(): Nothing? = null
        override fun component2(): String = message

    }

    companion object {
        inline fun <V> Result<V>.onSuccess(block: (value: V) -> Unit): Result<V> {
            if (this is Success<V>) {
                block(this.value)
            }
            return this
        }

        inline fun <V> Result<V>.onError(block: (value: String) -> Unit): Result<V> {
            if (this is Error) {
                block(this.message)
            }
            return this
        }

        @OptIn(ExperimentalContracts::class)
        fun <V> Result<V>.isSuccess(): Boolean {
            contract {
                returns(true) implies (this@isSuccess is Success<V>)
                returns(false) implies (this@isSuccess is Error)
            }
            return this is Success
        }
    }
}
