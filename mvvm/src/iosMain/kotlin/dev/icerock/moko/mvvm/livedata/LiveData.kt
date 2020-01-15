/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.mvvm.livedata

actual open class LiveData<T>(initialValue: T): BindableObject {
    private var storedValue: T = initialValue
    private val observers = mutableListOf<(T) -> Unit>()

    var didChange = PassthroughSubject<T, Never>()

    actual open val value: T
        get() = storedValue

    actual fun addObserver(observer: (T) -> Unit) {
        observer(value)
        observers.add(observer)
    }

    actual fun removeObserver(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    protected fun changeValue(value: T) {
        storedValue = value

        observers.forEach { it(value) }
    }
}
