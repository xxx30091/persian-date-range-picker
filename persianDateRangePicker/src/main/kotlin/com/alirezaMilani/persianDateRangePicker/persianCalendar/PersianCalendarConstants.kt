package com.alirezaMilani.persianDateRangePicker.persianCalendar

/**
 * @author Alireza Milani
 * @since 2.6.2
 */
class PersianCalendarConstants {

    companion object {
        /**
         * 00:00:00 UTC (Gregorian) Julian day 0,
         * 0 milliseconds since *1970-01-01*
         */
        const val MILLIS_JULIAN_EPOCH = -210866803200000L

        /**
         * Milliseconds of a day calculated by
         *
         * ``24L(hours) * 60L(minutes) * 60L(seconds) * 1000L(millis)``
         */
        const val MILLIS_OF_A_DAY = 86400000L

        /**
         * The JDN of 1 Farvardin 1; Equivalent to March 19, 622 A.D.
         */
        const val PERSIAN_EPOCH = 1948321L

        /**
         * List of persian month names
         */
//        "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
        val CHINESE_MONTH_NAMES = arrayOf(
            "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"
        )

        val MONTH_NAMES = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )

        /**
         * List of persian week day names
         */
        val CHINESE_WEEK_DAY_NAMES = arrayOf(
            "六",
            "日",
            "一",
            "二",
            "三",
            "四",
            "五"
        )

//        val PERSIAN_WEEK_DAY_NAMES = arrayOf(
//            "\u0634\u0646\u0628\u0647",
//            "\u06cc\u06a9\u200c\u0634\u0646\u0628\u0647",
//            "\u062f\u0648\u0634\u0646\u0628\u0647",
//            "\u0633\u0647\u200c\u0634\u0646\u0628\u0647",
//            "\u0686\u0647\u0627\u0631\u0634\u0646\u0628\u0647",
//            "\u067e\u0646\u062c\u200c\u0634\u0646\u0628\u0647",
//            "\u062c\u0645\u0639\u0647"
//        )

//        val PERSIAN_MONTH_NAMES = arrayOf(
//            "\u0641\u0631\u0648\u0631\u062f\u06cc\u0646",
//            "\u0627\u0631\u062f\u06cc\u0628\u0647\u0634\u062a",
//            "\u062e\u0631\u062f\u0627\u062f",
//            "\u062a\u06cc\u0631",
//            "\u0645\u0631\u062f\u0627\u062f",
//            "\u0634\u0647\u0631\u06cc\u0648\u0631",
//            "\u0645\u0647\u0631",
//            "\u0622\u0628\u0627\u0646",
//            "\u0622\u0630\u0631",
//            "\u062f\u06cc",
//            "\u0628\u0647\u0645\u0646",
//            "\u0627\u0633\u0641\u0646\u062f"
//        )
    }
}