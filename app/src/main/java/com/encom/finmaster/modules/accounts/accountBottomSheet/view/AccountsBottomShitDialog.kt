package com.encom.finmaster.modules.accounts.accountBottomSheet.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.accounts.accountBottomSheet.adapter.AccountBottomSheetDialogAdapter
import com.encom.finmaster.repository.sqlite.model.AccountModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by ABDUAHAD FAIZULLOEV on 11,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 */
class AccountsBottomShitDialog : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    var isShow = false
    var arrayList: ArrayList<AccountModel> = ArrayList()
    var listener: OnAccountSelectedListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary_list, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = AccountBottomSheetDialogAdapter(requireContext(), arrayList, this)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        isShow = true
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShow = false
    }

    override fun onClick(v: View?) {
        v?.let {
            listener?.OnAccountSelected(v.getTag() as AccountModel)
        }
        dismiss()
    }

    companion object {

        fun newInstance(arrayList: ArrayList<AccountModel>, listener: OnAccountSelectedListener? = null): AccountsBottomShitDialog {
            val fragment: AccountsBottomShitDialog = AccountsBottomShitDialog()
            fragment.arrayList = arrayList
            fragment.listener = listener
            return fragment
        }

    }

    interface OnAccountSelectedListener {
        fun OnAccountSelected(accountModel: AccountModel)
    }
}