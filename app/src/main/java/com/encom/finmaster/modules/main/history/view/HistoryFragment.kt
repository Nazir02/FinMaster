package com.encom.finmaster.modules.main.history.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.MainActivity
import com.encom.finmaster.R
import com.encom.finmaster.modules.main.history.adapter.HistoryAdapter
import com.encom.finmaster.modules.main.history.vm.HistoryViewModel
import com.encom.finmaster.modules.transaction.util.OnTransactionCreatedListener

class HistoryFragment : Fragment(), View.OnClickListener, OnTransactionCreatedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        if (activity is MainActivity) {
            (activity as MainActivity).onTransactionCreatedListener = this
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchHistory()
    }

    private fun fetchHistory() {
        view?.let {
            viewModel.getHistory().observe(viewLifecycleOwner, Observer {
                adapter = HistoryAdapter(requireContext(), it, this)
                recyclerView.adapter = adapter
            })
        }
    }

    override fun onTransactionCreated() {
        fetchHistory()
    }

    override fun onClick(p0: View?) {

    }
}