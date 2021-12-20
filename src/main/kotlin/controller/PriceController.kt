package controller

import objects.Apartment

interface PriceController {
    fun calculateServicePrice(apartment: Apartment?): Float
    fun calculatePercentForCompany(apartment: Apartment?): Float
}