package model

import objects.ApartmentForSale

interface ApartmentsForSaleDatabase {
    fun getAll(): List<ApartmentForSale>

    fun getById(id: String): ApartmentForSale?

    fun add(apartment: ApartmentForSale)

    fun addAll(apartments: List<ApartmentForSale>)

    fun removeById(id: String)

    fun clear()

    fun update(apartment: ApartmentForSale)
}