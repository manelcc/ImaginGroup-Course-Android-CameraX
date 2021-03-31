package es.imagingroup.exampleviewmodelhiltdb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val name: String = "", val lastName: String ="", val age: Int=0):Parcelable
