package model

import objects.ApartmentForRent

interface ApartmentsForRentDatabase {
    fun getAll(): List<ApartmentForRent>

    fun getById(id: String): ApartmentForRent?

    fun add(apartment: ApartmentForRent)

    fun addAll(apartments: List<ApartmentForRent>)

    fun removeById(id: String)

    fun clear()

    fun update(apartment: ApartmentForRent)
}