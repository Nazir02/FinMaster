package com.encom.finmaster.modules.transaction.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.encom.finmaster.R
import com.encom.finmaster.modules.transaction.view.TransactionFragment


class TransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TransactionFragment())
            .commit()

    }

    companion object{
        val REQUEST_SELECT_CATEGORY=10
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_down_reverse, R.anim.slide_up_reverse)
    }
}