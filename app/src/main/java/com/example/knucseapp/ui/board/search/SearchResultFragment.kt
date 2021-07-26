package com.example.knucseapp.ui.board.search

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ReservationFragmentBinding
import com.example.knucseapp.databinding.SearchResultFragmentBinding
import com.example.knucseapp.ui.DividerItemDecoration
import com.example.knucseapp.ui.board.freeboard.BoardAdapter
import com.example.knucseapp.ui.board.freeboard.BoardDTO
import com.example.knucseapp.ui.board.freeboard.BoardItem
import com.example.knucseapp.ui.board.freeboard.Comment


class SearchResultFragment : Fragment() {

    companion object {
        fun newInstance() = SearchResultFragment()
    }

    val boardDTOs = mutableListOf<BoardDTO>()

    private lateinit var binding: SearchResultFragmentBinding
    private lateinit var viewModel: SearchResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_result_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        binding.progressBar.visibility = VISIBLE
        setText()
        loadData()
        val boardAdapter = BoardAdapter()
        boardAdapter.boardDTOs = boardDTOs

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.searchRecycler.addItemDecoration(decoration)
        binding.searchRecycler.adapter = boardAdapter
        binding.searchRecycler.layoutManager = LinearLayoutManager(activity)

        binding.progressBar.visibility = GONE
    }

    fun setText(){
        val keyword = arguments?.getString("keyword") //검색한 단어 !!

    }

    fun loadData(){
        val emptyCommentDTO = Comment(0,"","","")
        boardDTOs.add(BoardDTO(BoardItem(1,"잡담","지완","배고파요","저녁 메뉴 추천좀요","2021-07-12 18:21",0),emptyCommentDTO))
        boardDTOs.add(BoardDTO(BoardItem(2,"잡담","지혜","키아누","커피 요즘 너무 맛있어진듯","2021-07-12 13:21",1),emptyCommentDTO))
        boardDTOs.add(BoardDTO(BoardItem(3,"잡담","성기","줄임표시확인줄임표시확인줄임표시확인줄임표시확인","줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인","2021-07-12 18:21",3),emptyCommentDTO))
        boardDTOs.add(BoardDTO(BoardItem(4,"잡담","성빈","까만 안경","사랑해요 나도~ 울고 있어요~ 오 난~~ 보고 싶어서 만나고 싶어서 죽고만 싶어요~","2021-07-12 11:21",99),emptyCommentDTO))
    }

}