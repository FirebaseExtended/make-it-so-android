/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.model.service.impl

import com.example.makeitso.BuildConfig
import com.example.makeitso.R.xml as AppConfig
import com.example.makeitso.model.service.ConfigurationService
import com.example.makeitso.model.service.LogService
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject

class ConfigurationServiceImpl @Inject constructor(
    private val logService: LogService
) : ConfigurationService {
    private val remoteConfig get() = Firebase.remoteConfig

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override fun fetchConfiguration() {
        val fetchConfigTrace = Firebase.performance.newTrace(FETCH_CONFIG_TRACE)
        fetchConfigTrace.start()

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                fetchConfigTrace.stop()
                if (!it.isSuccessful) logService.logNonFatalCrash(it.exception)
            }
    }

    override fun getShowTaskEditButtonConfig(): Boolean {
        return remoteConfig[SHOW_TASK_EDIT_BUTTON_KEY].asBoolean()
    }

    companion object {
        private const val SHOW_TASK_EDIT_BUTTON_KEY = "show_task_edit_button"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}