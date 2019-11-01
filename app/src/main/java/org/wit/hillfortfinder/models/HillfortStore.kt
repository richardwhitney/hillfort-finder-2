package org.wit.hillfortfinder.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun findByUserId(id: Long): List<HillfortModel>
    fun create(hillfort: HillfortModel)
    fun update(hillfort: HillfortModel)
    fun delete(hillfort: HillfortModel)
}