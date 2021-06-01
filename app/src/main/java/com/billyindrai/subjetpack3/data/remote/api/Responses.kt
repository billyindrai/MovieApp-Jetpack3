package com.billyindrai.subjetpack3.data.remote.api

class Responses<T> (val status: Status, val body: T, val message: String?) {
    companion object{
        fun <T> success(body: T): Responses<T> = Responses(Status.SUCCESS, body, null)
        fun <T> empty(msg: String, body: T): Responses<T> = Responses(Status.EMPTY, body, msg)
        fun <T> error(msg: String, body: T): Responses<T> = Responses(Status.ERROR, body, msg)
    }
}