package controller.impl

import controller.BookingController
import model.ApartmentsForRentDatabase
import model.impl.JsonApartmentsForRentDatabaseImpl
import utilities.ApartmentSystemException
import java.util.*

class BookingControllerImpl: BookingController {
    private val apartmentsForRentDatabase: ApartmentsForRentDatabase

    init {
        apartmentsForRentDatabase = JsonApartmentsForRentDatabaseImpl()
    }

    override fun book(apartmentId: String, dates: List<Date>) {
        val apartment = apartmentsForRentDatabase.getById(apartmentId) ?: throw ApartmentSystemException("No apartment with that ID")
        apartment.book(dates)
        apartmentsForRentDatabase.update(apartment)
    }
}