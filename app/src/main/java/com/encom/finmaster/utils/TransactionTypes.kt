package com.encom.finmaster.utils

/**
 * Created by ABDUAHAD FAIZULLOEV on 07,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class TransactionTypes {
    companion object{
        const val RASKHOD=1 // Расход
        const val DOKHOD=2 // Доход
        const val DOLG=3 //Я должен вернуть деньги
        const val CREDIT = 4 // Мне должны вернуть деньги
    }
}