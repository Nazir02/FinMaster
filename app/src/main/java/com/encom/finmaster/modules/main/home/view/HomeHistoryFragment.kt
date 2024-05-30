package com.encom.finmaster.modules.main.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.MainActivity
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.history.adapter.HistoryAdapter
import com.encom.finmaster.modules.main.history.vm.HistoryViewModel
import com.encom.finmaster.modules.main.home.adapter.HomeHistoryAdapter
import com.encom.finmaster.modules.main.home.vm.HomeHistoryViewModel
import com.encom.finmaster.modules.transaction.util.OnTransactionCreatedListener

class HomeHistoryFragment : Fragment(), View.OnClickListener, OnTransactionCreatedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyMsg: TextView
    private lateinit var adapter: HomeHistoryAdapter
    private lateinit var viewModel: HomeHistoryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(HomeHistoryViewModel::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home_history, container, false)
        tvEmptyMsg=view.findViewById(R.id.tvEmptyMsg)
        recyclerView = view.findViewById(R.id.recyclerViewOfHome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getHistory()
    }

    private fun getHistory() {
        viewModel.getHistory().observe(viewLifecycleOwner, Observer {
            adapter = HomeHistoryAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
            if(adapter.itemCount==0){
                tvEmptyMsg.visibility=View.VISIBLE
            }else{
                tvEmptyMsg.visibility=View.GONE
            }
        })
    }

    override fun onTransactionCreated() {
        getHistory()
    }

    override fun onClick(p0: View?) {

    }
}