package com.encom.finmaster.modules.main.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.home.model.AddAccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by ABDUAHAD FAIZULLOEV on 08,ноябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class AccountsViewModel : BaseViewModel() {
    fun fetchAccounts(): LiveData<ArrayList<Any>> {
        val result: MutableLiveData<ArrayList<Any>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            setLoading(true)
            val array:ArrayList<Any> = ArrayList()
            array.addAll(repository.getAccounts())
            array.add(AddAccountModel())
            result.postValue(array)
            setLoading(false)
        }
        return result
    }
}