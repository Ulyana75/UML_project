package controller.impl

import controller.AuthController
import model.EmployeeDatabase
import model.impl.JsonEmployeeDatabaseImpl
import objects.Employee
import utilities.ApartmentSystemException

class AuthControllerImpl: AuthController {
    private val employeeDatabase: EmployeeDatabase

    init {
        employeeDatabase = JsonEmployeeDatabaseImpl.getInstance()
    }

    override fun enter(id: String): Employee {
        return employeeDatabase.getById(id) ?: throw ApartmentSystemException("Wrong ID")
    }
}