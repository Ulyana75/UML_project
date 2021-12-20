package view.impl

import controller.AuthController
import utilities.ApartmentSystemException
import controller.CreationController
import controller.PriceController
import controller.SearchController
import controller.impl.AuthControllerImpl
import controller.impl.CreationControllerImpl
import controller.impl.PriceControllerImpl
import controller.impl.SearchControllerImpl
import objects.*
import view.View
import java.text.SimpleDateFormat
import java.util.*

class CommandLineViewImpl: View {
    override val creationController: CreationController
    override val searchController: SearchController
    override val priceController: PriceController
    override val authController: AuthController

    private val dateFormat = "dd.MM.yyyy"

    init {
        creationController = CreationControllerImpl()
        searchController = SearchControllerImpl()
        priceController = PriceControllerImpl()
        authController = AuthControllerImpl()
    }

    override fun enter(): Employee {
        println("Введите ваш ID:")
        val id = readLine()!!
        return authController.enter(id)
    }

    override fun createApartmentForRent() {
        println("Введите площадь:")
        val area = readLine()!!.toFloat()
        println("Введите количество комнат:")
        val rooms = readLine()!!.toInt()
        println("Введите город:")
        val city = readLine()!!
        println("Введите улицу:")
        val street = readLine()!!
        println("Введите номер дома:")
        val house = readLine()!!
        println("Введите номер квартиры:")
        val apartmentNumber = readLine()!!
        println("Введите цену в рублях::")
        val price = readLine()!!.toFloat()
        println("В квартире есть мебель? Напишите да/нет:")
        val hasFurniture = readLine()!! == "да"
        println("Введите тип ремонта. Возможные варианты: ELITE_RENOVATION, BASIC_REPAIR, NO_REPAIR")
        val repairType = RepairType.valueOf(readLine()!!)
        println("Введите описание:")
        val description = readLine()!!
        println("Введите занятые даты через пробел в формате $dateFormat:")
        val datesString = readLine()!!
        var busyDates = listOf<Date>()
        if (datesString.isNotEmpty()) {
            busyDates = datesString.split(" ").map {
                SimpleDateFormat(dateFormat).parse(it)
            }
        }
        println("Возможна сдача квартиры посуточно? Напишите да/нет:")
        val onlyLongTerm = readLine()!! != "да"
        println("Можно с детьми? Напишите да/нет:")
        val canChildren = readLine()!! == "да"
        println("Можно с животными? Напишите да/нет:")
        val canPets = readLine()!! == "да"

        creationController.createApartmentForRent(
            area, rooms, city, street, house, apartmentNumber, price, hasFurniture, repairType, description, busyDates, onlyLongTerm, canChildren, canPets
        )

        println("Квартира успешно создана и добавлена в базу данных")
    }

    override fun createApartmentForSale() {
        println("Введите площадь:")
        val area = readLine()!!.toFloat()
        println("Введите количество комнат:")
        val rooms = readLine()!!.toInt()
        println("Введите город:")
        val city = readLine()!!
        println("Введите улицу:")
        val street = readLine()!!
        println("Введите номер дома:")
        val house = readLine()!!
        println("Введите номер квартиры:")
        val apartmentNumber = readLine()!!
        println("Введите цену в рублях::")
        val price = readLine()!!.toFloat()
        println("В квартире есть мебель? Напишите да/нет:")
        val hasFurniture = readLine()!! == "да"
        println("Введите тип ремонта. Возможные варианты: ELITE_RENOVATION, BASIC_REPAIR, NO_REPAIR")
        val repairType = RepairType.valueOf(readLine()!!)
        println("Введите описание:")
        val description = readLine()!!

        val id = creationController.createApartmentForSale(
            area, rooms, city, street, house, apartmentNumber, price, hasFurniture, repairType, description
        ).id

        println("Квартира успешно создана и добавлена в базу данных. " +
                "ID квартиры: $id")
    }

    override fun createOrder(employee: Employee) {
        println("Введите номер паспорта клиента:")
        val passportNumber = readLine()!!
        val client = searchController.searchClientByPassportNumber(passportNumber)
            ?: throw ApartmentSystemException("No client with that passport number")
        println("Введите ID квартиры:")
        val id = readLine()!!
        val apartment = searchController.searchApartmentById(id)
            ?: throw ApartmentSystemException("No apartment with that ID")

        val orderId = creationController.createOrder(
            employee, client, apartment
        ).id

        println("Заказ успешно создан и добавлен в активные заказы. " +
                "ID заказа: $orderId")
    }

    override fun createClient() {
        println("Введите ФИО:")
        val name = readLine()!!
        println("Введите дату рождения в формате $dateFormat:")
        val dateOfBirth = SimpleDateFormat(dateFormat).parse(readLine()!!)
        println("Введите серию и номер паспорта без пробела:")
        val passportNumber = readLine()!!
        creationController.createClient(name, dateOfBirth, passportNumber, mutableListOf())
        println("Клиент успешно создан и добавлен в базу данных")
    }

    override fun createContract(employee: Employee) {
        println("Введите номер паспорта клиента:")
        val passportNumber = readLine()!!
        val client = searchController.searchClientByPassportNumber(passportNumber)
            ?: throw ApartmentSystemException("No client with that passport number")
        println("Введите ID квартиры:")
        val id = readLine()!!
        val apartment = searchController.searchApartmentById(id)
            ?: throw ApartmentSystemException("No apartment with that ID")

        var servicePrice = priceController.calculateServicePrice(apartment)
        var percentToCompany = priceController.calculatePercentForCompany(apartment)
        println("Предложенная цена услуги: $servicePrice. " +
                "Предложенный процент агенству: $percentToCompany. " +
                "Устраивает цена и процент? Напишите да/нет:")

        if (readLine()!! != "да") {
            println("Введите стоимость услуги:")
            servicePrice = readLine()!!.toFloat()
            println("Введите процент:")
            percentToCompany = readLine()!!.toFloat()
        }

        creationController.createContract(client, employee, servicePrice, percentToCompany, apartment)
        println("Контракт создан успешно")
    }

    override fun createEmployee(author: Employee) {
        println("Введите имя:")
        val name = readLine()!!
        println("Введите дату рождения в формате $dateFormat:")
        val dateOfBirth = SimpleDateFormat(dateFormat).parse(readLine()!!)
        println("Введите серию и номер паспорта без пробелов:")
        val passportNumber = readLine()!!
        val id = creationController.createEmployee(
            author, name, dateOfBirth, passportNumber, mutableListOf(), mutableListOf()
        ).id
        println("Работник создан успешно и добавлен в базу данных. " +
                "ID работника: $id")
    }

    override fun searchApartmentForSale() {
        println("Choose parameters with witch you want search:\n" +
                "0-areaMin 1-areaMax\n2-roomsMin 3-roomsMax\n" +
                "4-priceMin 5-priceMax\n6-hasFurniture 7-repairType\n" +
                "8-finish entering parameters")
        var answer = 9
        var areaMin: Float? = null; var areaMax: Float? = null
        var roomsMin: Int? = null; var roomsMax: Int? = null
        var priceMin: Float? = null; var priceMax: Float? = null
        var hasFurniture: Boolean? = null; var repairType: List<RepairType>? = null
        while (answer != 8) {
            println("Введите номер параметра:")
            answer = readLine()!!.toInt()
            when (answer) {
                0 -> {
                    println("Введите минимальную площадь:")
                    areaMin = readLine()!!.toFloat()
                }
                1 -> {
                    println("Введите максимальную площадь:")
                    areaMax = readLine()!!.toFloat()
                }
                2 -> {
                    println("Введите минимальное количество комнат:")
                    roomsMin = readLine()!!.toInt()
                }
                3 -> {
                    println("Введите максимальное количество комнат:")
                    roomsMax = readLine()!!.toInt()
                }
                4 -> {
                    println("Введите минимальную стоимость:")
                    priceMin = readLine()!!.toFloat()
                }
                5 -> {
                    println("Введите максимальную стоимость:")
                    priceMax = readLine()!!.toFloat()
                }
                6 -> {
                    println("Нужна мебель? Напишите да/нет:")
                    hasFurniture = readLine()!! == "да"
                }
                7 -> {
                    println("Перечислите возможные типы ремонта через пробел. " +
                            "Возможные варианты: ELITE_RENOVATION, BASIC_REPAIR, NO_REPAIR")
                    repairType = readLine()!!.split(" ").map { RepairType.valueOf(it) }
                }
            }
        }
        val apartments = searchController.searchApartmentForSale(
            areaMin, areaMax, roomsMin, roomsMax, priceMin, priceMax, hasFurniture, repairType
        )
        println("Квартиры по вашим критериям:")
        for (apartment in apartments) {
            println(apartment.toString())
        }
    }

    override fun searchApartmentForRent() {
        println("Choose parameters with witch you want search:\n" +
                "0-areaMin 1-areaMax\n2-roomsMin 3-roomsMax\n" +
                "4-priceMin 5-priceMax\n6-hasFurniture 7-repairType\n" +
                "8-requiredDates 9-onlyLongTerm\n10-canPets 11-canChildren" +
                "12-finish entering parameters")
        var answer = 13
        var areaMin: Float? = null; var areaMax: Float? = null
        var roomsMin: Int? = null; var roomsMax: Int? = null
        var priceMin: Float? = null; var priceMax: Float? = null
        var hasFurniture: Boolean? = null; var repairType: List<RepairType>? = null
        var requiredDates: List<Date>? = null; var onlyLongTerm: Boolean? = null
        var canChildren: Boolean? = null; var canPets: Boolean? = null
        while (answer != 12) {
            println("Введите номер параметра:")
            answer = readLine()!!.toInt()
            when (answer) {
                0 -> {
                    println("Введите минимальную площадь:")
                    areaMin = readLine()!!.toFloat()
                }
                1 -> {
                    println("Введите максимальную площадь:")
                    areaMax = readLine()!!.toFloat()
                }
                2 -> {
                    println("Введите минимальное количество комнат:")
                    roomsMin = readLine()!!.toInt()
                }
                3 -> {
                    println("Введите максимальное количество комнат:")
                    roomsMax = readLine()!!.toInt()
                }
                4 -> {
                    println("Введите минимальную стоимость:")
                    priceMin = readLine()!!.toFloat()
                }
                5 -> {
                    println("Введите максимальную стоимость:")
                    priceMax = readLine()!!.toFloat()
                }
                6 -> {
                    println("Нужна мебель? Напишите да/нет:")
                    hasFurniture = readLine()!! == "да"
                }
                7 -> {
                    println("Перечислите возможные типы ремонта через пробел. " +
                            "Возможные варианты: ELITE_RENOVATION, BASIC_REPAIR, NO_REPAIR")
                    repairType = readLine()!!.split(" ").map { RepairType.valueOf(it) }
                }
                8 -> {
                    println("Введите необходимые даты через пробел в формате $dateFormat:")
                    val datesString = readLine()!!
                    if (datesString.isNotEmpty()) {
                        requiredDates = datesString.split(" ").map { SimpleDateFormat(dateFormat).parse(it) }
                    }
                }
                9 -> {
                    println("Квартира нужна на долгий период? Напишите да/нет:")
                    onlyLongTerm = readLine()!! == "да"
                }
                10 -> {
                    println("В квартиру можно с детьми? Напишите да/нет:")
                    canChildren = readLine()!! == "да"
                }
                11 -> {
                    println("В квартиру можно с животными? Напишите да/нет:")
                    canPets = readLine()!! == "да"
                }
            }
        }
        val apartments = searchController.searchApartmentForRent(
            areaMin, areaMax, roomsMin, roomsMax, priceMin, priceMax, hasFurniture, repairType,
            requiredDates, onlyLongTerm, canChildren, canPets
        )
        println("Квартиры по вашим критериям:")
        for (apartment in apartments) {
            println(apartment.toString())
        }
    }

    override fun bookApartment() {
        println("Введите ID квартиры:")
        val id = readLine()!!
        val apartment = searchController.searchApartmentById(id)
            ?: throw ApartmentSystemException("No apartment with that ID")
        if (apartment !is ApartmentForRent) {
            throw ApartmentSystemException("This apartment is not for rent")
        }
        println("Введите необходимые даты через пробел в формате $dateFormat:")
        val dates1 = readLine()!!.split(" ").map { SimpleDateFormat(dateFormat).parse(it) }
        apartment.book(dates1)
        println("Квартира успешно забронирована на выбранные даты")
    }

    override fun searchClient() {
        println("Введите номер и серию паспорта клиента:")
        val passportNumber = readLine()!!
        val client = searchController.searchClientByPassportNumber(passportNumber)
        if (client == null) {
            println("Клиента с таким паспортом нет в базе данных")
        } else {
            println("Клиент найден: $client")
        }
    }

    override fun searchApartment() {
        println("Введите ID квартиры:")
        val id = readLine()!!
        val apartment = searchController.searchClientByPassportNumber(id)
        if (apartment == null) {
            println("Квартиры с таким ID нет в базе данных")
        } else {
            println("Квартира найдена: $apartment")
        }
    }

    override fun finishOrder(employee: Employee) {
        println("Введите ID заказа:")
        val id = readLine()!!
        val order = employee.activeOrders.find { it.id == id } ?: throw ApartmentSystemException("No order with that ID")
        employee.activeOrders.remove(order)
        println("Заказ успешно завершен")
    }
}