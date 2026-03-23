package com.example.movieapp.core.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String?.toDateFormatted(): String {
    if (this.isNullOrEmpty()) return ""

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(this, inputFormatter)

    val outputFormatter = DateTimeFormatter
        .ofPattern("dd MMM yyyy")
        .withLocale(Locale.getDefault())

    return localDate.format(outputFormatter)
}