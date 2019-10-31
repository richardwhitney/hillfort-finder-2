package org.wit.hillfortfinder.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortfinder.helpers.exists
import org.wit.hillfortfinder.helpers.read
import org.wit.hillfortfinder.helpers.write


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
            user.id = generateRandomId()
            users.add(user)
            serialize()
            return true
        }
        return false
    }

    override fun loign(email: String, password: String): UserModel? {
        var foundUser: UserModel? = users.find { p -> p.email == email }
        if (foundUser?.password == password) {
            return foundUser
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
}