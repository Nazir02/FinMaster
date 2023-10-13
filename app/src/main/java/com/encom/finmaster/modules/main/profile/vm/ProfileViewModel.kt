package com.encom.finmaster.modules.main.profile.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.encom.finmaster.core.BaseViewModel
import com.encom.finmaster.modules.main.profile.model.ProfileModel
import kotlinx.coroutines.launch

/**
 * Created by ABDUAHAD FAIZULLOEV on 17,октябрь,2021
 * abduahad.fayzulloev@gmail.com
 * http://abduahad.com/
 *
 */
class ProfileViewModel : BaseViewModel() {

    var myDataSet: ArrayList<ProfileModel> = ArrayList()

    fun getProfile(): LiveData<ArrayList<ProfileModel>> {
        val result: MutableLiveData<ArrayList<ProfileModel>> = MutableLiveData()
        viewModelScope.launch {
            _isLoading.postValue(true)
            myDataSet = repository.getProfile()
            result.postValue(myDataSet)
            _isLoading.postValue(false)
        }
        return result
    }



}