package com.alex.mmop.kuroapi.database

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Data(
    val Announcement: String?,
    val ExpiryDate: String?,
    val SLOT: String?,
    val appName: String?,
    val key: String?,
    val rng: Int?,
    val statusText: String?,
    val token: String?,
    val updateversion: String?,
    val updateapklink: String?,
    val updateinfo: String?,
    val updatetitle: String?,


    val EXP: String?,
    val Floating: String?,
    val credit: String?,
    val device: String?,
    val mod_status: String?,
    val modname: String?,
    val real: String?,
    val updated: String?,
    val version: String?,
    val AIM: String?,
    val BulletTrack: String?,
    val ESP: String?,
    val Item: String?,
    val Memory: String?,
    val Setting: String?,
    val SilentAim: String?,
    val bypass: String?,
)