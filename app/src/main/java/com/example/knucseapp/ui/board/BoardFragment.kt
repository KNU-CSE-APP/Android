package com.example.knucseapp.ui.board

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.ui.DividerItemDecoration

class BoardFragment : Fragment() {

    companion object {
        fun newInstance() = BoardFragment()
    }

    private lateinit var viewModel: BoardViewModel
    private lateinit var boardFragmentBinding: BoardFragmentBinding
    val boardItemList = mutableListOf<BoardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        boardFragmentBinding = BoardFragmentBinding.inflate(inflater,container,false)
        return boardFragmentBinding.root
    }
    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoardViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        val boardAdapter = BoardAdapter()
        loadData()
        boardAdapter.boardItemList = boardItemList

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        boardFragmentBinding.boardRecycler.addItemDecoration(decoration)

        boardFragmentBinding.boardRecycler.adapter = boardAdapter
        boardFragmentBinding.boardRecycler.layoutManager = LinearLayoutManager(activity)
    }

    fun loadData(){
        boardItemList.add(BoardItem("지완","배고파요","저녁 메뉴 추천좀요","2021-07-12 18:21","0"))
        boardItemList.add(BoardItem("지혜","키아누","커피 요즘 너무 맛있어진듯","2021-07-12 13:21","1"))
        boardItemList.add(BoardItem("성기","줄임표시확인줄임표시확인줄임표시확인줄임표시확인","줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인","2021-07-12 18:21","3"))
        boardItemList.add(BoardItem("성빈","까만 안경","사랑해요 나도~ 울고 있어요~ 오 난~~ 보고 싶어서 만나고 싶어서 죽고만 싶어요~","2021-07-12 11:21","99"))
    }
}