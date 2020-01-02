package org.wit.hillfortfinder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id: String = "0",
                     var email: String = "",
                     var password: String = "") : Parcelable