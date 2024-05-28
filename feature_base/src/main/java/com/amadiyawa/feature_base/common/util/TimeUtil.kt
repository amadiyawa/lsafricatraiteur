package com.amadiyawa.feature_base.common.util

import java.util.Calendar

class TimeUtil {
    companion object {
        private fun getCurrentWeek(): Pair<Long, Long> {
            val current = System.currentTimeMillis()
            val currentDay = current / 86400000
            val currentWeek = currentDay / 7
            val start = currentWeek * 7
            val end = start + 6
            return Pair(start * 86400000, end * 86400000)
        }

        private fun getCurrentMonth(): Pair<Long, Long> {
            val calendar = Calendar.getInstance()

            // Set the calendar to the start of the current month
            calendar[Calendar.DAY_OF_MONTH] = 1
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val start = calendar.timeInMillis

            // Set the calendar to the end of the current month
            calendar.add(Calendar.MONTH, 1)
            calendar.add(Calendar.MILLISECOND, -1)
            val end = calendar.timeInMillis

            return Pair(start, end)
        }

        private fun getStartAndEndOfCurrentYear(): Pair<Long, Long> {
            val calendar = Calendar.getInstance()

            // Set the calendar to the start of the current year
            calendar[Calendar.DAY_OF_YEAR] = 1
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val start = calendar.timeInMillis

            // Set the calendar to the end of the current year
            calendar.add(Calendar.YEAR, 1)
            calendar.add(Calendar.MILLISECOND, -1)
            val end = calendar.timeInMillis

            return Pair(start, end)
        }

        fun isDateInCurrentWeek(date: Long): Boolean {
            val currentWeek = getCurrentWeek()
            return date in currentWeek.first..currentWeek.second
        }

        fun isDateInCurrentMonth(date: Long): Boolean {
            val currentMonth = getCurrentMonth()
            return date in currentMonth.first..currentMonth.second
        }

        fun isDateInCurrentYear(date: Long): Boolean {
            val currentYear = getStartAndEndOfCurrentYear()
            return date in currentYear.first..currentYear.second
        }

        fun isDateInCustomDate(tokenDataDate: Long, customDate: Long): Boolean {
            val customDateCalendar = Calendar.getInstance()
            customDateCalendar.timeInMillis = customDate
            customDateCalendar[Calendar.HOUR_OF_DAY] = 0
            customDateCalendar[Calendar.MINUTE] = 0
            customDateCalendar[Calendar.SECOND] = 0
            customDateCalendar[Calendar.MILLISECOND] = 0
            val customDateStart = customDateCalendar.timeInMillis

            customDateCalendar.add(Calendar.DAY_OF_MONTH, 1)
            customDateCalendar.add(Calendar.MILLISECOND, -1)
            val customDateEnd = customDateCalendar.timeInMillis

            return tokenDataDate in customDateStart..customDateEnd
        }

        fun isDateInDateRange(tokenDataDate: Long, startDate: Long, endDate: Long): Boolean {
            return tokenDataDate in startDate..endDate
        }

        fun getCurrentYear() =  Calendar.getInstance()[Calendar.YEAR]
    }
}
