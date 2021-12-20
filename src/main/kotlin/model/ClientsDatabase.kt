package model

import objects.Client

interface ClientsDatabase {
    fun getAll(): List<Client>

    fun getByPassportNumber(passportNumber: String): Client?

    fun add(client: Client)

    fun addAll(clients: List<Client>)

    fun removeByPassportNumber(passportNumber: String)

    fun clear()

    fun update(client: Client)
}