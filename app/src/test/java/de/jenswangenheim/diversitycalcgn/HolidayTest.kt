package de.jenswangenheim.diversitycalcgn

import de.jenswangenheim.diversitycalcgn.model.Holiday
import org.joda.time.DateTime
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class HolidayTest {

    val holidayList = mutableListOf<Holiday>()

    init {
        holidayList.add(Holiday("Tag2", "Lorem ipsum", "Type",
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "01.02.2018"),
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "01.02.2018")))

        holidayList.add(Holiday("Tag4", "Lorem ipsum", "Type",
                DateTime(System.currentTimeMillis()),
                DateTime(System.currentTimeMillis())))

        holidayList.add(Holiday("Tag3", "Lorem ipsum", "Type",
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "11.06.2018"),
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "11.06.2018")))

        holidayList.add(Holiday("Tag1", "Lorem ipsum", "Type",
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "02.01.2018"),
                Holiday.stringAsDate(Holiday.DATE_FORMAT_PATTERN_LONG, "02.01.2018")))
    }

    @Test
    fun sorting_isCorrect() {
        holidayList.sort()
        assertEquals("Tag1", holidayList.first().name)
        assertEquals("Tag4", holidayList.last().name)
    }
}
