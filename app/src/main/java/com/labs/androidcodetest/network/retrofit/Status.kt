package com.labs.androidcodetest.network.retrofit

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, message=$message)"
    }

}
