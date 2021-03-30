/*
 * Copyright (C) 2020.  Manel Cabezas
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
package es.imagingroup.exampleviewmodelhiltdb.core.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


inline fun <reified T : AppCompatActivity> Context.launchActivity(
    origin: FragmentActivity,
    noinline init: Intent.() -> Unit = {}
) {
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(origin)
    val intent = newIntent<T>(this)
    intent.init()
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent, options.toBundle())
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)


/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun FragmentActivity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}


/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

val FragmentActivity.isConnected: Boolean
    get() {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

fun RecyclerView.lineUpGridSpan(reverseLayout:Boolean = false) {
    layoutManager = GridLayoutManager(context, 5, RecyclerView.HORIZONTAL, reverseLayout).also {
        it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 5
                    else -> 1
                }
            }
        }
    }
}