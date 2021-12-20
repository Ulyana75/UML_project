package controller

import objects.Employee

interface FinishOrderController {
    fun finishOrder(employee: Employee, orderId: String)
}