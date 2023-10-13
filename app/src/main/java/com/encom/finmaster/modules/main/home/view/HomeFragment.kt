package com.encom.finmaster.modules.main.home.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.encom.finmaster.MainActivity
import com.encom.finmaster.R
import com.encom.finmaster.modules.transaction.util.OnTransactionCreatedListener


class HomeFragment : Fragment(), OnTransactionCreatedListener {
    var accountsFragmentChangeListener: OnTransactionCreatedListener? = null
    var hisotryFragmentChangeListener: OnTransactionCreatedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).onTransactionCreatedListener = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFragments()
    }


    fun initializeFragments() {
        activity?.let {
            val accountsFragment = AccountsFragment()
            accountsFragmentChangeListener = accountsFragment
            it.supportFragmentManager.beginTransaction().replace(R.id.frgAccounts, accountsFragment).commit()
            val historyFragment = HomeHistoryFragment()
            hisotryFragmentChangeListener = historyFragment
            it.supportFragmentManager.beginTransaction().replace(R.id.frgHomeHistory, historyFragment).commit()
        }
    }

    override fun onTransactionCreated() {
        accountsFragmentChangeListener?.onTransactionCreated()
        hisotryFragmentChangeListener?.onTransactionCreated()
    }
}
