package view

import controller.*
import objects.*
import java.util.*

interface View {
    val creationController: CreationController
    val searchController: SearchController
    val priceController: PriceController
    val authController: AuthController
    val finishOrderController: FinishOrderController
    val bookingController: BookingController

    fun enter(): Employee
    fun createApartmentForRent()
    fun createApartmentForSale()
    fun createOrder(employee: Employee)
    fun createClient()
    fun createContract(employee: Employee)
    fun createEmployee(author: Employee)
    fun searchApartmentForSale()
    fun searchApartmentForRent()
    fun bookApartment()
    fun searchClient()
    fun searchApartment()
    fun finishOrder(employee: Employee)
}