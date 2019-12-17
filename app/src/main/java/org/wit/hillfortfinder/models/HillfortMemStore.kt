package org.wit.hillfortfinder.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

//TODO( "use uuid instead")
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore: HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(id: Long): HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    override fun findByUserId(id: Long): List<HillfortModel> {
        return hillforts.filter { p -> p.userId == id }
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.visited = hillfort.visited
            foundHillfort.dateVisited = hillfort.dateVisited
            foundHillfort.additionalNotes = hillfort.additionalNotes
            foundHillfort.image = hillfort.image
            foundHillfort.rating = hillfort.rating
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            logAll()
        }
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
    }

    fun logAll() {
        hillforts.forEach { info("$it") }
    }
}