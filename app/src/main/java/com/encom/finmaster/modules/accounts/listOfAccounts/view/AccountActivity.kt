package com.encom.finmaster.modules.accounts.listOfAccounts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.encom.finmaster.R
import com.encom.finmaster.modules.category.view.view.CategoriesFragment

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frgActAccountChanger, AccountFragment())
            .commit()
    }
}