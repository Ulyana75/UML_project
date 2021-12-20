package model.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import model.ApartmentsForSaleDatabase
import objects.ApartmentForSale
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonApartmentsForSaleDatabaseImpl: ApartmentsForSaleDatabase {
    private val gson = Gson()
    private val filename = "D:\\Desktop\\LABS\\5ыуь\\UML\\Project\\ApartmentSystem\\src\\main\\kotlin\\model\\impl\\json\\apartments_for_sale.json"

    companion object {
        private var INSTANCE: JsonApartmentsForSaleDatabaseImpl? = null

        fun getInstance(): JsonApartmentsForSaleDatabaseImpl {
            if (INSTANCE == null) {
                INSTANCE = JsonApartmentsForSaleDatabaseImpl()
            }
            return INSTANCE!!
        }
    }

    override fun getAll(): List<ApartmentForSale> {
        if (isFileEmpty()) return listOf()
        return gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<ApartmentForSale>>() {}.type)
    }

    override fun getById(id: String): ApartmentForSale? {
        if (isFileEmpty()) return null
        val data: List<ApartmentForSale> = gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<ApartmentForSale>>() {}.type)
        return data.find { it.id == id }
    }

    override fun add(apartment: ApartmentForSale) {
        if (isFileEmpty()) {
            writeToFile("[" + gson.toJson(apartment) + "]")
            return
        }
        val data = getAll().toMutableList()
        data.add(apartment)
        writeToFile(gson.toJson(data))
    }

    override fun addAll(apartments: List<ApartmentForSale>) {
        apartments.map(::add)
    }

    override fun removeById(id: String) {
        if (isFileEmpty()) return
        val apartments = getAll().toMutableList()
        val apartment = apartments.find { it.id == id }
        if (apartment != null) {
            apartments.remove(apartment)
            writeToFile(gson.toJson(apartments))
        }
    }

    override fun clear() {
        writeToFile("")
    }

    override fun update(apartment: ApartmentForSale) {
        val data = getAll().toMutableList()
        data[data.indexOf(data.find { it.id == apartment.id })] = apartment
        writeToFile(gson.toJson(data))
    }

    private fun writeToFile(text: String, append: Boolean = false) {
        val writer = FileWriter(filename, append)
        writer.write(text)
        writer.close()
    }

    private fun isFileEmpty(): Boolean {
        val file = File(filename)
        return file.length() == 0L
    }
}