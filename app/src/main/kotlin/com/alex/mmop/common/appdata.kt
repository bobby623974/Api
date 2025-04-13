package com.alex.mmop.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * @author alex5402
 * Created 9/28/24 at 8:53 PM
 * appdata
 */
class appdata : ViewModel() {
    private val _keydata = MutableStateFlow("")
    private val _uerldata = MutableStateFlow("")
    val key: StateFlow<String> = _keydata.asStateFlow()
    val url: StateFlow<String> = _uerldata.asStateFlow()

    fun setKey(key: String) {
        _keydata.value = key
    }

    fun setUrl(key: String) {
        _uerldata.value = key
    }



}