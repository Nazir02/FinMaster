package com.encom.finmaster.modules.accounts.listOfAccounts.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.accounts.listOfAccounts.adapter.AccountAdapter
import com.encom.finmaster.modules.accounts.listOfAccounts.vm.AccountViewModel
import com.encom.finmaster.modules.category.adapter.CategoryAdapter
import com.encom.finmaster.modules.category.view.CategoryActivity
import com.encom.finmaster.modules.category.vm.CategoryViewModel
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.main.settings.profile.view.adapter.ProfileAdapter
import com.encom.finmaster.repository.sqlite.model.AccountModel


class AccountFragment : Fragment(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AccountAdapter
    private lateinit var viewModel: AccountViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_account, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewOfActAccount)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAccount().observe(viewLifecycleOwner, Observer {
            adapter = AccountAdapter(it, this, requireContext())
           recyclerView.adapter = adapter
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            val itemData = v.tag as AccountModel
            Toast.makeText(context, itemData.name, Toast.LENGTH_SHORT).show()
        }
    }

}