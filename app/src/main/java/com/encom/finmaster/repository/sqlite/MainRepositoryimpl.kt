package com.encom.finmaster.repository.sqlite

import android.annotation.SuppressLint
import android.content.Context
import com.encom.finmaster.R
import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.main.history.model.HistoryModel
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.transaction.model.TransactionModel
import com.encom.finmaster.repository.sqlite.model.*
import com.encom.finmaster.utils.TransactionTypes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MainRepositoryimpl(context: Context) : MySQLiteOpenHelper(context), MainRepository {
    // getAccounts
    @SuppressLint("Range")
    override fun getAccounts(): ArrayList<AccountModel> {
        val array: ArrayList<AccountModel> = ArrayList()
        val sql =
            "SELECT a.id_account,a.is_default ,a.name, a.balance, a.card_holder,a.card_number, c.code as currency, i.name_icon as icon,cb.image,cb.is_dark,ct.code as card_type_code FROM account a,currency c,icon i,card_background cb, card_types ct WHERE a.id_icon=i.id AND a.id_currency =c.id AND a.id_card_bg =cb.id AND a.id_card_type=ct.id"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id_account = mCursor.getInt(mCursor.getColumnIndex("id_account"))
                    val name = mCursor.getString(mCursor.getColumnIndex("name"))
                    val balance = mCursor.getFloat(mCursor.getColumnIndex("balance"))
                    val card_holder = mCursor.getString(mCursor.getColumnIndex("card_holder"))
                    val currency = mCursor.getString(mCursor.getColumnIndex("currency"))
                    val icon = mCursor.getString(mCursor.getColumnIndex("icon"))
                    val image = mCursor.getString(mCursor.getColumnIndex("image"))
                    val is_dark = mCursor.getInt(mCursor.getColumnIndex("is_dark")) == 1
                    val is_default = mCursor.getInt(mCursor.getColumnIndex("is_default")) == 1
                    val card_type_code = mCursor.getString(mCursor.getColumnIndex("card_type_code"))
                    val card_number = mCursor.getString(mCursor.getColumnIndex("card_number"))
                    array.add(
                        AccountModel(
                            id_account,
                            icon,
                            currency,
                            name,
                            card_holder,
                            card_number,
                            balance,
                            card_type_code,
                            image,
                            is_dark,
                            is_default
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    //deleteAccount
    override fun deleteAccount(id: Int) {
        val sql = "delete from account where id_account=$id"
        Execute(sql)
    }

    //insertAccount
    override fun insertAccount(
        id_icon: Int,
        id_currency: Int,
        name: String,
        balance: Double,
        type: String
    ) {
        val sql =
            "insert into account(id_icon,id_currency,name,balance, type) values($id_icon,$id_currency,\"$name\",$balance,\"$type\")"
        Execute(sql)
    }

    //updateAccount
    override fun updateAccount(id: Int, name: String) {
        val sql =
            "update account set name=\"$name\" where id_account=$id"
        Execute(sql)
    }

    //updateAccountBalance
    override fun updateAccountBalance(id: Int, balance: Double) {
        val sql =
            "update account set balance=$balance where id_account=$id"
        Execute(sql)
    }

    // getCurrencies
    @SuppressLint("Range")
    override fun getCurrency(): ArrayList<CurrenciesItemData> {
        val array: ArrayList<CurrenciesItemData> = ArrayList()
        val sql =
            "select c.id, i.name_icon,c.name,c.code from currency as c, icon as i where c.id_icon_country=i.id"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val name_icon = mCursor.getString(mCursor.getColumnIndex("name_icon"))
                    val name = mCursor.getString(mCursor.getColumnIndex("name"))
                    val code = mCursor.getString(mCursor.getColumnIndex("code"))
                    array.add(CurrenciesItemData(id, name_icon, name, code))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    // getIcons
    @SuppressLint("Range")
    override fun getIcon(): ArrayList<IconsItemData> {
        val array: ArrayList<IconsItemData> = ArrayList()
        val sql = "SELECT * FROM icon"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val name = mCursor.getString(mCursor.getColumnIndex("name"))
                    val type = mCursor.getInt(mCursor.getColumnIndex("type"))
                    array.add(IconsItemData(id, name, type))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    // getCategories
    @SuppressLint("Range")
    override fun getCategories(): ArrayList<CategoryModel> {
        val array: ArrayList<CategoryModel> = ArrayList()
        val sql =
            "SELECT category.*, icon.name_icon as icon from category, icon  WHERE category.id_icon=icon.id ORDER BY category.id_parent,category.is_sub_category,category.name"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val id_parent = mCursor.getInt(mCursor.getColumnIndex("id_parent"))
                    val icon = mCursor.getString(mCursor.getColumnIndex("icon"))
                    val name = mCursor.getString(mCursor.getColumnIndex("name"))
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    val transaction_type =
                        mCursor.getInt(mCursor.getColumnIndex("transaction_type"))
                    val isSubCategory: Boolean =
                        mCursor.getInt(mCursor.getColumnIndex("is_sub_category")) == 1
                    array.add(
                        CategoryModel(
                            id,
                            id_parent,
                            icon,
                            name,
                            transaction_type,
                            isSubCategory
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    //deleteCategory
    override fun deleteCategory(id: Int) {
        val sql = "delete from category where id=$id"
        Execute(sql)
    }

    //insertCategory
    override fun insertCategory(id_icon: Int, name: String, transaction_type: Int) {
        val sql =
            "insert into category(id_icon,name,transaction_type) values($id_icon,\"$name\",$transaction_type)"
        Execute(sql)
    }

    //updateCategory
    override fun updateCategory(id: Int, name: String, transaction_type: Int) {
        val sql =
            "update category set name=\"$name\", transaction_type=$transaction_type where id=$id"
        Execute(sql)
    }

    // getSubCategories
    @SuppressLint("Range")
    override fun getSubCategories(): ArrayList<SubCategoriesItemData> {
        val array: ArrayList<SubCategoriesItemData> = ArrayList()
        val sql =
            "select s.id, i.name_icon, c.name, s.name  from category as c, icon as i,subcategory as s where s.id_icon=i.id and s.id_category=c.id"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val name_icon = mCursor.getString(mCursor.getColumnIndex("name_icon"))
                    val name_category = mCursor.getString(mCursor.getColumnIndex("name_category"))
                    val name = mCursor.getString(mCursor.getColumnIndex("name"))
                    array.add(SubCategoriesItemData(id, name_icon, name_category, name))
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    //deleteSubCategory
    override fun deleteSubCategory(id: Int) {
        val sql = "delete from subcategory where id=$id"
        Execute(sql)
    }

    //insertSubCategory
    override fun insertSubCategory(id_category: Int, id_icon: Int, name: String) {
        val sql =
            "insert into subcategory(id_category,id_icon,name) values($id_category,$id_icon,\"$name\")"
        Execute(sql)
    }

    //updateCategory
    override fun updateSubCategory(id: Int, name: String) {
        val sql =
            "update subcategory set name=\"$name\" where id=$id"
        Execute(sql)
    }

    override fun createTransaction(data: TransactionModel) {
        when (data.transaction_type) {
            TransactionTypes.RASKHOD, TransactionTypes.CREDIT -> {
                data.accountBalance = data.accountBalance - data.amount
            }
            TransactionTypes.DOKHOD, TransactionTypes.DOLG -> {
                data.accountBalance = data.accountBalance + data.amount
            }
        }
        val sql =
            "insert into transactions(id_account,id_category,note,amount,account_balance,transaction_type) values(${data.id_account},${data.id_category}, ${data.getNOTE()},${data.amount},${data.accountBalance},${data.transaction_type})"
        Execute(sql)
        updateAccountBalance(data.id_account, data.accountBalance)
    }

    fun updateAccountBalance(id_account: Int, balance: Float) {
        val sql =
            "update account SET balance=$balance WHERE id_account=$id_account"
        Execute(sql)
    }

    override fun updateTransaction(id_transaction: Int, data: TransactionModel) {
        val sql =
            "update transactions set id_account=${data.id_account}, id_category=${data.id_category}, note=${data.getNOTE()}, amount=${data.amount}, account_balance=${data.accountBalance}, transaction_type=${data.transaction_type} WHERE id=$id_transaction"
        Execute(sql)
    }

    override fun deleteTransaction(id_transaction: String) {
        Execute("DELETE FROM transactions WHERE id=$id_transaction")
    }

    @SuppressLint("Range", "SimpleDateFormat")
    override fun getHistory(): ArrayList<HistoryModel> {
        val array: ArrayList<HistoryModel> = ArrayList()
        var i = 0
        val sql =
            "select t.id,t.note,t.amount,strftime('%d-%m-%Y', t.date_time) as date_time,t.transaction_type, i.name_icon as icon,c.name as category,cur.code as currency from transactions t, icon i,category c,account a,currency cur  WHERE t.id_category = c.id and t.id_account=a.id_account and a.id_currency = cur.id and i.id=c.id_icon ORDER BY t.date_time DESC"
        val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    val id = mCursor.getInt(mCursor.getColumnIndex("id"))
                    val note = mCursor.getString(mCursor.getColumnIndex("note"))
                    val amount = mCursor.getFloat(mCursor.getColumnIndex("amount"))
                    val currency = mCursor.getString(mCursor.getColumnIndex("currency"))
                    val date_time = mCursor.getString(mCursor.getColumnIndex("date_time"))
                    val transaction_type =
                        mCursor.getInt(mCursor.getColumnIndex("transaction_type"))
                    val icon = mCursor.getString(mCursor.getColumnIndex("icon"))
                    val category = mCursor.getString(mCursor.getColumnIndex("category"))
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    i++
                    array.add(
                        HistoryModel(
                            id,
                            icon,
                            category,
                            note,
                            amount,
                            currency,
                            date_time,
                            transaction_type,
                            i % 2 == 0
                        )
                    )
                } while (mCursor.moveToNext())
            }
            mCursor.close()
        }
        return array
    }

    override fun getProfile(): ArrayList<ProfileModel> {
        val profile: ArrayList<ProfileModel> = ArrayList()
        profile.add(ProfileModel(1, R.drawable.ic_profile_settings, "Настройки"))
        profile.add(ProfileModel(2, R.drawable.ic_profile_cat, "Категория"))
        profile.add(ProfileModel(3, R.drawable.ic_profile_wallet, "Мои кошелки"))
        profile.add(ProfileModel(4, R.drawable.ic_profile_dolg, "Долги"))
        profile.add(ProfileModel(5, R.drawable.ic_profile_help, "Помощь и Поддержка"))

        return profile
    }

    //getAllIncomeMoney
    @SuppressLint("Range", "SimpleDateFormat")
    override fun getAllIncomeMoney(): Double {

        val sql =
            "select sum(amount) as sum from (select t.amount,strftime('%d-%m-%Y', t.date_time) as date_time,t.transaction_type, cur.code as currency from transactions t, icon i,category c,account a,currency cur  WHERE t.id_category = c.id and t.id_account=a.id_account and a.id_currency = cur.id and i.id=c.id_icon ORDER BY t.date_time DESC) where currency=\"TJS\" and transaction_type=2\n"
              val mCursor = Query(sql)
        if (mCursor != null) {
             if (mCursor.moveToFirst()) {

                val sum = mCursor.getDouble(mCursor.getColumnIndex("sum"))

                return sum

            } else {

            }
            mCursor.close()
        }
        return 0.toDouble()
    }
    //getAllOutcomeMoney
    @SuppressLint("Range", "SimpleDateFormat")
    override fun getAllOutcomeMoney(): Double {

        val sql =
            "select sum(amount) as sum1 from (select t.amount,strftime('%d-%m-%Y', t.date_time) as date_time,t.transaction_type, cur.code as currency from transactions t, icon i,category c,account a,currency cur  WHERE t.id_category = c.id and t.id_account=a.id_account and a.id_currency = cur.id and i.id=c.id_icon ORDER BY t.date_time DESC) where currency=\"TJS\" and transaction_type=1\n"
             val mCursor = Query(sql)
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {

                val sum1 = mCursor.getDouble(mCursor.getColumnIndex("sum1"))

                return sum1

            } else {

            }
            mCursor.close()
        }
        return 0.toDouble()
    }
}