package model

import objects.Employee

interface EmployeeDatabase {
    fun getAll(): List<Employee>

    fun getById(id: String): Employee?

    fun isAdmin(id: String): Boolean

    fun add(employee: Employee)

    fun addAll(employees: List<Employee>)

    fun removeById(id: String)

    fun clear()

    fun update(employee: Employee)
}