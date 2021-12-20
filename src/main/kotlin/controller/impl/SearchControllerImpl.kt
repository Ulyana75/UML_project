package controller.impl

import controller.SearchController
import model.ApartmentsForRentDatabase
import model.ApartmentsForSaleDatabase
import model.ClientsDatabase
import model.EmployeeDatabase
import model.impl.JsonApartmentsForRentDatabaseImpl
import model.impl.JsonApartmentsForSaleDatabaseImpl
import model.impl.JsonClientsDatabaseImpl
import model.impl.JsonEmployeeDatabaseImpl
import objects.*
import java.util.*

class SearchControllerImpl: SearchController {
    private val apartmentsForRentDatabase: ApartmentsForRentDatabase
    private val apartmentsForSaleDatabase: ApartmentsForSaleDatabase
    private val clientsDatabase: ClientsDatabase
    private val employeeDatabase: EmployeeDatabase

    init {
        apartmentsForRentDatabase = JsonApartmentsForRentDatabaseImpl.getInstance()
        apartmentsForSaleDatabase = JsonApartmentsForSaleDatabaseImpl.getInstance()
        clientsDatabase = JsonClientsDatabaseImpl.getInstance()
        employeeDatabase = JsonEmployeeDatabaseImpl.getInstance()
    }

    override fun searchClientByPassportNumber(passportNumber: String): Client? {
        return clientsDatabase.getByPassportNumber(passportNumber)
    }

    override fun searchApartmentForSale(
        areaMin: Float?,
        areaMax: Float?,
        roomsMin: Int?,
        roomsMax: Int?,
        priceMin: Float?,
        priceMax: Float?,
        hasFurniture: Boolean?,
        repairType: List<RepairType>?
    ): List<ApartmentForSale> {
        val apartments = apartmentsForSaleDatabase.getAll()
        return apartments.filter {
            (areaMin == null || it.area >= areaMin) &&
                    (areaMax == null || it.area <= areaMax) &&
                    (roomsMin == null || it.rooms >= roomsMin) &&
                    (roomsMax == null || it.rooms <= roomsMax) &&
                    (priceMin == null || it.price >= priceMin) &&
                    (priceMax == null || it.price <= priceMax) &&
                    (hasFurniture == null || it.hasFurniture == hasFurniture) &&
                    (repairType == null || repairType.contains(it.repairType))
        }
    }

    override fun searchApartmentForRent(
        areaMin: Float?,
        areaMax: Float?,
        roomsMin: Int?,
        roomsMax: Int?,
        priceMin: Float?,
        priceMax: Float?,
        hasFurniture: Boolean?,
        repairType: List<RepairType>?,
        requiredDates: List<Date>?,
        onlyLongTerm: Boolean?,
        canChildren: Boolean?,
        canPets: Boolean?
    ): List<ApartmentForRent> {
        val apartments = apartmentsForRentDatabase.getAll()
        return apartments.filter {
            (areaMin == null || it.area >= areaMin) &&
                    (areaMax == null || it.area <= areaMax) &&
                    (roomsMin == null || it.rooms >= roomsMin) &&
                    (roomsMax == null || it.rooms <= roomsMax) &&
                    (priceMin == null || it.price >= priceMin) &&
                    (priceMax == null || it.price <= priceMax) &&
                    (hasFurniture == null || it.hasFurniture == hasFurniture) &&
                    (repairType == null || repairType.contains(it.repairType)) &&
                    (requiredDates == null || it.busyDates.all { !requiredDates.contains(it) }) &&
                    (onlyLongTerm == null || it.onlyLongTerm == onlyLongTerm) &&
                    (canChildren == null || it.canChildren == canChildren) &&
                    (canPets == null || it.canPets == canPets)
        }
    }

    override fun searchApartmentById(id: String): Apartment? {
        return apartmentsForSaleDatabase.getById(id) ?: return apartmentsForRentDatabase.getById(id)
    }
}