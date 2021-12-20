package objects

import com.google.gson.annotations.SerializedName
import java.util.*

open class Employee(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: Date,

    @SerializedName("passport_number")
    val passportNumber: String,

    @SerializedName("contracts")
    val contracts: MutableList<Contract>,

    @SerializedName("active_orders")
    val activeOrders: MutableList<Order>
) {
    fun addNewActiveOrder(order: Order) {
        activeOrders.add(order)
    }

    fun addNewContract(contract: Contract) {
        contracts.add(contract)
    }
}