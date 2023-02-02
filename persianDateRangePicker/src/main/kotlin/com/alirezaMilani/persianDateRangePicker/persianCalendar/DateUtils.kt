package com.alirezaMilani.persianDateRangePicker.persianCalendar

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * 判斷日期自喘是否為當天
     * @param dateString String
     * @return Boolean
     */

    fun isToday(dateString: String): Boolean {
        if (dateString.isNullOrEmpty()) {
            return false
        }
        val current = LocalDateTime.now()
        val formatted = current.format(formatter)
        return dateString.substring(0,10) == formatted
    }

    fun getTodayDate(): String {
        val current = LocalDateTime.now()
        return current.format(formatter)
    }

    /**
     * 獲取當前年份
     * @return Int
     */
    fun getCurrentYear(): Int {
        return LocalDateTime.now().year
    }

    /**
     * 獲取當前月份
     * @return Int
     */
    fun getCurrentMonth(): Int {
        return LocalDateTime.now().month.value
    }

    /**
     * 獲取當前日期是星期幾
     */
    fun getWeekOfDate(dt: Date): Int {
        return LocalDateTime.now().dayOfWeek.value
    }

    fun getWeekOfDate(dtStr: String): Int {
        val dt = LocalDate.parse(dtStr, formatter)
        return dt.dayOfWeek.value
    }

    fun getDayOfMonth(dtStr: String): Int {
        val dt = LocalDate.parse(dtStr, formatter)
        return dt.lengthOfMonth()
    }

    /**
     * 當月份為一位時返回兩位
     * @param month Int
     * @return String
     */
    fun getMonthValue(month: Int): String {
        return if (month < 10) "0$month"
        else month.toString()
    }

    /**
     * 判斷時間1是否早於時間2
     * @param time1Str String
     * @param time2Str String
     */
    fun compareTime(time1Str: String, time2Str: String): Boolean {
        val time1 = LocalDate.parse(time1Str, formatter)
        val time2 = LocalDate.parse(time2Str, formatter)
        return time1.isBefore(time2)
    }

    /**
     * 查詢時間是否在某個區間內
     * @param timeStr String       要查詢的時間
     * @param limit List<String>   區間
     * @return DatePosition
     */
    fun checkDataIsInTime(timeStr: String, limit: List<String>): DatePosition {
        when (limit.size) {
            1 -> {
                return if (timeStr == limit[0]) {
                    DatePosition.SINGLE_1
                } else {
                    DatePosition.OTHER
                }
            }
            2 -> {
                if (limit[0] == limit[1]) {
                    return if (timeStr == limit[0]) {
                        DatePosition.SINGLE_2
                    } else {
                        DatePosition.OTHER
                    }
                } else {
                    val time = LocalDate.parse(timeStr, formatter)
                    val start: LocalDate
                    val end: LocalDate
                    if (compareTime(limit[0], limit[1])) {
                        start = LocalDate.parse(limit[0], formatter)
                        end = LocalDate.parse(limit[1], formatter)
                    } else {
                        start = LocalDate.parse(limit[1], formatter)
                        end = LocalDate.parse(limit[0], formatter)
                    }
                    if (start.isBefore(time) && end.isAfter(time)) {
                        return DatePosition.MIDDLE
                    } else if (start.isEqual(time)) {
                        return DatePosition.START
                    } else if (end.isEqual(time)) {
                        return DatePosition.END
                    } else {
                        return DatePosition.OTHER
                    }
                }
            }
            else -> return DatePosition.OTHER
        }
    }
}


enum class DatePosition {
    START, //开始节点
    MIDDLE, //中间节点
    END, //结尾节点
    SINGLE_1, //单边界
    SINGLE_2, //双边界,左右边界同一天
    OTHER //未选中
}