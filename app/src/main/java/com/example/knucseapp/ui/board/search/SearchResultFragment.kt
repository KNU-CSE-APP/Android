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
import com.example.knucseapp.databinding.SearchResultFragmentBinding
import com.example.knucseapp.ui.util.DividerItemDecoration
import com.example.knucseapp.ui.board.freeboard.*


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
        //TODO: 검색시 사용
        val keyword = arguments?.getString("keyword") //검색한 단어 !!
        val category = arguments?.getStringArrayList("category") //선택한 카테고리 목록!!
    }

    fun loadData(){
        val emptyreplys = mutableListOf<Reply>(Reply(0,"","",""))
        val emptyComments = mutableListOf<Comment>(Comment(0,"","","",emptyreplys))

        boardDTOs.add(BoardDTO(Board(BoardItem(1,"잡담","지완","배고파요","저녁 메뉴 추천좀요","2021-07-12 18:21"),emptyComments)))
        boardDTOs.add(BoardDTO(Board(BoardItem(2,"잡담","지혜","키아누","커피 요즘 너무 맛있어진듯","2021-07-12 13:21"),emptyComments)))
        boardDTOs.add(BoardDTO(Board(BoardItem(3,"잡담","성기","줄임표시확인줄임표시확인줄임표시확인줄임표시확인","줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인줄임표시확인","2021-07-12 18:21"),emptyComments)))
        boardDTOs.add(BoardDTO(Board(BoardItem(4,"잡담","성빈","까만 안경","사랑해요 나도~ 울고 있어요~ 오 난~~ 보고 싶어서 만나고 싶어서 죽고만 싶어요~","2021-07-12 11:21"),emptyComments)))
    }



}