package com.encom.finmaster.modules.main.history.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.history.model.HistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryViewModel : BaseViewModel() {
    val historyList: ArrayList<Any> = ArrayList()

    fun getHistory(): LiveData<ArrayList<Any>> {
        val result: MutableLiveData<ArrayList<Any>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(historyGroupByDate(repository.getHistory()))
        }
        return result
    }

    private fun historyGroupByDate(historyList: ArrayList<HistoryModel>): ArrayList<Any> {
        var lastHead = ""
        val arrayList: ArrayList<Any> = ArrayList()
        for (item in historyList) {
            if (item.date != lastHead) {
                lastHead = item.date
                arrayList.add(item.date)
                arrayList.add(item)
            } else {
                arrayList.add(item)
            }
        }
        return arrayList
    }
}