package org.wit.hillfortfinder.room

import androidx.room.*
import org.wit.hillfortfinder.models.HillfortModel

@Dao
interface HillfortDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hillfort: HillfortModel)

    @Query("SELECT * FROM HillfortModel")
    fun findAll(): List<HillfortModel>

    @Query("SELECT * FROM HillfortModel WHERE id = :id")
    fun findById(id: Long): HillfortModel

    @Query("SELECT * FROM HillfortModel WHERE userId = :id")
    fun findByUserId(id: String): List<HillfortModel>

    @Update
    fun update(hillfort: HillfortModel)

    @Delete
    fun deleteHillfort(hillfort: HillfortModel)
}