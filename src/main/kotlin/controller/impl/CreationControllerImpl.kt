package controller.impl

import controller.CreationController
import model.ApartmentsForRentDatabase
import model.ApartmentsForSaleDatabase
import model.ClientsDatabase
import model.EmployeeDatabase
import model.impl.JsonApartmentsForRentDatabaseImpl
import model.impl.JsonApartmentsForSaleDatabaseImpl
import model.impl.JsonClientsDatabaseImpl
import model.impl.JsonEmployeeDatabaseImpl
import objects.*
import utilities.ApartmentSystemException
import java.util.*

class CreationControllerImpl: CreationController {
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

    override fun createClient(
        name: String,
        dateOfBirth: Date,
        passportNumber: String,
        contracts: MutableList<Contract>
    ): Client {
        val client = Client(name, dateOfBirth, passportNumber, contracts)
        clientsDatabase.add(client)
        return client
    }

    override fun createApartmentForSale(
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
    ): ApartmentForSale {
        val id = UUID.randomUUID().toString()
        val address = Address(city, street, house, apartmentNumber)
        val apartment = ApartmentForSale(id, area, rooms, address, price, hasFurniture, repairType, description)
        apartmentsForSaleDatabase.add(apartment)
        return apartment
    }

    override fun createApartmentForRent(
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
    ): ApartmentForRent {
        val id = UUID.randomUUID().toString()
        val address = Address(city, street, house, apartmentNumber)
        val apartment = ApartmentForRent(id, area, rooms, address, price, hasFurniture, repairType, description, busyDates.toMutableSet(), onlyLongTerm, canChildren, canPets)
        apartmentsForRentDatabase.add(apartment)
        return apartment
    }

    override fun createEmployee(
        author: Employee,
        name: String,
        dateOfBirth: Date,
        passportNumber: String,
        contracts: MutableList<Contract>,
        activeOrders: MutableList<Order>
    ): Employee {
        if (!employeeDatabase.isAdmin(author.id)) {
            throw ApartmentSystemException("Only admin can create employees")
        }
        val id = UUID.randomUUID().toString()
        val employee = Employee(id, name, dateOfBirth, passportNumber, contracts, activeOrders)
        employeeDatabase.add(employee)
        return employee
    }

    override fun createOrder(
        employee: Employee,
        client: Client,
        apartment: Apartment
    ): Order {
        val id = UUID.randomUUID().toString()
        val order = Order(id, client.passportNumber, apartment.id)
        employee.addNewActiveOrder(order)
        employeeDatabase.update(employee)
        return order
    }

    override fun createContract(
        client: Client,
        employee: Employee,
        servicePrice: Float,
        percentToCompany: Float,
        apartment: Apartment
    ): Contract {
        val date = Date()
        val contract = Contract(date, client.passportNumber, employee.id, servicePrice, percentToCompany, apartment.id)
        employee.addNewContract(contract)
        client.addNewContract(contract)
        employeeDatabase.update(employee)
        clientsDatabase.update(client)
        return contract
    }
}