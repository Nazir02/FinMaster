package com.encom.finmaster.modules.transaction.vm

import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.transaction.model.TransactionModel
import com.encom.finmaster.repository.sqlite.model.AccountModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ABDUAHAD FAIZULLOEV on 09,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class TransactionViewModel : BaseViewModel() {
    var selectedCategory: CategoryModel? = null
    var selectedDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    var selectedNote: String? = null
    lateinit var selectedAccount: AccountModel


    fun createTransaction(amount: Float): Boolean {
        selectedCategory?.let { category ->
            val data: TransactionModel = TransactionModel(selectedAccount.id, selectedCategory!!.id, selectedNote, amount, selectedAccount.balance, category.transaction_type)
            repository.createTransaction(data)
            return true
        }
        return false
    }

    fun updateTransaction(id_transaction: Int, data: TransactionModel) {
        repository.updateTransaction(id_transaction, data)
    }

    fun deleteTransaction(id_transaction: String) {
        repository.deleteTransaction(id_transaction)
    }


}