package com.encom.finmaster.utils


class TransactionTypes {
    companion object{
        const val RASKHOD=1 // Расход
        const val DOKHOD=2 // Доход
        const val DOLG=3 //Я должен вернуть деньги
        const val CREDIT = 4 // Мне должны вернуть деньги
    }
}