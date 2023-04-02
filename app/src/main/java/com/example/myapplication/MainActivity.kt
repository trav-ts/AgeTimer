/*
* Author: Travis Slade
* Date: 04/01/2023
* Notes:
*       Learning the in's and out's of kotlin programming and android development.
*
* */
package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.google.android.material.datepicker.DateSelector
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*
import kotlin.time.Duration.Companion.days

class MainActivity : AppCompatActivity() {
    private lateinit var dateSelector: DatePicker
    private lateinit var stopWatchTextView: TextView
    private lateinit var secondsButton: Button
    private lateinit var hoursButton: Button
    private lateinit var minutesButton: Button
    private lateinit var timerButton: Button
    private lateinit var currDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assign all required resources.
        dateSelector = findViewById(R.id.datePicker)
        dateSelector.init(1994,3,12){view, year, month, day ->
            val selectedDate = LocalDate.of(year, dateSelector.month+1, day)
            currDate = LocalDate.now()
            val monthDiff: Int
            val yearDiff: Int
            val dayDiff: Int

            // Date is upcoming.
            if(selectedDate.month > currDate.month)
            {
                val monthsLeft = 12 - selectedDate.monthValue
                monthDiff = monthsLeft + currDate.monthValue
                yearDiff = currDate.year - selectedDate.year - 1
                dayDiff = if(selectedDate.isLeapYear)
                    (366 - selectedDate.dayOfYear) + currDate.dayOfYear
                else
                    (365 - selectedDate.dayOfYear) + currDate.dayOfYear
            }
            // Date has already passed.
            else if(selectedDate.month < currDate.month){
                if(currDate.dayOfYear - selectedDate.monthValue < 30)
                {
                    monthDiff = 0;
                    dayDiff = currDate.dayOfYear - selectedDate.dayOfYear
                }
                else{
                    monthDiff = currDate.monthValue - selectedDate.monthValue
                    var sum = selectedDate.month.length(selectedDate.isLeapYear) - selectedDate.dayOfMonth
                    sum += currDate.month.length(selectedDate.isLeapYear) - currDate.dayOfMonth
                    val n = YearMonth.of(currDate.year, 0)
                    n.lengthOfMonth()
                    dayDiff = currDate.dayOfYear - selectedDate.dayOfYear

                }
                yearDiff = currDate.year - selectedDate.year;
            }
            // Same month
            else{
                if(selectedDate.dayOfMonth > currDate.dayOfMonth){
                    monthDiff = 11
                    yearDiff = currDate.year - selectedDate.year - 1
                    dayDiff = if(selectedDate.isLeapYear)
                        (366 - selectedDate.dayOfYear) + currDate.dayOfYear
                    else
                        (365 - selectedDate.dayOfYear) + currDate.dayOfYear
                }
                else if(selectedDate.dayOfMonth < currDate.dayOfMonth){
                    monthDiff = 0
                    yearDiff = currDate.year - selectedDate.year
                    dayDiff = currDate.dayOfYear-selectedDate.dayOfYear
                }
                else{
                    monthDiff = 0
                    dayDiff = 0
                    yearDiff = currDate.year - selectedDate.year
                }
            }

            stopWatchTextView.text = yearDiff.toString() +":"+ monthDiff.toString() +":"+ dayDiff.toString()
        }
        stopWatchTextView = findViewById((R.id.textViewStopWatch))
        secondsButton = findViewById(R.id.seconds_btn)
        hoursButton = findViewById(R.id.hours_btn)
        minutesButton = findViewById(R.id.minutes_btn)
        timerButton = findViewById(R.id.timer_btn)

        secondsButton.setOnClickListener{v: View ->
            stopWatchTextView.text = "00:" + currDate.monthValue +":22"
        }
        hoursButton.setOnClickListener{v: View ->
            stopWatchTextView.text = "22:00:00"
        }
        minutesButton.setOnClickListener{v: View ->
            stopWatchTextView.text = "00:22:00"
        }
        timerButton.setOnClickListener{v: View ->
            stopWatchTextView.text = "22:22:22"
        }
    }
    fun sendMessage(view: View) {
        // Do something in response to button click
    }
}