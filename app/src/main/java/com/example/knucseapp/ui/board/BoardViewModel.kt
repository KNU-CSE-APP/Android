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

    private val _boardDetailData = MutableLiveData<ApiResult<BoardDTO>>()
    val boardDetailData: LiveData<ApiResult<BoardDTO>> get() = _boardDetailData

    private val _commentData = MutableLiveData<List<CommentDTO>>()
    val commentData: LiveData<List<CommentDTO>> get() = _commentData

    private val _commentDataLoading = MutableLiveData<Boolean>()
    val commentDataLoading: LiveData<Boolean> get() = _commentDataLoading

    private val _writeCommentResponse : MutableLiveData<ApiResult<CommentDTO>> = MutableLiveData()
    val writeCommentResponse : LiveData<ApiResult<CommentDTO>> = _writeCommentResponse

    private val _writeResponse : MutableLiveData<ApiResult<BoardDTO>> = MutableLiveData()
    val writeResponse : LiveData<ApiResult<BoardDTO>> = _writeResponse

    private val _deleteCommentResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val deleteCommentResponse : LiveData<ApiResult<String>> = _deleteCommentResponse

    private val _readByPageResponse : MutableLiveData<ApiResult<Page>> = MutableLiveData()
    val readByPageResponse : LiveData<ApiResult<Page>> = _readByPageResponse

    private val _toastMessage : MutableLiveData<String> = MutableLiveData()
    val toastMessage : LiveData<String> = _toastMessage

    init {
        writeCategory.set(writeCategoryDefault)
    }

    fun getAllBoard(category: String, page: Int, size: Int) = viewModelScope.launch {
        _readByPageResponse.value = boardRepository.getAllBoard(category, page, size)
    }

    fun getBoardDetailData(boardId: Int) {
        _commentDataLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            _boardDetailData.postValue(boardRepository.getBoardDetail(boardId))
        }
    }
    fun getAllComment(boardId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            boardRepository.findCommentsByBoardId(boardId)?.let {
                if(it.success && it.response.isNotEmpty()) {
                    _commentData.postValue(it.response!!)
                }
                _commentDataLoading.postValue(false)
            }
        }
    }

    fun writeComment(commentForm: CommentForm) {
        CoroutineScope(Dispatchers.IO).launch {
            _writeCommentResponse.postValue(boardRepository.commentWrite(commentForm))
        }
    }

    fun writeReply(replyForm: ReplyForm) {
        CoroutineScope(Dispatchers.IO).launch {
            _writeCommentResponse.postValue(boardRepository.commentReplyWrite(replyForm))
        }
    }

    fun deleteComment(commentId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _deleteCommentResponse.postValue(boardRepository.deleteComment(commentId))
        }
    }

    fun setNull() {
        _deleteCommentResponse.postValue(null)
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