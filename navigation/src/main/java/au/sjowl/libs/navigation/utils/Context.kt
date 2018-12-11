package au.sjowl.libs.navigation.utils

import android.content.Context
import android.content.SharedPreferences

fun Context.getPrivateSharedPreferences(): SharedPreferences {
    return this.getSharedPreferences(packageName + "_preference", Context.MODE_PRIVATE)
}

fun Context.setProperty(tag: String, value: String) {
    try {
        val spe = getPrivateSharedPreferences().edit()
        spe.putString(tag, value).apply()
    } catch (ex: Exception) {
    }
}

fun Context.getProperty(tag: String, default_value: String): String {
    var result = default_value
    try {
        val sp = getPrivateSharedPreferences()
        result = sp.getString(tag, default_value).orEmpty()
    } catch (ex: Exception) {
    }
    return result
}