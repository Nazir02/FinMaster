package com.encom.finmaster.modules.transaction.model


class TransactionModel(var id_account: Int,
                       var id_category: Int,
                       var note: String? = null,
                       var amount: Float,
                       var accountBalance: Float,
                       var transaction_type: Int) {

    fun getNOTE():String?{
        note?.let {
           return "'$it'"
        }
        return null
    }

}