package controller

import java.util.*

interface BookingController {
    fun book(apartmentId: String, dates: List<Date>)
}