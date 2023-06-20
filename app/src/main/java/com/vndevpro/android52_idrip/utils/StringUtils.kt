package com.vndevpro.android52_idrip.utils

object StringUtils {

    @JvmStatic
    fun convertInt2String(input: Int) = input.toString()
    @JvmStatic
    fun convertDouble2String(input: Double) = input.toString()

    @JvmStatic
    fun convertInt2Dolar(input: Int) = "$$input"
}