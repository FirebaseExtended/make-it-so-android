package com.example.makeitso.model.service

import com.google.firebase.perf.ktx.trace
import com.google.firebase.perf.metrics.Trace

/** Trace a block with Firebase performance.
 *
 * Supports both suspend and regular methods.
 */
inline fun <T> trace(name: String, block: Trace.() -> T): T = Trace.create(name).trace(block)