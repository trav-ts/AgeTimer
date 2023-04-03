/*
* Author: Travis Slade
* Date: 04/03/2023
* Notes:
*       Learning the in's and out's of kotlin programming and android development.
* Goals:
*       Have a running age "stop watch" for the age button.
*       Display time in seconds since or until a selected date.
*       Display time in minutes since or until a selected date.
*       Make it prettier
* */
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.time.LocalDate
import java.time.YearMonth


class MainActivity : AppCompatActivity() {
    private lateinit var dateSelector: DatePicker
    private lateinit var stopWatchTextView: TextView
    private lateinit var secondsButton: Button
    private lateinit var daysButton: Button
    private lateinit var minutesButton: Button
    private lateinit var timerButton: Button
    private lateinit var currDate: LocalDate
    private lateinit var selectedDate: LocalDate
    private var lastId: Int = 0 // track the last button clicked.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dateSelector = findViewById(R.id.datePicker)
        currDate = LocalDate.now()

        // Select date and update based on last button selected.
        dateSelector.init(1994,3,12){_, year, month, day ->
            selectedDate = LocalDate.of(year, month+1, day)
            when(lastId){
                secondsButton.id -> secondsButton.performClick()
                daysButton.id -> daysButtonClicked()
                minutesButton.id -> minutesButtonClicked()
                timerButton.id -> timerButtonClicked()
            }
        }

        selectedDate = LocalDate.of(dateSelector.year, dateSelector.month+1, dateSelector.dayOfMonth)

        // Assign all required resources.
        stopWatchTextView = findViewById((R.id.textViewStopWatch))
        secondsButton = findViewById(R.id.seconds_btn)
        daysButton = findViewById(R.id.days_btn)
        minutesButton = findViewById(R.id.minutes_btn)
        timerButton = findViewById(R.id.timer_btn)

        // OnClickListeners
        secondsButton.setOnClickListener{
            secondsButtonClicked()
        }
        daysButton.setOnClickListener{
            daysButtonClicked()
        }
        minutesButton.setOnClickListener{
            minutesButtonClicked()
        }
        timerButton.setOnClickListener{
            timerButtonClicked()
        }
    }

    // Listener function
    private fun secondsButtonClicked(){
        val secondsInDay = 86400
        lastId = secondsButton.id
        stopWatchTextView.text = "00:" + currDate.monthValue +":22"
    }

    // Listener function
    private fun daysButtonClicked(){
        lastId = daysButton.id
        val result = if(currDate > selectedDate)
            getDayDifference(currDate, selectedDate)
        else if (currDate < selectedDate)
            getDayDifference(selectedDate, currDate)
        else
            0
        stopWatchTextView.text = result.toString() + "Days"
    }

    // Listener function
    private fun minutesButtonClicked(){
        lastId = minutesButton.id
        stopWatchTextView.text = "00:22:00"
    }

    // Listener function
    private fun timerButtonClicked(){
        lastId = timerButton.id
        selectedDate = LocalDate.of(dateSelector.year, dateSelector.month + 1, dateSelector.dayOfMonth)
        val diffs: List<Int> = getDateDifference(currDate, selectedDate)
        val monthDiff: Int = diffs[1]
        val yearDiff: Int = diffs[2]
        val dayDiff: Int = diffs[0]
        stopWatchTextView.text = yearDiff.toString() +":"+ monthDiff.toString() +":"+ dayDiff.toString()
    }

    /**
     * Returns the difference of two dates. 10 years, 2 months, and 4 days. NOT 10 years, 122 months, 3714 days.
     */
    private fun getDateDifference(currDate: LocalDate, selectedDate: LocalDate): List<Int> {
        // Do something in response to button click
        var monthDiff = 0
        val yearDiff: Int
        val dayDiff: Int

        // Date is upcoming.
        if (selectedDate.month > currDate.month) {
            val monthsLeft = 12 - selectedDate.monthValue
            monthDiff = monthsLeft + currDate.monthValue // months left in year plus months this year.
            yearDiff = currDate.year - selectedDate.year - 1
            dayDiff = if (selectedDate.isLeapYear) // Check for leap year
                (366 - selectedDate.dayOfYear) + currDate.dayOfYear
            else
                (365 - selectedDate.dayOfYear) + currDate.dayOfYear
        }
        // Date has already passed.
        else if (selectedDate.month < currDate.month) {
            if (currDate.dayOfMonth < selectedDate.dayOfMonth) {
                monthDiff = currDate.monthValue - selectedDate.monthValue -1
                dayDiff = currDate.dayOfYear - selectedDate.dayOfYear
            } else {
                monthDiff = currDate.monthValue - selectedDate.monthValue

                // Get Num days
                var sum =
                    selectedDate.month.length(selectedDate.isLeapYear) - selectedDate.dayOfMonth
                sum += currDate.dayOfMonth
                for(i in currDate.monthValue+1 until selectedDate.monthValue){
                    sum += YearMonth.of(currDate.year, i).lengthOfMonth()
                }
                dayDiff = currDate.dayOfYear - selectedDate.dayOfYear - sum
            }
            yearDiff = currDate.year - selectedDate.year
        }
        // Same month
        else {
            if (selectedDate.dayOfMonth > currDate.dayOfMonth) {
                monthDiff = 11
                yearDiff = currDate.year - selectedDate.year - 1
                dayDiff = if (selectedDate.isLeapYear)
                    (366 - selectedDate.dayOfYear) + currDate.dayOfYear
                else
                    (365 - selectedDate.dayOfYear) + currDate.dayOfYear
            } else if (selectedDate.dayOfMonth < currDate.dayOfMonth) {
                monthDiff = 0
                yearDiff = currDate.year - selectedDate.year
                dayDiff = currDate.dayOfYear - selectedDate.dayOfYear
            } else {
                monthDiff = 0
                dayDiff = 0
                yearDiff = currDate.year - selectedDate.year
            }
        }
        return listOf(dayDiff, monthDiff, yearDiff)
    }

    private fun getSecondsSince(currDate: LocalDate, selectedDate: LocalDate): Long {
        return 0
    }

    /**
     * Return the number of days between the two dates assuming that the selected date is less than
     * the current date.
     * First date parameter comes later in time than second. This provides days since.
     * First date parameter comes earlier in time than second. This provides days until.
     */
    private fun getDayDifference(currDate: LocalDate, selectedDate: LocalDate): Long {
        var sum: Long = 0

        // 1st handle days left in start year.
        sum += if(selectedDate.isLeapYear)
            366 - selectedDate.dayOfYear
        else
            365 - selectedDate.dayOfYear

        if(currDate.year == selectedDate.year){
            return (currDate.dayOfYear - selectedDate.dayOfYear).toLong()
        }
        // 2nd handle years between two days.
        for (i in selectedDate.year+1 until currDate.year){
            sum += if(i % 4 == 0) {
                if (i % 100 == 0) {
                    // year is divisible by 400, hence the year is a leap year
                    if(i % 400 == 0)
                        366
                    else
                        365
                } else
                    366
            } else
                365
        }
        //3rd handle days so far of current year.
        sum+= currDate.dayOfYear

        return sum
    }

    private fun getMinutesSince(currDate: LocalDate, selectedDate: LocalDate): Long {
        return 0
    }
}