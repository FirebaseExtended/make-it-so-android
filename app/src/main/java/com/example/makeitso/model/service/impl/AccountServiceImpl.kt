package com.example.makeitso.model.service.impl

import com.example.makeitso.model.service.AccountService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun isAnonymousUser(): Boolean {
        return Firebase.auth.currentUser?.isAnonymous ?: true
    }

    override fun getUserId(): String {
        return if (isAnonymousUser()) getAnonymousUserId()
        else Firebase.auth.currentUser?.uid.orEmpty()
    }

    override fun getAnonymousUserId(): String {
        return ANONYMOUS_ID
    }

    override fun authenticate(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> callback(task) }
    }

    override fun createAccount(email: String, password: String, callback: (Task<AuthResult>) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> callback(task) }
    }

    override fun sendRecoveryEmail(email: String, callback: (Throwable?) -> Unit) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task -> callback(task.exception) }
    }

    override fun createAnonymousAccount(callback: (Task<AuthResult>) -> Unit) {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { task -> callback(task) }
    }

    override fun linkAccount(email: String, password: String, callback: () -> Unit) {
        val credential = EmailAuthProvider.getCredential(email, password)

        Firebase.auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { callback() }
    }

    override fun deleteAccount(callback: (Throwable?) -> Unit) {
        Firebase.auth.currentUser!!.delete()
            .addOnCompleteListener { task -> callback(task.exception) }
    }

    override fun signOut() {
        Firebase.auth.signOut()
    }

    companion object {
        private const val ANONYMOUS_ID = "ANONYMOUS_ID"
    }
}