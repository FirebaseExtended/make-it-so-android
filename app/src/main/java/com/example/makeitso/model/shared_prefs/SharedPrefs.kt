package com.example.makeitso.model.shared_prefs

interface SharedPrefs {
    fun saveCurrentUserId(userId: String)
    fun deleteCurrentUser(userId: String)
    fun getCurrentUserId(): String?
    fun hasUser(): Boolean
}
