package com.encom.finmaster.repository.sqlite

import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.main.history.model.HistoryModel
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.transaction.model.TransactionModel
import com.encom.finmaster.repository.sqlite.model.*


interface MainRepository {

    fun getHistory(): ArrayList<HistoryModel>

    fun updateSubCategory(id: Int, name: String)

    fun getProfile(): ArrayList<ProfileModel>

    fun insertSubCategory(id_category: Int, id_icon: Int, name: String)

    fun deleteSubCategory(id: Int)

    fun getSubCategories(): ArrayList<SubCategoriesItemData>

    fun updateCategory(id: Int, name: String, transaction_type: Int)

    fun insertCategory(id_icon: Int, name: String, transaction_type: Int)

    fun deleteCategory(id: Int)

    fun getCategories(): ArrayList<CategoryModel>

    fun getIcon(): ArrayList<IconsItemData>

    fun getCurrency(): ArrayList<CurrenciesItemData>

    fun createTransaction(data: TransactionModel)

    fun updateTransaction(id_transaction: Int, data: TransactionModel)

    fun deleteTransaction(id_transaction: String)

    fun updateAccountBalance(id: Int, balance: Double)

    fun updateAccount(id: Int, name: String)

    fun insertAccount(id_icon: Int, id_currency: Int, name: String, balance: Double, type: String)

    fun deleteAccount(id: Int)

    fun getAccounts(): ArrayList<AccountModel>

    fun getAllIncomeMoney():Double
    fun getAllOutcomeMoney():Double
}