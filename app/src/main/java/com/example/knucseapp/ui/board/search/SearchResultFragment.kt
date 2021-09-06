package com.example.knucseapp.ui.board.search

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.SearchResultFragmentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.board.freeboard.*
import com.example.knucseapp.ui.util.*


class SearchResultFragment : Fragment() {

    companion object {
        fun newInstance() = SearchResultFragment()
        val TAG = "SearchResultFragment"
    }

    val boardDTOs = mutableListOf<BoardDTO>()

    private lateinit var binding: SearchResultFragmentBinding
    private lateinit var viewModel: BoardViewModel
    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var boardAdapter: BoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_result_fragment, container, false)
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setviewModel()
        setRecyclerView()

        val connection = NetworkConnection(MyApplication.instance.context())
        connection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected)
            {
                binding.searchRecycler.visibility = View.VISIBLE
                binding.disconnectedLayout.visibility = View.GONE
                NetworkStatus.status = true
                val keyword = arguments?.getString("keyword") //검색한 단어 !!
                val category = arguments?.getInt("category") //선택한 카테고리 목록!!
                viewModel.findBoard(category!!, keyword!!)
            }
            else
            {
                binding.searchRecycler.visibility = View.GONE
                binding.disconnectedLayout.visibility = View.VISIBLE
                NetworkStatus.status = false
            }
        }

    }

    fun setRecyclerView() {
        val keyword = arguments?.getString("keyword") //검색한 단어 !!
        val category = arguments?.getInt("category") //선택한 카테고리 목록!!
        boardAdapter = BoardAdapter("검색")
        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.searchRecycler.addItemDecoration(decoration)
        binding.searchRecycler.adapter = boardAdapter
        binding.searchRecycler.layoutManager = LinearLayoutManager(activity)
        viewModel.findBoard(category!!, keyword!!)
    }

    fun setviewModel() {

        viewModel.findBoardDataLoading.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.show()
            }
            else{
                binding.progressBar.hide()
            }
        }

        viewModel.findBoardData.observe(viewLifecycleOwner) {
            if(it.success){
                boardAdapter.myboardItem(it.response)
            }
            else{
                Log.d(TAG, "${it.error}")
            }
        }

    }

}