package model.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import model.ApartmentsForRentDatabase
import objects.ApartmentForRent
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonApartmentsForRentDatabaseImpl: ApartmentsForRentDatabase {
    private val gson = Gson()
    private val filename = "D:\\Desktop\\LABS\\5ыуь\\UML\\Project\\ApartmentSystem\\src\\main\\kotlin\\model\\impl\\json\\apartments_for_rent.json"

    companion object {
        private var INSTANCE: JsonApartmentsForRentDatabaseImpl? = null

        fun getInstance(): JsonApartmentsForRentDatabaseImpl {
            if (INSTANCE == null) {
                INSTANCE = JsonApartmentsForRentDatabaseImpl()
            }
            return INSTANCE!!
        }
    }

    override fun getAll(): List<ApartmentForRent> {
        if (isFileEmpty()) return listOf()
        return gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<ApartmentForRent>>() {}.type)
    }

    override fun getById(id: String): ApartmentForRent? {
        if (isFileEmpty()) return null
        val data: List<ApartmentForRent> = gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<ApartmentForRent>>() {}.type)
        return data.find { it.id == id }
    }

    override fun add(apartment: ApartmentForRent) {
        if (isFileEmpty()) {
            writeToFile("[" + gson.toJson(apartment) + "]")
            return
        }
        val data = getAll().toMutableList()
        data.add(apartment)
        writeToFile(gson.toJson(data))
    }

    override fun addAll(apartments: List<ApartmentForRent>) {
        apartments.map(::add)
    }

    override fun removeById(id: String) {
        if (isFileEmpty()) return
        val apartments = getAll().toMutableList()
        val apartment = apartments.find { it.id == id }
        if (apartment != null) {
            apartments.remove(apartment)
            writeToFile(gson.toJson(apartments), false)
        }
    }

    override fun clear() {
        writeToFile("", false)
    }

    override fun update(apartment: ApartmentForRent) {
        val data = getAll().toMutableList()
        data[data.indexOf(data.find { it.id == apartment.id })] = apartment
        writeToFile(gson.toJson(data))
    }

    private fun writeToFile(text: String, append: Boolean = true) {
        val writer = FileWriter(filename, append)
        writer.write(text)
        writer.close()
    }

    private fun isFileEmpty(): Boolean {
        val file = File(filename)
        return file.length() == 0L
    }
}