package com.example.makeitso.model.service.impl

import com.example.makeitso.model.service.CrashlyticsService
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import javax.inject.Inject

class CrashlyticsServiceImpl @Inject constructor() : CrashlyticsService {
    override fun logNonFatalCrash(throwable: Throwable?) {
        if (throwable != null) Firebase.crashlytics.recordException(throwable)
    }

    override fun logNonFatalCrash(exception: Exception?) {
        if (exception != null) Firebase.crashlytics.recordException(exception)
    }
}