package objects

import com.google.gson.annotations.SerializedName

data class ApartmentForSale(
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
) : Apartment {
}