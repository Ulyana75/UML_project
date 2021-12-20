package objects

import com.google.gson.annotations.SerializedName


data class Address(
    @SerializedName("city")
    val city: String,

    @SerializedName("street")
    val street: String,

    @SerializedName("house")
    val house: String,

    @SerializedName("apartment_number")
    val apartmentNumber: String
)