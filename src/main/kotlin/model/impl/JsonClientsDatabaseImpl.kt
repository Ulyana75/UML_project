package model.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import model.ClientsDatabase
import objects.Client
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonClientsDatabaseImpl: ClientsDatabase {
    private val gson = Gson()
    private val filename = "D:\\Desktop\\LABS\\5ыуь\\UML\\Project\\ApartmentSystem\\src\\main\\kotlin\\model\\impl\\json\\clients.json"

    companion object {
        private var INSTANCE: JsonClientsDatabaseImpl? = null

        fun getInstance(): JsonClientsDatabaseImpl {
            if (INSTANCE == null) {
                INSTANCE = JsonClientsDatabaseImpl()
            }
            return INSTANCE!!
        }
    }

    override fun getAll(): List<Client> {
        if (isFileEmpty()) return listOf()
        return gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<Client>>() {}.type)
    }

    override fun getByPassportNumber(passportNumber: String): Client? {
        if (isFileEmpty()) return null
        val data: List<Client> = gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<Client>>() {}.type)
        return data.find { it.passportNumber == passportNumber }
    }

    override fun add(client: Client) {
        if (isFileEmpty()) {
            writeToFile("[" + gson.toJson(client) + "]")
            return
        }
        val data = getAll().toMutableList()
        data.add(client)
        writeToFile(gson.toJson(data))
    }

    override fun addAll(clients: List<Client>) {
        clients.map(::add)
    }

    override fun removeByPassportNumber(passportNumber: String) {
        if (isFileEmpty()) return
        val clients = getAll().toMutableList()
        val client = clients.find { it.passportNumber == passportNumber }
        if (client != null) {
            clients.remove(client)
            writeToFile(gson.toJson(clients))
        }
    }

    override fun clear() {
        writeToFile("")
    }

    override fun update(client: Client) {
        val data = getAll().toMutableList()
        data[data.indexOf(data.find { it.passportNumber == client.passportNumber })] = client
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