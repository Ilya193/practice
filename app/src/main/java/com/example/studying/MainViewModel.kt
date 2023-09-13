package com.example.studying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _images = MutableLiveData<List<String>>()
    val images: LiveData<List<String>> get() = _images

    fun init() {
        val list = mutableListOf<String>()
        list.add("https://i.ytimg.com/vi/1uQ13OvAr1M/hq720.jpg?sqp=-oaymwEcCOgCEMoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBDdMItDINoIvp0tUnfyZcu-jlC1g")
        list.add("https://sportishka.com/uploads/posts/2022-11/thumbs/1667489855_8-sportishka-com-p-fresh-avto-drift-krasivo-8.jpg")
        list.add("https://www.thesun.ie/wp-content/uploads/sites/3/2022/05/280053944_549060233242362_8091676143213703885_n.jpg")
        _images.value = list
    }
}