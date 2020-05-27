package tech.central.showcase.base.extension

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle

val SavedStateHandle.toBundle: Bundle
    get() {
        val bundle = bundleOf()
        this.keys().forEach { key ->
            bundle.putAll(bundleOf(key to this[key]))
        }
        return bundle
    }