package com.example.knucseapp.ui.board.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.BoardHomeFragmentBinding
import com.example.knucseapp.databinding.SearchBlankFragmentBinding

class SearchBlankFragment : Fragment() {

    private lateinit var binding : SearchBlankFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_blank_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = SearchCategoryAdapter()
        adapter.category_list.addAll(setData())
        binding.blankRecycler.adapter = adapter
        binding.blankRecycler.layoutManager = LinearLayoutManager(context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    private fun setData() = arrayOf("잡담하기", "정보", "팀원 구하기", "수업", "aaaa", "bbbbbbb")
}