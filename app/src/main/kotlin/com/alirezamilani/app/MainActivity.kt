package com.alirezamilani.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alirezamilani.app.ui.theme.PersianDateRangePickerTheme
import com.alirezaMilani.persianDateRangePicker.DateRangePicker
import com.alirezaMilani.persianDateRangePicker.persianCalendar.MyCalendar
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val calendar = Calendar.getInstance()
        val thisYear = calendar.get(Calendar.YEAR)
        val thisMonth = calendar.get(Calendar.MONTH) + 1
        val today = calendar.get(Calendar.DAY_OF_MONTH)

        val start = MyCalendar().apply {
            setCalendarDate(thisYear - 0, thisMonth + 0, today)
        }
        val end = MyCalendar().apply {
            setCalendarDate(thisYear - 0, thisMonth + 0, today + 1)
        }

        val time = System.currentTimeMillis()

        Log.i("Arthur_test", " $time")

        setContent {
            PersianDateRangePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DateRangePicker(
                        initialDates = start to end,
                        title = "SELECT DATES",
                        saveLabel = null,
                        isRtl = false,
                        yearRange = IntRange(2022, 2022),
                        onCloseClick = {  },
                        onConfirmClick = { _, _ -> Log.i("Arthur_test", "start: ${start.selectedDate}, end: ${end.selectedDate}")}
                    )
                }
            }
        }
    }
}

/**
    initialDates: Pair<PersianCalendar, PersianCalendar>? = null, 設定最一開始所選的日期
    yearRange: IntRange = IntRange(1400, 1401),
    colors: DateRangePickerColors = DateRangePickerDefaults.colors(),
    title: String? = null, => 小 title
    saveLabel: String? = null, => 按鈕所要顯示之文字，沒設定時會顯示儲存
    isRtl: Boolean = true, => 是否由右至左顯示
    onCloseClick: () -> Unit,
    onConfirmClick: (start: PersianCalendar, end: PersianCalendar) -> Unit
 */

