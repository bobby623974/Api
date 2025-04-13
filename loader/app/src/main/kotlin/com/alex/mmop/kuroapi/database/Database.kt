package com.alex.mmop.kuroapi.database


import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Database(
    val status: Boolean?,
    val data: Data?,
    val reason : String?
)