package com.encom.finmaster.modules.transaction.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.encom.finmaster.MainActivity
import com.encom.finmaster.R
import com.encom.finmaster.modules.accounts.accountBottomSheet.view.AccountsBottomShitDialog
import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.category.view.CategoryActivity
import com.encom.finmaster.modules.transaction.model.TransactionModel
import com.encom.finmaster.modules.transaction.vm.TransactionViewModel
import com.encom.finmaster.repository.sqlite.model.AccountModel
import com.encom.finmaster.utils.TransactionTypes
import java.io.InputStream
import java.util.*

class TransactionFragment : Fragment(), View.OnClickListener,
    ActivityResultCallback<ActivityResult>, AccountsBottomShitDialog.OnAccountSelectedListener {
    private var namectgr: String = "Выберите категорию"
    private lateinit var editTextSum: EditText
    private lateinit var editTextData: EditText
    private lateinit var editTextAccount: EditText
    private lateinit var editTextChooseCategory: EditText
    private lateinit var imageViewIconCategoryTransaction: ImageView
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: TransactionViewModel
    private lateinit var accountsDialog: AccountsBottomShitDialog
    private lateinit var imageViewIconAccountTransaction: ImageView
    private lateinit var textViewCurrency: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(), this)
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        editTextChooseCategory = view.findViewById<EditText>(R.id.editTextChooseCategory).apply {
            hint = namectgr
            setOnClickListener(this@TransactionFragment)
        }
        view.findViewById<Button>(R.id.buttonOkTransaction).setOnClickListener(this)
        view.findViewById<Button>(R.id.buttonCancelTransaction).setOnClickListener(this)
        editTextSum = view.findViewById<EditText>(R.id.editTextSum)
        editTextData = view.findViewById<EditText>(R.id.editTextDataTransaction)
        editTextAccount = view.findViewById<EditText>(R.id.editTextAccountTransaction)
        textViewCurrency = view.findViewById<TextView>(R.id.textViewCurrency)
        imageViewIconCategoryTransaction =
            view.findViewById<ImageView>(R.id.imageViewIconCategoryTransaction)
        imageViewIconAccountTransaction =
            view.findViewById<ImageView>(R.id.imageViewIconAccountTransaction)
        editTextData.setOnClickListener(this)
        editTextAccount.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAccounts().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            accountsDialog = AccountsBottomShitDialog.newInstance(it, this@TransactionFragment)
            for (item in it) {
                if (item.is_default) {
                    OnAccountSelected(item)
                    break
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonCancelTransaction -> {
                activity?.onBackPressed()
            }

            R.id.buttonOkTransaction -> {
                if (editTextSum.text.isNotEmpty()) {
                    if (viewModel.createTransaction(editTextSum.text.toString().toFloat())) {
                        activity?.setResult(MainActivity.ADD_TRANSACTION_STATUS_CODE)
                        activity?.onBackPressed()
                    }
                } else {
                    editTextSum.error = getString(R.string.errorAddSum)
                }
            }

            R.id.editTextAccountTransaction -> {
                if (!accountsDialog.isShow) {
                    accountsDialog.show(requireActivity().supportFragmentManager, "Accounts")
                }
            }

            R.id.editTextDataTransaction -> {
                clickDataPicker()
            }

            R.id.editTextChooseCategory -> {
                val intent = Intent(activity, CategoryActivity::class.java)
                intent.putExtra(CategoryActivity.SELECT_CATEGORY, CategoryActivity.SELECT_CATEGORY)
                resultLauncher.launch(intent)
            }
        }
    }

    override fun onActivityResult(result: ActivityResult?) {
        if (result != null && result.resultCode == TransactionActivity.REQUEST_SELECT_CATEGORY && result.data != null) {
            result.data?.let {
                if (it.hasExtra(CategoryActivity.SELECTED_CATEGORY)) {
                    val data =
                        it.getSerializableExtra(CategoryActivity.SELECTED_CATEGORY) as CategoryModel
                    viewModel.selectedCategory = data
                    initCategory(data)
                }
            }
        }
    }

    override fun OnAccountSelected(accountModel: AccountModel) {
        viewModel.selectedAccount = accountModel
        initAccount(accountModel)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.selectedDate = "$year-" + (monthOfYear + 1) + "-$dayOfMonth"
                editTextData.setText(viewModel.selectedDate)
            },
            year,
            month,
            day
        )
        dpd.show()
    }


private fun initCategory(data: CategoryModel) {
    try {
        when (data.transaction_type) {
            TransactionTypes.RASKHOD, TransactionTypes.CREDIT -> {
                textViewCurrency.setTextColor(requireContext().resources.getColor(R.color.colorExpend))
                editTextSum.setTextColor(requireContext().resources.getColor(R.color.colorExpend))
            }

            TransactionTypes.DOKHOD, TransactionTypes.DOLG -> {
                textViewCurrency.setTextColor(requireContext().resources.getColor(R.color.colorIncome))
                editTextSum.setTextColor(requireContext().resources.getColor(R.color.colorIncome))
            }
        }
        val inputStream: InputStream = context?.assets!!.open("icons/" + data.icon)
        editTextChooseCategory.setText(data.name)
        val image = Drawable.createFromStream(inputStream, null)
        imageViewIconCategoryTransaction.setImageDrawable(image)
    } catch (ex: Exception) {

    }
}

private fun initAccount(data: AccountModel) {
    try {
        editTextAccount.setText(data.name)
        val inputStream: InputStream = context?.assets!!.open("icons/" + data.icon)
        val image = Drawable.createFromStream(inputStream, null)
        imageViewIconAccountTransaction.setImageDrawable(image)
        textViewCurrency.text = data.currency
    } catch (ex: Exception) {
    }
}

companion object {
    fun newInstance(namectgr: String, iconctgr: String): TransactionFragment {
        val fragment = TransactionFragment()
        fragment.namectgr = namectgr
        return fragment
    }
}


}