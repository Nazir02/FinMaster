package com.encom.finmaster.modules.main.home.utils

/**
 * Created by ABDUAHAD FAIZULLOEV on 07,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
interface ICardAdapter {
    fun getViewAt(position: Int): android.view.View?
    fun getCardsCount(): Int

    companion object {
        val MAX_ELEVATION_FACTOR: Int = 2
    }
}