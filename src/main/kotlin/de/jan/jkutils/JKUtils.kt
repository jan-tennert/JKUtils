package de.jan.jkutils

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(pattern: String) : Date {
    val formatter = SimpleDateFormat(pattern)
    return formatter.parse(this)
}

fun Date.toFormattedString(pattern: String) : String {
    val formatter = SimpleDateFormat(pattern)
    return formatter.format(this)
}

fun Collection<String>.convertToString(separator: String = " ") : String {
    var string = ""
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        val next = iterator.next()
        string += next + if(iterator.hasNext()) separator else ""
    }
    return string
}

fun Array<String>.convertToString(separator: String = " ") : String {
    var string = ""
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        val next = iterator.next()
        string += next + if(iterator.hasNext()) separator else ""
    }
    return string
}