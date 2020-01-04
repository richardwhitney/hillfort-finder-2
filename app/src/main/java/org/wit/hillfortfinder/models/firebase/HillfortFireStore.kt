package org.wit.hillfortfinder.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortfinder.helpers.readImageFromPath
import org.wit.hillfortfinder.models.HillfortModel
import org.wit.hillfortfinder.models.HillfortStore
import java.io.ByteArrayOutputStream
import java.io.File

class HillfortFireStore(val context: Context): HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  lateinit var userId: String
  lateinit var db: DatabaseReference
  lateinit var st: StorageReference

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
      updateImage(hillfort)
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
    if ((hillfort.image.length) > 0 && (hillfort.image[0] != 'h')) {
      updateImage(hillfort)
    }
  }

  override fun delete(hillfort: HillfortModel) {
    db.child("users").child(userId).child("hillforts").child(hillfort.fbId).removeValue()
    hillforts.remove(hillfort)
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

  fun updateImage(hillfort: HillfortModel) {
    if (hillfort.image != "") {
      val fileName = File(hillfort.image)
      val imageName =  fileName.name

      st = FirebaseStorage.getInstance().reference
      var imageRef = st.child(userId + '/' + imageName)
      val baos = ByteArrayOutputStream()
      val bitmap = readImageFromPath(context, hillfort.image)

      bitmap?.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
          println(it.message)
        }.addOnSuccessListener { taskSnapshot ->
          taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
            hillfort.image = it.toString()
            db.child("users").child(userId).child("hillforts").child(hillfort.fbId).setValue(hillfort)
          }
        }
      }
    }
  }

}