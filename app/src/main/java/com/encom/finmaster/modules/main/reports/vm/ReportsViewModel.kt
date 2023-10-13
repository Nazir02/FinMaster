package com.encom.finmaster.modules.main.reports.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.history.model.HistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ReportsViewModel : BaseViewModel() {


    fun getAllIncomeMoney() :Double  {
        val result:Double= repository.getAllIncomeMoney()

        return result
    }

    fun getAllOutcomeMoney() :Double  {
        val result:Double= repository.getAllOutcomeMoney()

        return result
    }

}