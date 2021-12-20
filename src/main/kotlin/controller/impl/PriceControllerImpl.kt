package controller.impl

import controller.PriceController
import objects.Apartment
import java.util.*

class PriceControllerImpl: PriceController {
    private val random = Random()

    override fun calculateServicePrice(apartment: Apartment): Float {
        return getRandomFloat(10000.toFloat(), 300000.toFloat())
    }

    override fun calculatePercentForCompany(apartment: Apartment): Float {
        return getRandomFloat(5.toFloat(), 20.toFloat())
    }

    private fun getRandomFloat(left: Float, right: Float): Float {
        return left + random.nextFloat() * (right - left)
    }
}