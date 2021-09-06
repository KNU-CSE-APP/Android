package com.example.knucseapp.ui.board

import android.util.Log
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
import okhttp3.MultipartBody

class BoardViewModel(private val boardRepository: BoardRepository) : ViewModel() {

    val writeCategoryDefault = "카테고리를 설정해주세요"
    var writeCategory = ObservableField<String>()
    var writeContent = ObservableField<String>()
    var writeTitle = ObservableField<String>()

    private val _freeBoardPage = MutableLiveData<Int>()
    val freeBoardPage : LiveData<Int> get() = _freeBoardPage

    private val _boardData = MutableLiveData<List<BoardDTO>>()
    val data: LiveData<List<BoardDTO>> get() = _boardData

    private val _findBoardData = MutableLiveData<ApiResult<List<BoardDTO>>>()
    val findBoardData: LiveData<ApiResult<List<BoardDTO>>> get() = _findBoardData

    private val _findBoardDataLoading = MutableLiveData<Boolean>()
    val findBoardDataLoading: LiveData<Boolean> get() = _findBoardDataLoading

    private val _boardDetailData = MutableLiveData<ApiResult<BoardDTO>>()
    val boardDetailData: LiveData<ApiResult<BoardDTO>> get() = _boardDetailData

    private val _deleteBoardDetailResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val deleteBoardDetailResponse : LiveData<ApiResult<String>> = _deleteBoardDetailResponse

    private val _allCommentData = MutableLiveData<List<CommentDTO>>()
    val allCommentData: LiveData<List<CommentDTO>> get() = _allCommentData

    private val _allCommentDataLoading = MutableLiveData<Boolean>()
    val allCommentDataLoading: LiveData<Boolean> get() = _allCommentDataLoading

    private val _commentData = MutableLiveData<CommentDTO>()
    val commentData: LiveData<CommentDTO> get() = _commentData

    private val _commentDataLoading = MutableLiveData<Boolean>()
    val commentDataLoading: LiveData<Boolean> get() = _commentDataLoading

    private val _writeCommentResponse : MutableLiveData<ApiResult<CommentDTO>> = MutableLiveData()
    val writeCommentResponse : LiveData<ApiResult<CommentDTO>> = _writeCommentResponse

    private val _writeResponse : MutableLiveData<ApiResult<BoardDTO>> = MutableLiveData()
    val writeResponse : LiveData<ApiResult<BoardDTO>> = _writeResponse

    private val _writeLoading = MutableLiveData<Boolean>()
    val writeLoading : LiveData<Boolean> get() = _writeLoading

    private val _deleteCommentResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val deleteCommentResponse : LiveData<ApiResult<String>> = _deleteCommentResponse



    private val _toastMessage : MutableLiveData<String> = MutableLiveData()
    val toastMessage : LiveData<String> = _toastMessage

    private val _changeBoardDetailResponse : MutableLiveData<ApiResult<String>> = MutableLiveData()
    val changeBoardDetailResponse : LiveData<ApiResult<String>> = _changeBoardDetailResponse

    init {
        writeCategory.set(writeCategoryDefault)
    }

    //모든 게시글 가져오기
    private val _readByPageResponse : MutableLiveData<ApiResult<Page>> = MutableLiveData()
    val readByPageResponse : LiveData<ApiResult<Page>> = _readByPageResponse

    private val _readByPageLoading : MutableLiveData<Boolean> = MutableLiveData()
    val readByPageLoading : LiveData<Boolean> = _readByPageLoading
    fun getAllBoard(category: String, page: Int, size: Int) = viewModelScope.launch {
        if(page == 0) _readByPageLoading.postValue(true)
        _readByPageResponse.value = boardRepository.getAllBoard(category, page, size)
    }

    fun setReadByPageLoadingFalse(page: Int) {
        if(page == 1) _readByPageLoading.postValue(false)
    }

    fun getBoardDetailData(boardId: Int) {
        _allCommentDataLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            _boardDetailData.postValue(boardRepository.getBoardDetail(boardId))
        }
    }

    fun findBoard(category: Int, searchKey: String) = viewModelScope.launch {
        _findBoardDataLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch{
            _findBoardData.postValue(
                    when(category){
                        0 -> boardRepository.findContent(searchKey)
                        1 -> boardRepository.findTitle(searchKey)
                        else -> boardRepository.findAuthor(searchKey)
                    }
            )
            _findBoardDataLoading.postValue(false)
        }
    }
    fun deleteBoardDetail(boardId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _deleteBoardDetailResponse.postValue(boardRepository.deleteBoardDetail(boardId))
        }
    }
    fun getAllComment(boardId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            boardRepository.findCommentsByBoardId(boardId)?.let {
                if(it.success) {
                    _allCommentData.postValue(it.response!!)
                }
                _allCommentDataLoading.postValue(false)
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

    fun getCommentReply(commentId: Int) {
        //comment 에 해당하는 정보를 가져옴
        _commentDataLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            boardRepository.getComment(commentId).let {
                if(it.success) {
                    _commentData.postValue(it.response!!)
                }
                _commentDataLoading.postValue(false)
            }

        }

    }

    fun deleteComment(commentId: Int) {
        CoroutineScope(Dispatchers.IO).launch() {
            launch {
                Log.d("BoardViewModel", "try to delete${commentId}")
                _deleteCommentResponse.postValue(boardRepository.deleteComment(commentId))
            }.join()
            setDeleteCommentNull()
        }
    }

    fun setDeleteCommentNull() {
        _deleteCommentResponse.postValue(null)
    }


    fun write(categoryText: String, file: MutableList<MultipartBody.Part>) {
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
            _writeLoading.postValue(true)
            val categoryid = when(categoryText){
                "자유게시판" -> "FREE"
                "QNA" -> "QNA"
                else -> "ADMIN"
            }
            viewModelScope.launch {
                _writeResponse.value = boardRepository.write(
                    MultipartBody.Part.createFormData("category",categoryid),
                    MultipartBody.Part.createFormData("content",writeContent.get()!!),
                        null,
                        file,
                    MultipartBody.Part.createFormData("title",writeTitle.get()!!)
                )
                _writeLoading.postValue(false)
            }
        }
    }

    fun changeBoardDetail(boardDTO: BoardDTO, file: MutableList<MultipartBody.Part>, delete: MutableList<MultipartBody.Part>) {
        //boardForm 이 기존의 것
        if(writeTitle.get().isNullOrEmpty()){
            _toastMessage.value = "제목을 입력해주세요"
        }
        else if(writeContent.get().isNullOrEmpty()){
            _toastMessage.value = "본문을 입력해주세요"
        }
        else {
            viewModelScope.launch {
                _changeBoardDetailResponse.value = boardRepository.changeBoardDetail(
                        MultipartBody.Part.createFormData("category",boardDTO.category),
                        if(writeContent.get()!! == boardDTO.content) MultipartBody.Part.createFormData("content","") else MultipartBody.Part.createFormData("content",writeContent.get()!!)!!,
                        delete,
                        file,
                        if(writeTitle.get()!! == boardDTO.title) MultipartBody.Part.createFormData("title","") else MultipartBody.Part.createFormData("title",writeTitle.get()!!),
                        boardDTO.boardId,
                )
            }
        }

    }
}