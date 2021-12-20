package objects

import java.util.*

class Admin(
    id: String,
    name: String,
    dateOfBirth: Date,
    passportNumber: String,
    contracts: MutableList<Contract>,
    activeOrders: MutableList<Order>
): Employee(id, name, dateOfBirth, passportNumber, contracts, activeOrders) {
    constructor(employee: Employee) : this(
        employee.id, employee.name, employee.dateOfBirth, employee.passportNumber, employee.contracts, employee.activeOrders
    )
}