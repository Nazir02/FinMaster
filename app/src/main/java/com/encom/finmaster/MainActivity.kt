package com.encom.finmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.encom.finmaster.modules.category.view.CategoryActivity
import com.encom.finmaster.modules.main.history.view.HistoryFragment
import com.encom.finmaster.modules.main.home.view.HomeFragment
import com.encom.finmaster.modules.main.profile.view.ProfileFragment
import com.encom.finmaster.modules.main.reports.view.ReportsFragment
import com.encom.finmaster.modules.transaction.util.OnTransactionCreatedListener
import com.encom.finmaster.modules.transaction.view.TransactionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener, ActivityResultCallback<ActivityResult> {
    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var ctgrButton: com.google.android.material.button.MaterialButton
    private val previousFragment = arrayListOf(0)
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var onTransactionCreatedListener: OnTransactionCreatedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), this)
        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.home

        ctgrButton = findViewById(R.id.btnAdd)
        ctgrButton.setOnClickListener {
            resultLauncher.launch(Intent(this@MainActivity, TransactionActivity::class.java))
            overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.beginTransaction().apply {

            fun setCustomAnimRtoL() {
                setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            }

            fun setCustomAnimLtoR() {
                setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            }

            previousFragment.add(item.itemId)
            previousFragment.reverse()

            if (previousFragment[1] == R.id.home && (item.itemId == R.id.history || item.itemId == R.id.reports || item.itemId == R.id.profile)) {
                setCustomAnimRtoL()
            } else if (previousFragment[1] == R.id.history && (item.itemId == R.id.reports || item.itemId == R.id.profile)) {
                setCustomAnimRtoL()
            } else if (previousFragment[1] == R.id.history && item.itemId == R.id.home) {
                setCustomAnimLtoR()
            } else if (previousFragment[1] == R.id.reports && item.itemId == R.id.profile) {
                setCustomAnimRtoL()
            } else if (previousFragment[1] == R.id.reports && (item.itemId == R.id.home || item.itemId == R.id.history)) {
                setCustomAnimLtoR()
            } else if (previousFragment[1] == R.id.profile && (item.itemId == R.id.history || item.itemId == R.id.reports || item.itemId == R.id.home)) {
                setCustomAnimLtoR()
            } else {
                setCustomAnimations(0, 0)
            }
            if (previousFragment[1] != item.itemId) {

                when (item.itemId) {
                    R.id.home -> replace(R.id.frgChanger, HomeFragment())
                    R.id.history -> replace(R.id.frgChanger, HistoryFragment())
                    R.id.reports -> replace(R.id.frgChanger, ReportsFragment())
                    R.id.profile -> replace(R.id.frgChanger, ProfileFragment())
                }
                commit()
            }
        }
        previousFragment.reverse()
        return true
    }

    override fun onActivityResult(result: ActivityResult?) {
        result?.let {
            if (result.resultCode == ADD_TRANSACTION_STATUS_CODE) {
                onTransactionCreatedListener?.onTransactionCreated()
            }
        }
    }

    companion object {
        const val ADD_TRANSACTION_STATUS_CODE = 200
    }
}