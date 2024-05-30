package com.encom.finmaster.modules.category.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.category.model.CategoryHeadModel
import com.encom.finmaster.modules.category.model.CategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class CategoryViewModel : BaseViewModel() {
    val categoryList: ArrayList<CategoryModel> = ArrayList()

    fun getCategories(): LiveData<ArrayList<CategoryModel>> {
        val result: MutableLiveData<ArrayList<CategoryModel>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getCategories())
        }
        return result
    }

    fun onFilterChanged(filterQuery: String): ArrayList<CategoryModel> {
        val filteredList = ArrayList<CategoryModel>()
        for (currentSport in categoryList) {
            if (currentSport.name.lowercase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(currentSport)
            }
        }
        return filteredList
    }
}