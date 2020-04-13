package com.mkielar.sklepallegro.util

import java.util.concurrent.atomic.AtomicBoolean

class SingleEvent<T>(
    private val value: T
) {
    private val isConsumed = AtomicBoolean(false)

    fun consume(): T? =
        if (isConsumed.compareAndSet(false, true)) value else null

    fun peek(): T = value
}