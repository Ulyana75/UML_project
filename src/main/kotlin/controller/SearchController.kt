package controller

import objects.*
import java.util.*

interface SearchController {
    fun searchClientByPassportNumber(passportNumber: String): Client?

    fun searchApartmentForSale(
        areaMin: Float? = null,
        areaMax: Float? = null,
        roomsMin: Int? = null,
        roomsMax: Int? = null,
        priceMin: Float? = null,
        priceMax: Float? = null,
        hasFurniture: Boolean? = null,
        repairType: List<RepairType>? = null,
        // TODO district, metro stations
    ): List<ApartmentForSale>

    fun searchApartmentForRent(
        areaMin: Float? = null,
        areaMax: Float? = null,
        roomsMin: Int? = null,
        roomsMax: Int? = null,
        priceMin: Float? = null,
        priceMax: Float? = null,
        hasFurniture: Boolean? = null,
        repairType: List<RepairType>? = null,
        requiredDates: List<Date>? = null,
        onlyLongTerm: Boolean? = null,
        canChildren: Boolean? = null,
        canPets: Boolean? = null
        // TODO district, metro stations
    ): List<ApartmentForRent>

    fun searchApartmentById(id: String): Apartment?
}