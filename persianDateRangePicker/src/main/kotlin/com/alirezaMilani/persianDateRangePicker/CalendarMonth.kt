package com.alirezaMilani.persianDateRangePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alirezaMilani.persianDateRangePicker.persianCalendar.MyCalendar

/**
 * A layout to show days of month
 *
 * @author Alireza Milani
 * @since 1.0.0
 */
@Composable
fun CalendarMonth(
    modifier: Modifier = Modifier.background(Color.White),
    isRtl: Boolean,
    state: DateRangePickerState = rememberDateRangePickerState(yearRange = IntRange(1400, 1401)),
    colors: DateRangePickerColors = DateRangePickerDefaults.colors(),
    calendar: MyCalendar
) {
    val (firstDay, numDays) = remember { state.getDates(calendar) }
    val datesList = remember { IntRange(1, numDays).toList() }
    val heightFactor = remember { state.getMaximumWeeks(firstDay, numDays) }

    LazyVerticalGrid(
        columns = Fixed(7),
        modifier = modifier.size(
            width = DateRangePickerTokens.DaysOfWeekWidth,
            height = (heightFactor * 48).dp
        ),
        userScrollEnabled = false
    ) {

        // 計算每月第一日前空格數
        for (x in 0 until firstDay) {
            item { Box(Modifier.size(DateRangePickerTokens.DayParentSize)) }
        }

        items(datesList) { date ->
            val currentDate = (calendar.clone() as MyCalendar).apply {
                setPersianDay(date)
            }
            val isSelected = remember(state.selectedStartDate, state.selectedEndDate) {
                state.isSelected(currentDate.timeInMillis)
            }

            val isRangeFill = remember(state.selectedStartDate, state.selectedEndDate) {
                state.isInRange(currentDate.timeInMillis)
            }

            val dayType = remember(state.selectedStartDate, state.selectedEndDate) {
                state.getDayType(currentDate.timeInMillis)
            }

            CalendarDay(
                date = date,
                selected = isSelected,
                dayType = dayType,
                isRtl = isRtl,
                isRangeFill = isRangeFill,
                colors = colors,
                onClick = { state.select(currentDate.timeInMillis) }
            )
        }
    }
}

@Preview
@Composable
fun CalendarMonthPreview() {
//    val calendar = MyCalendar().apply {
//        setCalendarYear(2023)
//        setPersianMonth(2)
//    }
    CalendarMonth(calendar = MyCalendar(), isRtl = false)
}