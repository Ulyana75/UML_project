package controller

import objects.Employee

interface AuthController {
    fun enter(id: String): Employee
}