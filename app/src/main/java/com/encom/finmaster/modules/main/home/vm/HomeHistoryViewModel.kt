package com.encom.finmaster.modules.main.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.history.model.HistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeHistoryViewModel : BaseViewModel() {
    val historyList: ArrayList<Any> = ArrayList()

    fun getHistory(): LiveData<ArrayList<HistoryModel>> {
        val result: MutableLiveData<ArrayList<HistoryModel>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue((repository.getHistory()))
        }
        return result
    }
}