package com.example.makeitso.model.service.impl

import com.example.makeitso.model.service.CrashlyticsService
import javax.inject.Inject

class CrashlyticsServiceImpl @Inject constructor() : CrashlyticsService {
    override suspend fun logNonFatalCrash(throwable: Throwable) {
        //Log non fatal crash
    }

}