package objects

import com.google.gson.annotations.SerializedName
import java.util.*

data class Contract(
    @SerializedName("date")
    val date: Date,

    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("employee_id")
    val employee_id: String,

    @SerializedName("service_price")
    val servicePrice: Float,

    @SerializedName("percent_to_company")
    val percentToCompany: Float,

    @SerializedName("apartment_id")
    val apartmentId: String? = null
)