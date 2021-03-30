/*
 * Copyright (C) 2020.  Manel Cabezas Calderó
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package es.app.laliguilla.core.extension

import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Bitmap.toBase64String():String{
    ByteArrayOutputStream().apply {
        compress(Bitmap.CompressFormat.JPEG,90,this)
        return Base64.encodeToString(toByteArray(),Base64.DEFAULT)
    }
}

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String) {
    Snackbar.make(this, snackbarText, Snackbar.LENGTH_LONG).show()
}

fun View.showOrGone(value : Boolean){
   if(value){
       this.show()
   }else{
       this.gone()
   }
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Hide the view  (visibility = View.VISIBLE)
 */
fun View.gone() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}