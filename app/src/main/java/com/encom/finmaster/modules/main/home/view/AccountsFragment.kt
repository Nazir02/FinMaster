package com.encom.finmaster.modules.main.home.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.home.adapter.AccountsAdapter
import com.encom.finmaster.modules.main.home.model.AccountsModel
import com.encom.finmaster.modules.main.home.model.AddAccountModel
import com.encom.finmaster.modules.main.home.utils.ShadowTransformer
import com.encom.finmaster.modules.main.home.vm.AccountsViewModel
import com.encom.finmaster.modules.transaction.util.OnTransactionCreatedListener
import com.encom.finmaster.repository.sqlite.model.AccountModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class AccountsFragment : Fragment(), View.OnClickListener, OnTransactionCreatedListener {
    private var images: ArrayList<Any> = ArrayList()
    private lateinit var viewPager: ViewPager
    private lateinit var wormDotsIndicator: WormDotsIndicator
    private lateinit var viewModel: AccountsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(AccountsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_accounts, container, false)
        viewPager = view.findViewById(R.id.infiniteCycleViewPager) as ViewPager
        wormDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAccounts()
    }

    private fun fetchAccounts() {
        viewModel.fetchAccounts().observe(viewLifecycleOwner, Observer {
            val accountsAdapter = AccountsAdapter(requireContext(), it, this)
            viewPager.adapter = accountsAdapter
            viewPager.setPageTransformer(false, ShadowTransformer(viewPager, accountsAdapter))
            wormDotsIndicator.setViewPager(viewPager)
        })
    }

    override fun onTransactionCreated() {
        fetchAccounts()
    }

    override fun onClick(v: View?) {
        v?.let {
            if (it.tag is AccountModel) {
                val data = it.tag as AccountModel
                Toast.makeText(requireContext(),data.name,Toast.LENGTH_SHORT).show()
            } else if (it.tag is AddAccountModel) {
                bottomSheetOpen()
            }
        }
    }

    private fun bottomSheetOpen(){
        Toast.makeText(requireContext(), "AddNewAccount", Toast.LENGTH_SHORT).show()
        val view: View = layoutInflater.inflate(R.layout.fragment_add_card_or_wallet, null)
        val dialog: BottomSheetDialog? = activity?.let { it1 -> BottomSheetDialog(it1) }
        dialog?.setContentView(view)
        dialog?.show()
    }
}
