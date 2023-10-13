package com.encom.finmaster.modules.main.profile.view

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
import com.encom.finmaster.modules.accounts.listOfAccounts.view.AccountActivity
import com.encom.finmaster.modules.category.view.CategoryActivity
import com.encom.finmaster.modules.main.settings.profile.view.adapter.ProfileAdapter
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.modules.main.profile.vm.ProfileViewModel

class ProfileFragment : Fragment(),View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfileAdapter
    private lateinit var viewModel: ProfileViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile().observe(viewLifecycleOwner, Observer {
            adapter = ProfileAdapter(it, this, requireContext())
            recyclerView.adapter = adapter
        })
    }

    override fun onClick(v: View?) {
        v?.let {
            val itemData = v.tag as ProfileModel
            if (itemData.id == 2) {
                val intent = Intent(requireActivity(), CategoryActivity::class.java)
                startActivity(intent)
            }
            else if (itemData.id == 3) {
                val intent = Intent(requireActivity(), AccountActivity::class.java)
                startActivity(intent)
            }
            else if (itemData.id == 5) {
                val intent = Intent(requireActivity(), HelpActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(context, itemData.name, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}