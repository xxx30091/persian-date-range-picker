package com.alirezaMilani.persianDateRangePicker.persianCalendar

import com.alirezaMilani.persianDateRangePicker.persianCalendar.PersianCalendarConstants.Companion.MILLIS_JULIAN_EPOCH
import com.alirezaMilani.persianDateRangePicker.persianCalendar.PersianCalendarConstants.Companion.MILLIS_OF_A_DAY
import com.alirezaMilani.persianDateRangePicker.persianCalendar.PersianCalendarUtils.Companion.ceil
import java.util.*
import kotlin.math.floor

/**
 * **Persian *Shamsi* calendar**
 *
 * The calendar consists of 12 months, the first six of which are 31 days, the
 * next five 30 days, and the final month 29 days in a normal year and 30 days
 * in a leap year.
 * >
 * As one of the few calendars designed in the era of accurate positional
 * astronomy, the Persian calendar uses a very complex leap year structure which
 * makes it the most accurate solar calendar in use today. Years are grouped
 * into cycles which begin with four normal years after which every fourth
 * subsequent year in the cycle is a leap year. Cycles are grouped into grand
 * cycles of either 128 years (composed of cycles of 29, 33, 33, and 33 years)
 * or 132 years, containing cycles of of 29, 33, 33, and 37 years. A great grand
 * cycle is composed of 21 consecutive 128 year grand cycles and a final 132
 * grand cycle, for a total of 2820 years. The pattern of normal and leap years
 * which began in 1925 will not repeat until the year 4745!
 * >
 * Each 2820 year great grand cycle contains 2137 normal years of 365 days
 * and 683 leap years of 366 days, with the average year length over the great
 * grand cycle of 365.24219852. So close is this to the actual solar tropical
 * year of 365.24219878 days that the Persian calendar accumulates an error of
 * one day only every 3.8 million years. As a purely solar calendar, months are
 * not synchronized with the phases of the Moon.
 * >
 * **PersianCalendar** by extending Default GregorianCalendar
 * provides capabilities such as:
 *
 * - you can set the date in Persian by `setPersianDate(persianYear,
 * persianMonth, persianDay)` and get the Gregorian date or vice versa
 *
 * - determine is the current date is Leap year in persian calendar or not by
 * `IsPersianLeapYear()`
 *
 * - getPersian short and long Date String `getPersianShortDate()` and
 * `getPersianLongDate()` you also can set delimiter to assign delimiter of returned dateString
 *
 * - parse string based on assigned delimiter
 *
 * ### Example ###
 *  ```
 * val persianCalendar = PersianCalendar()
 * println(persianCalendar.persianShortDate)
 *
 * persianCalendar.set(1982, Calendar.MAY, 22)
 * println(persianCalendar.persianShortDate)
 *
 * persianCalendar.delimiter = " , "
 * persianCalendar.parse("1361 , 03 , 01")
 * println(persianCalendar.persianShortDate)
 *
 * persianCalendar.setPersianDate(1361, 3, 1)
 * println(persianCalendar.persianLongDate)
 * println(persianCalendar.getTime())
 *
 * persianCalendar.addPersianDate(Calendar.MONTH, 33)
 * persianCalendar.addPersianDate(Calendar.YEAR, 5)
 * persianCalendar.addPersianDate(Calendar.DATE, 50)
 * ```
 *
 * @author Alireza Milani
 * @since 2.6.2
 */
class MyCalendar : GregorianCalendar {

    var calendarYear = 0
        private set

    var calendarMonth = 0
        private set
        get() = field + 1

    var calendarDay = 0
        private set

    /**
     * Use to separate PersianDate's field and also Parse the DateString based on this delimiter
     */
    var delimiter = "/"

    val calendarMonthName: String
        get() = PersianCalendarConstants.MONTH_NAMES[calendarMonth - 1]

    val calendarWeekDayName: String
        get() = when (get(DAY_OF_WEEK)) {
            SATURDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[0]
            SUNDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[1]
            MONDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[2]
            TUESDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[3]
            WEDNESDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[4]
            THURSDAY -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[5]
            else -> PersianCalendarConstants.CHINESE_WEEK_DAY_NAMES[6]
        }

    /**
     * String of Persian Date e.g. *شنبه 01 خرداد 1361*
     */
//    val persianLongDate: String
//        get() = "$calendarWeekDayName  $calendarDay  $calendarMonthName  $calendarYear"
    val persianLongDate: String
        get() = "$calendarYear-$calendarMonth-$calendarDay"

    val selectedDate: String
        get() {
            val month = if (calendarMonth < 10) "0$calendarMonth" else "$calendarMonth"
            val day = if (calendarDay < 10) "0$calendarDay" else "$calendarDay"
            return "$calendarYear-$month-$day"
        }

    /**
     * String of Persian Date e.g. *شنبه 01 خرداد 1361 ساعت 10:58:20*
     */
    val persianLongDateTime: String
        get() = persianLongDate + " ساعت " + get(HOUR_OF_DAY) + ":" + get(MINUTE) + ":" + get(SECOND)

    /**
     * String of persian date formatted by
     *
     *  **1400[delimiter]01[delimiter]01** default delimiter is '/'
     */
    val persianShortDate: String
        get() = formatToMilitary(calendarYear) + delimiter + formatToMilitary(calendarMonth) + delimiter + formatToMilitary(calendarDay)

    val persianShortDateTime: String
        get() = formatToMilitary(calendarYear) + delimiter + formatToMilitary(calendarMonth) + delimiter + formatToMilitary(
            calendarDay
        ) + " " + formatToMilitary(get(HOUR_OF_DAY)) + ":" + formatToMilitary(get(MINUTE)) + ":" + formatToMilitary(
            get(SECOND)
        )

    /**
     * Determines if the given year is a leap year in persian calendar.
     * Returns true if the given year is a leap year.
     */
    val isPersianLeapYear: Boolean
        get() = PersianCalendarUtils.isPersianLeapYear(calendarYear)

    /**
     * Most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. **Default TimeZone was set to
     * "GMT"** in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    constructor(millis: Long) {
        timeInMillis = millis
    }

    /**
     * Most of the time we don't care about TimeZone when we persisting Date or
     * doing some calculation on date. **Default TimeZone was set to
     * "GMT"** in order to make developer to work more convenient with
     * the library; however you can change the TimeZone as you do in
     * GregorianCalendar by calling setTimeZone()
     */
    constructor() : super(TimeZone.getDefault(), Locale.getDefault())

    override fun toString(): String {
        val str = super.toString()
        return str.substring(0, str.length - 1) + ",PersianDate=" + persianShortDate + "]"
    }

    override fun set(field: Int, value: Int) {
        super.set(field, value)
        calculatePersianDate()
    }

    override fun setTimeInMillis(millis: Long) {
        super.setTimeInMillis(millis)
        calculatePersianDate()
    }

    override fun setTimeZone(zone: TimeZone) {
        super.setTimeZone(zone)
        calculatePersianDate()
    }

    /**
     * Set the persian date it converts PersianDate to the Julian and
     * assigned equivalent milliseconds to the instance
     */
    fun setCalendarDate(year: Int, month: Int, day: Int) {
        calendarYear = year
        calendarMonth = month - 1
        calendarDay = day

        val gregorianYearMonthDay =
            persianToGregorian(YearMonthDay(calendarYear, this.calendarMonth - 1, calendarDay))
        set(gregorianYearMonthDay.year, gregorianYearMonthDay.month, gregorianYearMonthDay.day)
    }

    fun setCalendarYear(year: Int) {
        calendarYear = year

        timeInMillis = convertToMillis(
            PersianCalendarUtils.persianToJulian(
                if (calendarYear > 0) calendarYear.toLong() else calendarYear + 1.toLong(),
                calendarMonth - 1,
                1
            )
        )
    }

    /**
     * Specify month and set 1 for day of specific month
     */
    fun setPersianMonth(month: Int) {
        calendarMonth = month - 1

        timeInMillis = convertToMillis(
            PersianCalendarUtils.persianToJulian(
                if (calendarYear > 0) calendarYear.toLong() else calendarYear + 1.toLong(),
                calendarMonth - 1,
                1
            )
        )
    }

    fun setPersianDay(day: Int) {
        calendarDay = day

        timeInMillis = convertToMillis(
            PersianCalendarUtils.persianToJulian(
                if (calendarYear > 0) calendarYear.toLong() else calendarYear + 1.toLong(),
                calendarMonth - 1,
                calendarDay
            )
        )
    }

    fun setGregorianDay(day: Int) {
        calendarDay = day
//        timeInMillis = convertToMillis(
//
//        )
    }

    override fun compareTo(other: java.util.Calendar): Int {
        require(other is MyCalendar) { "other is not PersianCalendar" }

        return when {
            calendarYear < other.calendarYear -> -1
            calendarYear > other.calendarYear -> 1
            else -> {
                when {
                    calendarMonth < other.calendarMonth -> -1
                    calendarMonth > other.calendarMonth -> 1
                    else -> {
                        when {
                            calendarDay < other.calendarDay -> -1
                            calendarDay > other.calendarDay -> 1
                            else -> 0
                        }
                    }
                }
            }
        }
    }

    /**
     * Add specific amount of fields to the current date for now doesn't handle
     * before 1 farvardin hejri (before epoch)
     *
     * @param field You can use [MyCalendar.YEAR], [MyCalendar.MONTH], [MyCalendar.DATE], [MyCalendar.HOUR_OF_DAY], [MyCalendar.MINUTE], [MyCalendar.SECOND], [MyCalendar.MILLISECOND]
     */
    fun addPersianDate(field: Int, amount: Int) {
        if (amount == 0) {
            // Do nothing!
            return
        }
        require(!(field < 0 || field >= ZONE_OFFSET))
        if (field == YEAR) {
            setCalendarDate(calendarYear + amount, calendarMonth, calendarDay)
            return
        } else if (field == MONTH) {
            setCalendarDate(
                calendarYear + (calendarMonth + amount) / 12, (calendarMonth + amount) % 12, calendarDay
            )
            return
        }
        add(field, amount)
        calculatePersianDate()
    }

    /**
     * Use [PersianDateParser] to parse string and get the Persian Date.
     */
    fun parse(dateString: String) {
        val parser = PersianDateParser(dateString, delimiter).persianDate
        setCalendarDate(parser.calendarYear, parser.calendarMonth, parser.calendarDay)
    }

    fun getMonthLength(): Int = if (calendarMonth == 12 && isPersianLeapYear) {
        30
    } else {
        persianDaysInMonth[calendarMonth - 1]
    }

    private fun convertToMillis(julianDate: Long): Long =
        MILLIS_JULIAN_EPOCH + julianDate * MILLIS_OF_A_DAY + ceil(
            (timeInMillis - MILLIS_JULIAN_EPOCH.toDouble()),
            MILLIS_OF_A_DAY.toDouble()
        )

    /**
     * Calculate persian date from current Date and populates the corresponding
     * fields *persianYear, persianMonth, persianDay*
     */
    private fun calculatePersianDate() {
        val persianYearMonthDay =
            gregorianToJalali(YearMonthDay(get(YEAR), get(MONTH), get(DAY_OF_MONTH)))
        calendarYear = persianYearMonthDay.year
        calendarMonth = persianYearMonthDay.month
        calendarDay = persianYearMonthDay.day
    }

    private fun formatToMilitary(i: Int): String = if (i <= 9) "0$i" else i.toString()

    internal class YearMonthDay(var year: Int, var month: Int, var day: Int) {

        override fun toString(): String {
            return "$year/$month/$day"
        }
    }

    companion object {

        private val gregorianDaysInMonth = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        private val persianDaysInMonth = intArrayOf(31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)

        private fun gregorianToJalali(gregorian: YearMonthDay): YearMonthDay {
            require(!(gregorian.month > 11 || gregorian.month < -11))

            var persianYear: Int
            val persianMonth: Int
            val persianDay: Int
            var persianDayNo: Int

            gregorian.year = gregorian.year - 1600
            gregorian.day = gregorian.day - 1

            var gregorianDayNo: Int =
                (365 * gregorian.year + floor(((gregorian.year + 3) / 4).toDouble()).toInt() - floor(
                    ((gregorian.year + 99) / 100).toDouble()
                ).toInt() + floor(((gregorian.year + 399) / 400).toDouble()).toInt())

            var i = 0
            while (i < gregorian.month) {
                gregorianDayNo += gregorianDaysInMonth[i]
                ++i
            }

            if (gregorian.month > 1 && (gregorian.year % 4 == 0 && gregorian.year % 100 != 0 || gregorian.year % 400 == 0)) {
                ++gregorianDayNo
            }
            gregorianDayNo += gregorian.day
            persianDayNo = gregorianDayNo - 79
            val persianNP: Int = floor((persianDayNo / 12053).toDouble()).toInt()
            persianDayNo %= 12053
            persianYear = 979 + 33 * persianNP + 4 * (persianDayNo / 1461)
            persianDayNo %= 1461
            if (persianDayNo >= 366) {
                persianYear += floor(((persianDayNo - 1) / 365).toDouble()).toInt()
                persianDayNo = (persianDayNo - 1) % 365
            }
            i = 0
            while (i < 11 && persianDayNo >= persianDaysInMonth[i]) {
                persianDayNo -= persianDaysInMonth[i]
                ++i
            }
            persianMonth = i
            persianDay = persianDayNo + 1
            return YearMonthDay(persianYear, persianMonth, persianDay)
        }

        private fun persianToGregorian(persian: YearMonthDay): YearMonthDay {
            require(!(persian.month > 11 || persian.month < -11))
            var gregorianYear: Int
            val gregorianMonth: Int
            val gregorianDay: Int
            var gregorianDayNo: Int
            var leap: Int
            persian.year = persian.year - 979
            persian.day = persian.day - 1
            var persianDayNo: Int =
                365 * persian.year + (persian.year / 33) * 8 + floor(((persian.year % 33 + 3) / 4).toDouble()).toInt()

            var i = 0
            while (i < persian.month) {
                persianDayNo += persianDaysInMonth[i]
                ++i
            }
            persianDayNo += persian.day
            gregorianDayNo = persianDayNo + 79
            gregorianYear = 1600 + 400 * floor((gregorianDayNo / 146097).toDouble()).toInt()
            gregorianDayNo %= 146097
            leap = 1
            if (gregorianDayNo >= 36525) {
                gregorianDayNo--
                gregorianYear += 100 * floor((gregorianDayNo / 36524).toDouble()).toInt()
                gregorianDayNo %= 36524
                if (gregorianDayNo >= 365) {
                    gregorianDayNo++
                } else {
                    leap = 0
                }
            }
            gregorianYear += 4 * floor((gregorianDayNo / 1461).toDouble()).toInt()
            gregorianDayNo %= 1461
            if (gregorianDayNo >= 366) {
                leap = 0
                gregorianDayNo--
                gregorianYear += floor((gregorianDayNo / 365).toDouble()).toInt()
                gregorianDayNo %= 365
            }
            i = 0
            while (gregorianDayNo >= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0) {
                gregorianDayNo -= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0
                i++
            }
            gregorianMonth = i
            gregorianDay = gregorianDayNo + 1
            return YearMonthDay(gregorianYear, gregorianMonth, gregorianDay)
        }
    }
}