package com.example.makeitso.model.shared_prefs.impl

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsImpl @Inject constructor(@ApplicationContext context : Context) : SharedPrefs {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override fun saveCurrentUserId(userId: String) {
        prefs.edit().putString(USER_ID, userId).apply()
    }

    override fun deleteCurrentUser(userId: String) {
        prefs.edit().remove(USER_ID).apply()
    }

    override fun getCurrentUserId(): String? {
        return prefs.getString(USER_ID, "")
    }

    override fun hasUser(): Boolean {
        val currentUserId = getCurrentUserId()
        return currentUserId != null && currentUserId.isNotBlank()
    }

    companion object {
        private const val USER_ID = "USER_ID"
    }
}