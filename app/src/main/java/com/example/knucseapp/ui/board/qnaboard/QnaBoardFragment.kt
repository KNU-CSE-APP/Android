package com.example.knucseapp.ui.board.qnaboard

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.ui.auth.AuthViewModel
import com.example.knucseapp.ui.auth.AuthViewModelFactory
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.board.freeboard.BoardAdapter
import com.example.knucseapp.ui.util.DividerItemDecoration

class QnaBoardFragment : Fragment() {

    companion object {
        fun newInstance() = QnaBoardFragment()
    }

    lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: BoardFragmentBinding



    val boardDTOs = mutableListOf<BoardDTO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.board_fragment,container, false)
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)

        loadData()

        val boardAdapter = BoardAdapter("QNA")
        boardAdapter.boardDTOs = boardDTOs

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.boardRecycler.addItemDecoration(decoration)
        binding.boardRecycler.adapter = boardAdapter
        binding.boardRecycler.layoutManager = LinearLayoutManager(activity)
    }


    fun loadData(){
        boardDTOs.add(BoardDTO("지완", 1, "QNA", "저녁 메뉴 추천좀", "2021-07-12 18:21", 9, "저녁메뉴??"))
//        boardDTOs.add(BoardDTO(Board(BoardItem(1,"#잡담","지완","배고파요","저녁 메뉴 추천좀요","2021-07-12 18:21"),
//            mutableListOf<Comment>())))
//        boardDTOs.add(BoardDTO(Board(BoardItem(2,"#잡담","지혜","키아누","커피 요즘 너무 맛있어진듯","2021-07-12 13:21"),mutableListOf<Comment>())))
//        boardDTOs.add(BoardDTO(Board(BoardItem(3,"#팀원구해요","성기","줄임표시확인줄임표시확인줄임표시확인줄임표시확인","줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인","2021-07-12 18:21"),mutableListOf<Comment>())))
//        boardDTOs.add(BoardDTO(Board(BoardItem(4,"#정보","성빈","까만 안경","사랑해요 나도~ 울고 있어요~ 오 난~~ 보고 싶어서 만나고 싶어서 죽고만 싶어요~","2021-07-12 11:21"),mutableListOf<Comment>())))
    }
}