package objects

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("id")
    val id: String,

    @SerializedName("client_passport_number")
    val clientPassportNumber: String,

    @SerializedName("apartment_id")
    val apartmentId: String?
)