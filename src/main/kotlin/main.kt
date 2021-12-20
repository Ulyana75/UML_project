import objects.Employee
import utilities.ApartmentSystemException
import view.View
import view.impl.CommandLineViewImpl

val view: View = CommandLineViewImpl()

fun main() {
    val employee = auth()

    var ans = showMenu()
    while (true) {
        try {
            val needToShowMenu = handleMenu(ans, employee)
            if (!needToShowMenu) break
        } catch(e: ApartmentSystemException) {
            println(e.message)
        }
        ans = showMenu()
    }
}

fun auth(): Employee {
    var employee: Employee? = null
    while (employee == null) {
        try {
            employee = view.enter()
        } catch (e: ApartmentSystemException) {
            println(e.message)
            continue
        }
    }
    println("Здравствуйте, ${employee.name}!")
    return employee
}

fun showMenu(): Int {
    println("Выберите, что вы хотите сделать.\n" +
            "0-создать квартиру на продажу\n" +
            "1-создать квартиру на сдачу\n" +
            "2-создать заказ\n" +
            "3-поиск клиента в базе\n" +
            "4-добавить клиента в базу\n" +
            "5-создать договор\n" +
            "6-поиск квартиры для покупки\n" +
            "7-поиск квартиры для аренды\n" +
            "8-завершить заказ\n" +
            "9-добавить работника\n" +
            "10-забронировать квартиру\n" +
            "11-завершить работу с программой")
    return readLine()!!.toInt()
}

fun handleMenu(ans: Int, employee: Employee): Boolean {
    when (ans) {
        0 -> view.createApartmentForSale()
        1 -> view.createApartmentForRent()
        2 -> view.createOrder(employee)
        3 -> view.searchClient()
        4 -> view.createClient()
        5 -> view.createContract(employee)
        6 -> view.searchApartmentForSale()
        7 -> view.searchApartmentForRent()
        8 -> view.finishOrder(employee)
        9 -> view.createEmployee(employee)
        10 -> view.bookApartment()
        11 -> return false
    }
    return true
}