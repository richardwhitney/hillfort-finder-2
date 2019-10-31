package org.wit.hillfortfinder.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun signup(user: UserModel): Boolean
    fun loign(email: String, password: String): UserModel?
    fun update(user: UserModel): UserModel?
}