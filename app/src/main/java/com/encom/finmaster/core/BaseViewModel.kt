package com.encom.finmaster.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.repository.sqlite.MainRepository
import com.encom.finmaster.repository.sqlite.model.AccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject


open class BaseViewModel() : ViewModel(), KoinComponent {
    val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _progressVisibility: MutableLiveData<Int> = MutableLiveData()
    protected val errorHandler: MutableLiveData<Any> = MutableLiveData()
    protected val repository: MainRepository by inject()
    fun getErrors(): LiveData<Any?> = errorHandler

    fun getAccounts(): LiveData<ArrayList<AccountModel>> {
        val result: MutableLiveData<ArrayList<AccountModel>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(repository.getAccounts())
        }
        return result
    }

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    fun getLoading(): LiveData<Boolean> {
        return _isLoading
    }

    val progressVisibility: LiveData<Int>
        get() = _progressVisibility

    fun setProgressVisibility(visibility: Int) {
        _progressVisibility.postValue(visibility)
    }

}
