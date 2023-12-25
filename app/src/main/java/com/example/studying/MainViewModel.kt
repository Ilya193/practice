package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {

    private val list = mutableListOf<ItemUi>()
    private val _items = MutableLiveData<List<ItemUi>>()
    val items: LiveData<List<ItemUi>> get() = _items

    private var timer = Timer()
    private var sec = 0
    private var moneyReceived = 100
    private val _game = MutableLiveData<GameUi>()
    val game: LiveData<GameUi> get() = _game

    fun init() {
        list.clear()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (sec > 20 && moneyReceived > 10) moneyReceived -= 5
                if (sec < 10) _game.postValue(GameUi.Tick("00:0$sec", moneyReceived.toString()))
                else if (sec in 10..59) _game.postValue(GameUi.Tick("00:$sec", moneyReceived.toString()))
                else {
                    val min = sec / 60
                    val newSec = sec % 60
                    if (min < 10)
                        _game.postValue(GameUi.Tick("0$min:$newSec", moneyReceived.toString()))
                }
                sec++
            }
        }, 0, 1000)

        list.addAll(
            listOf(
                ItemUi.Fifth(),
                ItemUi.Tenth(),
                ItemUi.First(),
                ItemUi.Second(),
                ItemUi.Third(),
                ItemUi.Fourth(),
                ItemUi.Seventh(),
                ItemUi.Fourth(),
                ItemUi.Fifth(),
                ItemUi.Sixth(),
                ItemUi.Second(),
                ItemUi.Sixth(),
                ItemUi.Seventh(),
                ItemUi.Eighth(),
                ItemUi.First(),
                ItemUi.Eighth(),
                ItemUi.Ninth(),
                ItemUi.Third(),
                ItemUi.Ninth(),
                ItemUi.Tenth(),
            )
        )
        _items.value = list.toList()
    }

    fun setVisible(index: Int, itemUi: ItemUi) = viewModelScope.launch(Dispatchers.IO) {
        itemUi.visible(list, index)
        _items.postValue(list.toList())
        val visible = list.all {
            it.visible
        }
        if (visible) {
            timer.cancel()
            _game.postValue(GameUi.Finish(moneyReceived.toString()))
            moneyReceived = 100
            sec = 0
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val visibleItems = list.filter { it.visible }
                val result = hasDuplicates(visibleItems, itemUi)
                if (!result) {
                    itemUi.visible(list, index, false)
                    _items.postValue(list.toList())
                }
            }
        }, 1500)
    }

    private fun hasDuplicates(list: List<ItemUi>, item: ItemUi): Boolean {
        var result = 0
        var count = 0
        list.forEach {
            if (it.value == item.value) {
                count++
                if (count == 2) result++
            }
        }
        return result > 0
    }
}

sealed interface GameUi {
    data class Tick(val time: String, val money: String): GameUi
    data class Finish(val money: String): GameUi
}

sealed class ItemUi(
    open val value: Int,
    open val visible: Boolean,
) {

    abstract fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean = true)

    data class First(
        override val value: Int = R.drawable.baseline_beach_access_24,
        override val visible: Boolean = false,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = First(visible = visible)
        }
    }

    data class Second(
        override val value: Int = R.drawable.baseline_anchor_24,
        override val visible: Boolean = false,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Second(visible = visible)
        }
    }


    data class Third(
        override val value: Int = R.drawable.baseline_alarm_24,
        override val visible: Boolean = false,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Third(visible = visible)
        }
    }

    data class Fourth(
        override val value: Int = R.drawable.baseline_auto_stories_24,
        override val visible: Boolean = false,
    ) : ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Fourth(visible = visible)
        }
    }

    data class Fifth(
        override val value: Int = R.drawable.baseline_bedroom_baby_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Fifth(visible = visible)
        }
    }

    data class Sixth(
        override val value: Int = R.drawable.baseline_bug_report_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Sixth(visible = visible)
        }
    }

    data class Seventh(
        override val value: Int = R.drawable.baseline_broken_image_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Seventh(visible = visible)
        }
    }

    data class Eighth(
        override val value: Int = R.drawable.baseline_bookmark_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Eighth(visible = visible)
        }
    }

    data class Ninth(
        override val value: Int = R.drawable.baseline_bluetooth_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Ninth(visible = visible)
        }
    }

    data class Tenth(
        override val value: Int = R.drawable.baseline_commit_24,
        override val visible: Boolean = false,
    ) :
        ItemUi(value, visible) {
        override fun visible(list: MutableList<ItemUi>, index: Int, visible: Boolean) {
            list[index] = Tenth(visible = visible)
        }
    }
}