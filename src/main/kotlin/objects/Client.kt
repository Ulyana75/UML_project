package objects

import com.google.gson.annotations.SerializedName
import java.util.*

data class Client(
    @SerializedName("name")
    val name: String,

    @SerializedName("date_of_birth")
    val dateOfBirth: Date,

    @SerializedName("passport_number")
    val passportNumber: String,

    @SerializedName("contracts")
    val contracts: MutableList<Contract>
) {
    fun addNewContract(contract: Contract) {
        contracts.add(contract)
    }
}