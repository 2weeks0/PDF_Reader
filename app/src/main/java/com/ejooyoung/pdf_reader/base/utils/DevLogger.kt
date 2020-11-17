package com.ejooyoung.pdf_reader.base.utils

import android.util.Log

object DevLogger {

    private const val TAG = "devLog"
    private const val NAME_BASE_PACKAGE = "com.ejooyoung.pdf_reader"

    fun i(message: String) {
        Log.i(TAG, "${getPrefixOfMessage()} $message")
    }

    fun d(message: String) {
        Log.d(TAG, "${getPrefixOfMessage()} $message")
    }

    fun w(message: String) {
        Log.w(TAG, "${getPrefixOfMessage()} $message")
    }

    fun e(message: String) {
        Log.e(TAG, "${getPrefixOfMessage()} $message")
    }

    fun i() {
        i("")
    }

    fun d() {
        d("")
    }

    fun w() {
        w("")
    }

    fun e() {
        e("")
    }

    fun i(format: String, vararg args: Any?) {
        i(String.format(format, *args))
    }

    fun d(format: String, vararg args: Any?) {
        d(String.format(format, *args))
    }

    fun w(format: String, vararg args: Any?) {
        w(String.format(format, *args))
    }

    fun e(format: String, vararg args: Any?) {
        e(String.format(format, *args))
    }

    private fun getPrefixOfMessage(): String? {
        return Thread.currentThread().stackTrace
            .find {
                it.className.contains(NAME_BASE_PACKAGE) &&
                        !it.className.contains(this.javaClass.name)
            }
            ?.let {
                "[(${it.fileName}:${it.lineNumber}) ${it.methodName}]"
            }
    }
}