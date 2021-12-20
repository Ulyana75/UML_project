package objects

import com.google.gson.annotations.SerializedName
import controller.Booking
import java.util.*

data class ApartmentForRent(
    @SerializedName("id")
    override val id: String,

    @SerializedName("area")
    override val area: Float,

    @SerializedName("rooms")
    override val rooms: Int,

    @SerializedName("address")
    override val address: Address,

    @SerializedName("price")
    override val price: Float,

    @SerializedName("has_furniture")
    override val hasFurniture: Boolean,

    @SerializedName("repair_type")
    override val repairType: RepairType,

    @SerializedName("description")
    override val description: String,

    @SerializedName("busy_dates")
    val busyDates: MutableSet<Date>,

    @SerializedName("only_long_term")
    val onlyLongTerm: Boolean,

    @SerializedName("can_children")
    val canChildren: Boolean,

    @SerializedName("can_pets")
    val canPets: Boolean
) : Apartment, Booking {
    override fun book(dates: List<Date>) {
        dates.map {
            busyDates.add(it)
        }
    }
}