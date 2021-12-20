package model.impl

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import model.EmployeeDatabase
import objects.Admin
import objects.Employee
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonEmployeeDatabaseImpl: EmployeeDatabase {
    private val gson = Gson()
    private val filename = "D:\\Desktop\\LABS\\5ыуь\\UML\\Project\\ApartmentSystem\\src\\main\\kotlin\\model\\impl\\json\\employees.json"

    companion object {
        private var INSTANCE: JsonEmployeeDatabaseImpl? = null

        fun getInstance(): JsonEmployeeDatabaseImpl {
            if (INSTANCE == null) {
                INSTANCE = JsonEmployeeDatabaseImpl()
            }
            return INSTANCE!!
        }
    }

    override fun getAll(): List<Employee> {
        if (isFileEmpty()) return listOf()
        val data = getRawData()
        return data.map {
            if (it.isAdmin) Admin(it.employee)
            else it.employee
        }
    }

    override fun getById(id: String): Employee? {
        if (isFileEmpty()) return null
        val data = getRawData()
        val employeeObj = data.find { it.employee.id == id }
        if (employeeObj != null) {
            if (employeeObj.isAdmin) {
                return Admin(employeeObj.employee)
            }
        }
        return employeeObj?.employee
    }

    override fun isAdmin(id: String): Boolean {
        if (isFileEmpty()) return false
        val data = getRawData()
        val employee = data.filter { it.employee.id == id }
        if (employee.isEmpty() || !employee[0].isAdmin) {
            return false
        }
        return true
    }

    override fun add(employee: Employee) {
        val employeeObject = toEmployeeObject(employee)

        if (isFileEmpty()) {
            writeToFile("[" + gson.toJson(employeeObject) + "]")
            return
        }
        val data = getRawData().toMutableList()
        data.add(employeeObject)
        writeToFile(gson.toJson(data))
    }

    override fun addAll(employees: List<Employee>) {
        employees.map(::add)
    }

    override fun removeById(id: String) {
        if (isFileEmpty()) return
        val data = getRawData().toMutableList()
        val employeeObj = data.find { it.employee.id == id }
        if (employeeObj != null) {
            data.remove(employeeObj)
            writeToFile(gson.toJson(data))
        }
    }

    override fun clear() {
        writeToFile("")
    }

    override fun update(employee: Employee) {
        val data = getAll().toMutableList()
        data[data.indexOf(data.find { it.id == employee.id })] = employee
        writeToFile(gson.toJson(data.map { toEmployeeObject(it) }))
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

    private fun getRawData(): List<EmployeeObject> {
        return gson.fromJson(JsonReader(FileReader(filename)), object : TypeToken<List<EmployeeObject>>() {}.type)
    }

    private fun toEmployeeObject(employee: Employee): EmployeeObject {
        return if (employee is Admin) {
            EmployeeObject(employee, true)
        } else {
            EmployeeObject(employee, false)
        }
    }

    private class EmployeeObject(
        @SerializedName("employee")
        val employee: Employee,

        @SerializedName("is_admin")
        val isAdmin: Boolean
    )
}