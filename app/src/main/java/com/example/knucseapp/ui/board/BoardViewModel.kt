package com.example.knucseapp.ui.board

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knucseapp.data.model.*
import com.example.knucseapp.data.repository.BoardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    val writeCategoryDefault = "카테고리를 설정해주세요"
    var writeCategory = ObservableField<String>()
    var writeContent = ObservableField<String>()
    var writeTitle = ObservableField<String>()

    private val _boardData = MutableLiveData<List<BoardDTO>>()
    val data: LiveData<List<BoardDTO>> get() = _boardData

    private val _writeResponse : MutableLiveData<ApiResult<BoardDTO>> = MutableLiveData()
    val writeResponse : LiveData<ApiResult<BoardDTO>> = _writeResponse

    private val _toastMessage : MutableLiveData<String> = MutableLiveData()
    val toastMessage : LiveData<String> = _toastMessage

    init {
        writeCategory.set(writeCategoryDefault)
    }
    fun getAllBoard(category: String, page: Int, size: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            boardRepository.getAllBoard(category, page, size)?.let {
                _boardData.postValue(it.response.content)
            }
        }
    }

    fun write(categoryText: String) {
        if(categoryText.equals(writeCategoryDefault))
        {
            _toastMessage.value = "카테고리는 필수사항입니다."
        }
        else if(writeTitle.get().isNullOrEmpty()){
            _toastMessage.value = "제목을 입력해주세요"
        }
        else if(writeContent.get().isNullOrEmpty()){
            _toastMessage.value = "본문을 입력해주세요"
        }
        else {
            val categoryid = when(categoryText){
                "자유게시판" -> "FREE"
                "QNA" -> "QNA"
                else -> ""
            }
            viewModelScope.launch {
                _writeResponse.value = boardRepository.write(
                    BoardForm(
                        categoryid,
                        writeContent.get()!!,
                        writeTitle.get()!!
                    )
                )
            }
        }
    }

}