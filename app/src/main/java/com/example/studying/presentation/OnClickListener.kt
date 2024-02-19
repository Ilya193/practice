package com.example.studying.presentation

interface OnClickListener {
    fun onFavorite(position: Int)
    fun onDelete(position: Int)
    fun onDetail(position: Int)
}