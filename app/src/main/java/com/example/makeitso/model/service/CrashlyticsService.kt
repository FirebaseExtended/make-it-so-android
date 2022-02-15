package com.example.makeitso.model.service

interface CrashlyticsService {
    suspend fun logNonFatalCrash(throwable: Throwable)
}
