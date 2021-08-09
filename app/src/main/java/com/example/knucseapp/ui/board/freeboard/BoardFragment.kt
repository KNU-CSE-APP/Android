package com.example.knucseapp.ui.board.freeboard

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
import com.example.knucseapp.R
import com.example.knucseapp.data.model.BoardDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration

class BoardFragment : Fragment() {

    companion object {
        fun newInstance() = BoardFragment()
    }

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding: BoardFragmentBinding
    private lateinit var adapter: BoardAdapter

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

    override fun onResume() {
        super.onResume()
        setData()
    }

    fun setData() {
        viewModel.getAllBoard("FREE", 0, 10)
    }
    fun setviewModel() {
        viewModel.data.observe(viewLifecycleOwner) {
            Log.d("BoardFragment", "data : ${it.toString()}")
            adapter.boardDTOs.clear()
            adapter.boardDTOs.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun setRecyclerView() {
        adapter = BoardAdapter("자유게시판")

        val decoration = DividerItemDecoration(1f,1f, Color.LTGRAY)
        binding.boardRecycler.addItemDecoration(decoration)
        binding.boardRecycler.adapter = adapter
        binding.boardRecycler.layoutManager = LinearLayoutManager(activity)
    }

}