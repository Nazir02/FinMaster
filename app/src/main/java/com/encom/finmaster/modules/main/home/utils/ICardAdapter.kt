package com.encom.finmaster.modules.main.home.utils


interface ICardAdapter {
    fun getViewAt(position: Int): android.view.View?
    fun getCardsCount(): Int

    companion object {
        val MAX_ELEVATION_FACTOR: Int = 2
    }
}