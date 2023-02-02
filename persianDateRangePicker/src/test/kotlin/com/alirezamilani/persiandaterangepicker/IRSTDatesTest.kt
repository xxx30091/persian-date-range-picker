package com.alirezamilani.persiandaterangepicker

import com.alirezaMilani.persianDateRangePicker.IRSTDates
import com.alirezaMilani.persianDateRangePicker.persianCalendar.MyCalendar
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * @author Alireza Milani
 * @since 1.0.0
 */
class IRSTDatesTest {

    @Test
    fun getTodayCalendar() {
        val todayCalendar = IRSTDates.getTodayCalendar()

        Assert.assertEquals(MyCalendar(), todayCalendar)
    }

    @Test
    fun getMaximumWeeks() {
        val maximum = IRSTDates.getCalendar().getMaximum(Calendar.WEEK_OF_MONTH)

        Assert.assertEquals(6, maximum)
    }

    @Test
    fun getUtcCalendarOf_ReturnFirstMomentOfCurrentDate() {
        val expected = MyCalendar().also {
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)
        }

        val calendar = IRSTDates.getCalendarOf(null)

        Assert.assertEquals(expected.persianShortDate, calendar.persianShortDate)
    }
}