package objects

interface Apartment {
    val id: String
    val area: Float
    val rooms: Int
    val address: Address
    val price: Float
    val hasFurniture: Boolean
    val repairType: RepairType
    val description: String
}