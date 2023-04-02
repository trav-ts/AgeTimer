package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun currentYear() {
        val currDate = LocalDate.now()
        val selectedDate = LocalDate.of(2023, 1, 1)
        val result = getDaysSince(currDate, selectedDate)
        assertEquals(91, result)
    }

    @Test
    fun birthYear() {
        val currDate = LocalDate.now()
        val selectedDate = LocalDate.of(1994, 4, 12)
        val result = getDaysSince(currDate, selectedDate)
        assertEquals(10582, result)
    }

    @Test
    fun birthDay(){
        val currDate = LocalDate.now()
        val selectedDate = LocalDate.of(2024, 4, 12)
        val result = getDaysUntil(currDate, selectedDate)
        assertEquals(376, result)
    }





    public fun getDaysSince(currDate: LocalDate, selectedDate: LocalDate): Long {
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
    public fun getDaysUntil(currDate: LocalDate, selectedDate: LocalDate): Long {
        var sum: Long = 0

        // 1st handle days left in start year.
        sum += if(currDate.isLeapYear)
            366 - currDate.dayOfYear
        else
            365 - currDate.dayOfYear

        if(currDate.year == selectedDate.year){
            return (selectedDate.dayOfYear - currDate.dayOfYear).toLong()
        }

        // 2nd handle years between two days.
        for (i in currDate.year+1 until selectedDate.year){
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
        sum+= selectedDate.dayOfYear

        return sum
    }
}