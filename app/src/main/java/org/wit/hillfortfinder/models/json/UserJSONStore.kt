
package org.wit.hillfortfinder.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortfinder.helpers.exists
import org.wit.hillfortfinder.helpers.read
import org.wit.hillfortfinder.helpers.write
import org.wit.hillfortfinder.models.UserModel
import org.wit.hillfortfinder.models.UserStore


val UJSON_FILE = "users.json"
val uGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val uListType = object: TypeToken<java.util.ArrayList<UserModel>>() {}.type

class UserJSONStore: UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor(context: Context) {
        this.context = context
        if (exists(context, UJSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun signup(user: UserModel): Boolean {
        var foundUser: UserModel? = users.find { p -> p.email == user.email }
        if (foundUser == null) {
            user.id = generateRandomId().toString()
            users.add(user)
            serialize()
            return true
        }
        return false
    }

    override fun loign(email: String, password: String): UserModel? {
        var foundUser: UserModel? = users.find { p -> p.email == email }
        if (foundUser?.password == password) {
            logAll()
            return foundUser
        }
        else {
            return null
        }
    }

    override fun update(user: UserModel): UserModel? {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            var emailExists = users.any { p -> p.email == user.email }
            if (!emailExists) {
                foundUser.email = user.email
                foundUser.password = user.password
                serialize()
                return foundUser
            }
            return null
        }
        else {
            return null
        }
    }

    private fun serialize() {
        val jsonString = uGsonBuilder.toJson(users, uListType)
        write(context, UJSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, UJSON_FILE)
        users = Gson().fromJson(jsonString, uListType)
    }

    fun logAll() {
        users.forEach { info("$it") }
    }
}