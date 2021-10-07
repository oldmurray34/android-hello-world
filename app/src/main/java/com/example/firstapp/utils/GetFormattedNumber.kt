//package com.example.firstapp.utils
//
//fun getFormattedNumber(number: Int): String {
//    val numberChars = number.toString().toCharArray().toCollection(ArrayList())
//    val result = when (number) {
//        in 1000..9999 -> "${numberChars[0]}.${numberChars[1]}K"
//        in 10000..99999 -> "${numberChars[0]}${numberChars[1]}K"
//        in 100000..999999 -> "${numberChars[0]}${numberChars[1]}${numberChars[2]}K"
//        in 1000000..1099999 -> "${numberChars[0]}M"
//        in 1100000..9999999 -> "${numberChars[0]}.${numberChars[1]}M"
//        in 10000000..99999999 -> "${numberChars[0]}${numberChars[1]}.${numberChars[2]}M"
//        else -> number.toString()
//    }
//    return result
//}