package com.example.makeitso.model.service

import java.lang.Exception

interface CrashlyticsService {
    fun logNonFatalCrash(throwable: Throwable?)
    fun logNonFatalCrash(exception: Exception?)
}
