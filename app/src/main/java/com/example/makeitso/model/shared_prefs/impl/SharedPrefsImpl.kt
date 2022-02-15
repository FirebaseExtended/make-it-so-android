package com.example.makeitso.model.shared_prefs.impl

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.makeitso.model.shared_prefs.SharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsImpl @Inject constructor(@ApplicationContext context : Context) : SharedPrefs {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override fun saveCurrentUserId(userId: Long) {
        prefs.edit().putLong(USER_ID, userId).apply()
    }

    override fun deleteCurrentUser(userId: String) {
        prefs.edit().remove(USER_ID).apply()
    }

    override fun getCurrentUserId(): Long {
        return prefs.getLong(USER_ID, DEFAULT_ID)
    }

    override fun hasUser(): Boolean {
        return getCurrentUserId() != DEFAULT_ID
    }

    companion object {
        private const val USER_ID = "USER_ID"
        private const val DEFAULT_ID = -1L
    }
}