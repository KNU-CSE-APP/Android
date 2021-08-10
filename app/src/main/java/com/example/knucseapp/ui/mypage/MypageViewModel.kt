package com.example.knucseapp.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.data.model.ApiResult
import com.example.knucseapp.data.model.MemberDTO
import com.example.knucseapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MypageViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    private val _getUserInfoResponse : MutableLiveData<ApiResult<MemberDTO>> = MutableLiveData()
    val getUserInfoResponse : LiveData<ApiResult<MemberDTO>> get() = _getUserInfoResponse

    private val _getPutProfileResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val getPutProfileResponse : LiveData<ApiResult<String>> get() = _getPutProfileResponse

    fun getUserInfo(){
        _dataLoading.postValue(true)
        viewModelScope.launch {
            _getUserInfoResponse.value = authRepository.requestUserInfo()
            _dataLoading.postValue(false)
        }
    }

    fun editUserInfo(image : MultipartBody.Part?,nickname : MultipartBody.Part?) = viewModelScope.launch{
        _getPutProfileResponse.value = authRepository.requestEditUserInfo(image,nickname)
    }
}