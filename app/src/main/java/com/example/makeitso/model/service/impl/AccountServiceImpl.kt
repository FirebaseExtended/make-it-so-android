package com.example.makeitso.model.service.impl

import com.example.makeitso.model.service.AccountService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.RuntimeException
import javax.inject.Inject

class AccountServiceImpl @Inject constructor() : AccountService {
    override suspend fun authenticate(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    throw RuntimeException() //custom
                }
            }
    }

    override suspend fun createAccount(email: String, password: String) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    throw RuntimeException() //custom
                }
            }
    }

    override suspend fun createAnonymousAccount() {
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    throw RuntimeException() //custom
                }
            }
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }

    override suspend fun getUserId(): String {
        return Firebase.auth.currentUser?.uid.orEmpty()
    }
}