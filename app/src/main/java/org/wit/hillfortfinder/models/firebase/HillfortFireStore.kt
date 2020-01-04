package org.wit.hillfortfinder.models.firebase

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.HillfortStore

class HillfortFireStore(val context: Context): HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun findById(id: Long): HillfortModel? {
    return hillforts.find { p -> p.id == id }
  }

  override fun findByUserId(id: String): List<HillfortModel> {
    return hillforts.filter { p -> p.userId == userId }
  }

  override fun create(hillfort: HillfortModel) {
    val key = db.child("users").child(userId).child("hillforts").push().key
    key?.let {
      hillfort.fbId = key
      hillforts.add(hillfort)
      db.child("users").child(userId).child("hillforts").child(key).setValue(hillfort)
    }
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
    if (foundHillfort != null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.location = hillfort.location
      foundHillfort.favourited = hillfort.favourited
      foundHillfort.visited = hillfort.visited
      foundHillfort.rating = hillfort.rating
      foundHillfort.dateVisited = hillfort.dateVisited
      foundHillfort.additionalNotes = hillfort.additionalNotes
    }
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
  }

  override fun delete(hillfort: HillfortModel) {
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
  }

  override fun clear() {
    hillforts.clear()
  }

  fun fetchHillforts(hillfortsReady: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(dataSnapshot: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot!!.children.mapNotNullTo(hillforts) { it.getValue<HillfortModel>(HillfortModel::class.java) }
        hillfortsReady()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    hillforts.clear()
    db.child("users").child(userId).child("hillforts").addListenerForSingleValueEvent(valueEventListener)
  }

}