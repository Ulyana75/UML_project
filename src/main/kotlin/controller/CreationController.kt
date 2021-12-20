package controller

import objects.*
import java.util.*

interface CreationController {
    fun createClient(
        name: String,
        dateOfBirth: Date,
        passportNumber: String,
        contracts: MutableList<Contract>
    ): Client

    fun createApartmentForSale(
        area: Float,
        rooms: Int,
        city: String,
        street: String,
        house: String,
        apartmentNumber: String,
        price: Float,
        hasFurniture: Boolean,
        repairType: RepairType,
        description: String
    ): ApartmentForSale

    fun createApartmentForRent(
        area: Float,
        rooms: Int,
        city: String,
        street: String,
        house: String,
        apartmentNumber: String,
        price: Float,
        hasFurniture: Boolean,
        repairType: RepairType,
        description: String,
        busyDates: List<Date>,
        onlyLongTerm: Boolean,
        canChildren: Boolean,
        canPets: Boolean
    ): ApartmentForRent

    fun createEmployee(
        author: Employee,
        name: String,
        dateOfBirth: Date,
        passportNumber: String,
        contracts: MutableList<Contract>,
        activeOrders: MutableList<Order>
    ): Employee

    fun createOrder(
        employee: Employee,
        client: Client,
        apartment: Apartment
    ): Order

    fun createContract(
        client: Client,
        employee: Employee,
        servicePrice: Float,
        percentToCompany: Float,
        apartment: Apartment
    ): Contract
}