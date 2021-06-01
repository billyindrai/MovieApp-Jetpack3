package com.billyindrai.subjetpack3

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdling {
    private val resource: String? = "GLOBAL"
    private val espressoTestIdling = CountingIdlingResource(resource)
    fun getEspressoIdling(): IdlingResource {
        return espressoTestIdling
    }

    fun increment() {
        espressoTestIdling.increment()
    }

    fun decrement() {
        espressoTestIdling.decrement()
    }

}