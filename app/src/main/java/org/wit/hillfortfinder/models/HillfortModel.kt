package org.wit.hillfortfinder.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class HillfortModel(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: String = "",
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var visited: Boolean = false,
                         var userId: Long = 0,
                         var additionalNotes: String = "",
                         var dateVisited: String = "",
                         var rating: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat :Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable