package controller.impl

import controller.FinishOrderController
import controller.SearchController
import model.ApartmentsForRentDatabase
import model.ApartmentsForSaleDatabase
import model.EmployeeDatabase
import model.impl.JsonApartmentsForRentDatabaseImpl
import model.impl.JsonApartmentsForSaleDatabaseImpl
import model.impl.JsonEmployeeDatabaseImpl
import objects.ApartmentForRent
import objects.ApartmentForSale
import objects.Employee
import utilities.ApartmentSystemException

class FinishOrderControllerImpl: FinishOrderController {
    private val searchController: SearchController
    private val apartmentsForSaleDatabase: ApartmentsForSaleDatabase
    private val apartmentsForRentDatabase: ApartmentsForRentDatabase
    private val employeeDatabase: EmployeeDatabase

    init {
        searchController = SearchControllerImpl()
        apartmentsForSaleDatabase = JsonApartmentsForSaleDatabaseImpl()
        apartmentsForRentDatabase = JsonApartmentsForRentDatabaseImpl()
        employeeDatabase = JsonEmployeeDatabaseImpl()
    }

    override fun finishOrder(employee: Employee, orderId: String) {
        val order = employee.activeOrders.find { it.id == orderId } ?: throw ApartmentSystemException("No order with that ID")
        if (order.apartmentId != null) {
            when (val apartment = searchController.searchApartmentById(order.apartmentId)) {
                is ApartmentForRent -> apartmentsForRentDatabase.removeById(apartment.id)
                is ApartmentForSale -> apartmentsForSaleDatabase.removeById(apartment.id)
            }
        }
        employee.activeOrders.remove(order)
        employeeDatabase.update(employee)
        // TODO send data to accounting database
    }
}