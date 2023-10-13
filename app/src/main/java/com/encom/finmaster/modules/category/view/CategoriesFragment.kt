package com.encom.finmaster.modules.category.view.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.encom.finmaster.R
import com.encom.finmaster.modules.category.adapter.CategoryAdapter
import com.encom.finmaster.modules.category.model.CategoryModel
import com.encom.finmaster.modules.category.view.CategoryActivity
import com.encom.finmaster.modules.category.vm.CategoryViewModel
import com.encom.finmaster.modules.transaction.view.TransactionActivity
import com.encom.finmaster.modules.transaction.view.TransactionFragment
import kotlin.collections.ArrayList


class CategoriesFragment : Fragment(), View.OnClickListener {
    private var isSelectCategory: Boolean = false

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var viewModel: CategoryViewModel
    //private lateinit var editText: AppCompatEditText
    //private lateinit var clearQueryImageView: ImageView
    //private lateinit var voiceSearchImageView: ImageView
    //private lateinit var fabNewCategory: FloatingActionButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        /*editText = view.findViewById(R.id.search_edit_text)
        clearQueryImageView = view.findViewById(R.id.clear_search_query)
        voiceSearchImageView = view.findViewById(R.id.voice_search_query)
        fabNewCategory = view.findViewById(R.id.fabNewCategory)
        fabNewCategory.setOnClickListener(this)
        voiceSearchImageView.setOnClickListener(this)
        clearQueryImageView.setOnClickListener(this)*/
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*editText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase(Locale.getDefault())
            filterWithQuery(query)
        }*/
        viewModel.getCategories().observe(viewLifecycleOwner, Observer {
            initializeCategories(it)
            viewModel.categoryList.clear()
            viewModel.categoryList.addAll(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            //editText.setText(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View) {
        val data = v.tag as CategoryModel
        if (isSelectCategory) {
            val intent = Intent()
            intent.putExtra(CategoryActivity.SELECTED_CATEGORY, data)
            activity?.setResult(TransactionActivity.REQUEST_SELECT_CATEGORY, intent)
            activity?.onBackPressed()
        }
    }

    private fun initializeCategories(list: ArrayList<CategoryModel>) {
        adapter = CategoryAdapter(requireContext(), list, this)
        recyclerView.adapter = adapter
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            val filteredList: ArrayList<CategoryModel> = viewModel.onFilterChanged(query)
            initializeCategories(filteredList)
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            initializeCategories(viewModel.categoryList)
        }
    }

    private fun toggleRecyclerView(sportsList: ArrayList<CategoryModel>) {
        if (sportsList.isEmpty()) {
            recyclerView.visibility = View.INVISIBLE
            // noSearchResultsFoundText.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            //  noSearchResultsFoundText.visibility = View.INVISIBLE
        }
    }


    companion object {
        const val SPEECH_REQUEST_CODE = 0

        fun newInstance(isCategorySelect: Boolean = false): CategoriesFragment {
            val fragment: CategoriesFragment = CategoriesFragment()
            fragment.isSelectCategory = isCategorySelect
            return fragment
        }
    }


}

