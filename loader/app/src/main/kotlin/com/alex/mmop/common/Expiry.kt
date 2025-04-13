package com.alex.mmop.common

/**
 * @author alex5402
 * Created 9/28/24 at 8:52 PM
 * Expiry
 */
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * @author alex5402
 * Created 9/21/24 at 5:53 AM
 * ExpiryTime
 */

class ExpiryTime : ViewModel() {

    private val _daysLeft = MutableStateFlow(0L)
    private val _hoursLeft = MutableStateFlow(0L)
    private val _minutesLeft = MutableStateFlow(0L)
    private val _secondsLeft = MutableStateFlow(0L)

    val daysLeft: StateFlow<Long> = _daysLeft
    val hoursLeft: StateFlow<Long> = _hoursLeft
    val minutesLeft: StateFlow<Long> = _minutesLeft
    val secondsLeft: StateFlow<Long> = _secondsLeft

    private lateinit var expiryTime: LocalDateTime


    fun setExpiryTimeAndStartTimer(expiryTimeString: String) {
        expiryTime = LocalDateTime.parse(expiryTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        startTimer()
    }

    // Start the timer to calculate the remaining time
    private fun startTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val now = LocalDateTime.now()
                if (now.isBefore(expiryTime)) {
                    val days = ChronoUnit.DAYS.between(now, expiryTime)
                    val hours = ChronoUnit.HOURS.between(now, expiryTime) % 24
                    val minutes = ChronoUnit.MINUTES.between(now, expiryTime) % 60
                    val seconds = ChronoUnit.SECONDS.between(now, expiryTime) % 60

                    _daysLeft.value = days
                    _hoursLeft.value = hours
                    _minutesLeft.value = minutes
                    _secondsLeft.value = seconds
                } else {
                    _daysLeft.value = 0
                    _hoursLeft.value = 0
                    _minutesLeft.value = 0
                    _secondsLeft.value = 0
                    break
                }
                delay(1000L) // Update every second
            }
        }
    }
}
