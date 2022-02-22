package com.example.makeitso.model.service.impl

import com.example.makeitso.model.service.CrashlyticsService
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class CrashlyticsServiceImpl @Inject constructor() : CrashlyticsService {
    override suspend fun logNonFatalCrash(throwable: Throwable) {
        Firebase.crashlytics.recordException(throwable)
    }
}