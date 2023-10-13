package com.encom.finmaster.modules.category.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.encom.finmaster.R
import com.encom.finmaster.modules.category.view.view.CategoriesFragment
import com.encom.finmaster.modules.transaction.view.TransactionFragment


class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        if (intent.hasExtra(SELECT_CATEGORY)){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CategoriesFragment.newInstance(true))
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, CategoriesFragment())
                .commit()
        }
    }

    companion object {
        const val SELECT_CATEGORY = "SELECT_CATEGORY"
        const val SELECTED_CATEGORY = "SELECTED_CATEGORY"
    }
}