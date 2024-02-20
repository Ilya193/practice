package com.example.studying.presentation

interface OnClickListenerNotes : OnClickListener {
    fun onFavorite(position: Int)
    fun onDetail(position: Int)
}