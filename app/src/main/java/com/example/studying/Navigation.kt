package com.example.studying

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface Navigation<T> {
    fun read(): LiveData<T>
    fun update(value: T)

    class Base : Navigation<Screen>, FirstRouter, SecondRouter {
        private val liveData = MutableLiveData<Screen>()

        override fun read(): LiveData<Screen> = liveData

        override fun update(value: Screen) {
            liveData.value = value
        }

        override fun openSecond() {
            update(SecondScreen())
        }

        override fun comeback() {
            update(WarningScreen())
        }

        override fun coup() {
            update(Screen.Coup)
        }
    }
}

interface Screen {
    fun show(supportFragmentManager: FragmentManager, container: Int) = Unit

    abstract class Replace(
        private val fragment: Fragment,
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment).commit()
        }
    }

    abstract class Dialog(
        private val fragment: DialogFragment,
        private val tag: String? = null
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            fragment.show(supportFragmentManager, tag)
        }
    }

    abstract class ReplaceWithAddToBackStack(
        private val fragment: Fragment,
        private val addToBackStack: String? = null
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment)
                .addToBackStack(addToBackStack).commit()
        }
    }

    abstract class ReplaceWithClear(
        fragment: Fragment,
    ) : Replace(fragment) {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            repeat(supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
            super.show(supportFragmentManager, container)
        }
    }

    data object Pop : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.popBackStack()
        }
    }

    data object Coup : Screen
}

class InitScreen : Screen.Replace(FirstFragment.newInstance())
class SecondScreen : Screen.ReplaceWithAddToBackStack(SecondFragment.newInstance())
class WarningScreen : Screen.Dialog(WarningDialogFragment.newInstance())