package com.example.knucseapp.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.BoardHomeFragmentBinding
import com.example.knucseapp.ui.board.freeboard.BoardFragment
import com.example.knucseapp.ui.board.noticeboard.NoticeBoardFragment
import com.google.android.material.tabs.TabLayoutMediator

class BoardHomeFragment : Fragment() {

    companion object {
        fun newInstance() = BoardHomeFragment()
    }

    private lateinit var boardHomeFragmentBinding : BoardHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        boardHomeFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.board_home_fragment,container, false)
        return boardHomeFragmentBinding.root
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)

        val fragmentList = listOf(BoardFragment(), NoticeBoardFragment())
        val adapter = FragmentAdapter(requireActivity())
        adapter.fragmentList = fragmentList
        boardHomeFragmentBinding.viewPager.adapter = adapter

        val tabTitles = listOf<String>("자유게시판","학생회공지")
        TabLayoutMediator(boardHomeFragmentBinding.tabLayout,boardHomeFragmentBinding.viewPager){ tab, position ->
            tab.text = tabTitles.get(position)
        }.attach()
    }
}