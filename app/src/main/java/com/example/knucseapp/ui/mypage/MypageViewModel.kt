package com.example.knucseapp.ui.mypage

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.data.model.ApiResult
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.model.ChangePasswordForm
import com.example.knucseapp.data.model.MemberDTO
import com.example.knucseapp.data.repository.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MypageViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var newPassword = ObservableField<String>()
    var newPasswordConfirm = ObservableField<String>()
    var curPassword = ObservableField<String>()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    private val _getUserInfoResponse : MutableLiveData<ApiResult<MemberDTO>> = MutableLiveData()
    val getUserInfoResponse : LiveData<ApiResult<MemberDTO>> get() = _getUserInfoResponse

    private val _getPutProfileResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val getPutProfileResponse : LiveData<ApiResult<String>> get() = _getPutProfileResponse

    private val _getChangePasswordResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val getChangePasswordResponse : LiveData<ApiResult<String>> get() = _getChangePasswordResponse

    private val _getMyBoardResponse : MutableLiveData<ApiResult<List<BoardDTO>>> = MutableLiveData()
    val getMyBoardResponse : LiveData<ApiResult<List<BoardDTO>>> get() = _getMyBoardResponse

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

    fun editPassword() = viewModelScope.launch {
        _getChangePasswordResponse.value = authRepository.requestChangePassword(
            ChangePasswordForm(newPassword.get()!!,curPassword.get()!!)
        )
    }

    fun getMyBoard() {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            _getMyBoardResponse.postValue(authRepository.getMyBoards())
            _dataLoading.postValue(false)
        }
    }
}