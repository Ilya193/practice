package com.example.studying

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class TestFragmentFactory(
    private val fragmentBlankProvider: () -> Fragment
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BlankFragment::class.java.name -> fragmentBlankProvider()
            else -> super.instantiate(classLoader, className)
        }
    }
}