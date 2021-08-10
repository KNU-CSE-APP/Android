package com.example.knucseapp.ui.board.freeboard

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration
import okhttp3.internal.notify

class BoardFragment : Fragment() {

    companion object {
        fun newInstance() = BoardFragment()
        private const val size = 5 //한 페이지에 읽어올 게시글 개수
        private const val boardCategory = "FREE"
        private const val TAG = "BoardFragment"
    }

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: BoardFragmentBinding
    private lateinit var adapter: BoardAdapter
    private var pages = 0
    private var isNext = false


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

        setRecyclerView()
        setviewModel()
    }


    override fun onStart() {
        super.onStart()
        Log.d("BoardFragment", "onResume call")
        initData()
    }

    fun initData() {
        pages = 0
        adapter.boardDTOs.clear()
        viewModel.getAllBoard(boardCategory, getPage(), size)
    }

    fun loadMoreData() {
        viewModel.getAllBoard(boardCategory, getPage(), size)
    }
    fun setviewModel() {
        viewModel.readByPageResponse.observe(viewLifecycleOwner) {
            if(it.success) {
                it.response.let { page ->
                    isNext = !page.last
                    Log.d(TAG, "${page.last}, ${isNext} !!")
                    adapter.addItem(page.content, page.last)
                }
            }
        }
    }

    fun setRecyclerView() {
        adapter = BoardAdapter("자유게시판")

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.boardRecycler.addItemDecoration(decoration)
        binding.boardRecycler.adapter = adapter
        binding.boardRecycler.layoutManager = LinearLayoutManager(activity)

        binding.boardRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("BoardFragment", "${hasNextPage()}")
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                if (!binding.boardRecycler.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    Log.d(TAG, "end! : ${isNext}")
                    if(hasNextPage()) {
                        adapter.deleteLoading()
                        loadMoreData()
                    }
                    else{
                        if(adapter.deleteLoading()) adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun getPage() = pages++

    private fun hasNextPage() = isNext

    private fun setHasNextPage(b: Boolean){
        isNext = b
    }

}