package com.encom.finmaster.modules.category.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.encom.finmaster.R

//ToDo: EDIT or ADD a new Category
class AddCategoryFragment : Fragment() {
    var category_id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     var  view=  inflater.inflate(R.layout.fragment_add_category, container, false)


        return view
    }

    companion object {
        fun newInstance(category_id: Int) =
            AddCategoryFragment().apply {
                this.category_id = category_id
            }
    }
}