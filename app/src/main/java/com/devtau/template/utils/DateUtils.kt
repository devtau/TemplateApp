package com.devtau.template.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Date formatters, converters and comparators
 */
object DateUtils {

    private const val DATE_PATTERN = "HH:mm dd.MM.yyy"

    /**
     * Compares date with now minus 24 hours
     * @param dateMs milliseconds, representing date to compare
     * @return true if diff is greater than 24 hors, false otherwise
     */
    fun isDateBeforeYesterday(dateMs: Long): Boolean {
        val targetDate = Calendar.getInstance()
        targetDate.timeInMillis = dateMs
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        val result = targetDate.before(yesterday)
        Timber.d("isDateBeforeYesterday. date=${targetDate.format()}, yesterday=${yesterday.format()}, isBefore=$result")
        return result
    }

    private fun Calendar.format(): String {
        val format = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        return format.format(this.time)
    }
}