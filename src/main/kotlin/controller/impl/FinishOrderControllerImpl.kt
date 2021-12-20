package controller.impl

import controller.FinishOrderController
import controller.SearchController
import objects.Employee
import utilities.ApartmentSystemException

class FinishOrderControllerImpl: FinishOrderController {
    private val searchController: SearchController

    init {
        searchController = SearchControllerImpl()
    }

    override fun finishOrder(employee: Employee, orderId: String) {
        val id = readLine()!!
        val order = employee.activeOrders.find { it.id == id } ?: throw ApartmentSystemException("No order with that ID")
        val apartment
        employee.activeOrders.remove(order)
        // TODO send data to accounting database
    }

    // TODO изменить создание клиента с учетом типа. Добавить дефолтное для квартиры нул. При завершении заказа проверять тип клиента
    // и если он селлер удалять квартиру из бд
}