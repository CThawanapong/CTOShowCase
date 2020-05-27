package tech.central.showcase.di.factory

import androidx.lifecycle.ViewModel

interface ViewModelChildAssistedFactory<T : ViewModel> {
    fun create(): T
}