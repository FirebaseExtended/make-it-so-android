package com.example.makeitso.model.shared_prefs

interface SharedPrefs {
    fun saveCurrentUserId(userId: Long)
    fun deleteCurrentUser()
    fun getCurrentUserId(): Long
    fun hasUser(): Boolean
}
