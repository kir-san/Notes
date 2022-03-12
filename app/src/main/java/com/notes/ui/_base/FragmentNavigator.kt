package com.notes.ui._base

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun navigateTo(
        fragment: Class<out Fragment>, arguments: Bundle? = null
    )
}
