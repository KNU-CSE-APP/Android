package com.example.knucseapp.ui.board.freeboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration


class BoardFragment(boardType: Int) : Fragment() {

    companion object {
        private const val size = 10 //한 페이지에 읽어올 게시글 개수
        private const val TAG = "BoardFragment"
    }

    private val boardCategory = when(boardType) {
        0 -> "FREE"
        1 -> "QNA"
        else -> "ADMIN"
    }

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: BoardFragmentBinding
    private lateinit var adapter: BoardAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var pages = 0
    private var isNext = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.board_fragment, container, false)
        viewModelFactory = BoardViewModelFactory(BoardRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(BoardViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        swipeRefreshLayout = binding.swipe

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
        initData()
    }


    fun initData() {
        pages = 0
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
                    adapter.addItem(page.content, pages)
                }
            }
        }
    }

    fun setRecyclerView() {
        var boardname = when(boardCategory){
            "FREE" -> "자유게시판"
            "QNA" -> "QNA"
            else -> "학생회공지"
        }
        adapter = BoardAdapter(boardname)

        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.boardRecycler.addItemDecoration(decoration)
        binding.boardRecycler.adapter = adapter
        binding.boardRecycler.layoutManager = LinearLayoutManager(activity)

        binding.boardRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                //스크롤의 끝에 도달했을 때
                if (!binding.boardRecycler.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    Log.d(TAG, "end! : ${isNext}")
                    if (hasNextPage()) {
                        adapter.deleteLoading()
                        loadMoreData()
                    } else {
                        if (adapter.deleteLoading()) adapter.notifyDataSetChanged()
                    }
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            initData()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun getPage() = pages++

    private fun hasNextPage() = isNext

    fun refresh() {
        if(::binding.isInitialized) {
            binding.boardRecycler.layoutManager?.scrollToPosition(0)
            initData()
        }
    }

}