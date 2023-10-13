package com.encom.finmaster.modules.accounts.listOfAccounts.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import com.encom.finmaster.repository.sqlite.model.AccountModel
import kotlinx.coroutines.launch

class AccountViewModel:  BaseViewModel() {

    var myDataSet: ArrayList<AccountModel> = ArrayList()

    fun getAccount(): LiveData<ArrayList<AccountModel>> {
        val result: MutableLiveData<ArrayList<AccountModel>> = MutableLiveData()
        viewModelScope.launch {
            _isLoading.postValue(true)
            myDataSet = repository.getAccounts()
            result.postValue(myDataSet)
            _isLoading.postValue(false)
        }
        return result
    }
}