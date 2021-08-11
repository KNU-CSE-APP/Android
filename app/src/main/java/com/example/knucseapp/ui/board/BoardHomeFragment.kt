package com.example.knucseapp.ui.board

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.knucseapp.R
import com.example.knucseapp.databinding.BoardHomeFragmentBinding
import com.example.knucseapp.ui.MainActivity
import com.example.knucseapp.ui.board.detail.BoardDetailActivity
import com.example.knucseapp.ui.board.freeboard.BoardFragment
import com.example.knucseapp.ui.board.noticeboard.NoticeBoardFragment
import com.example.knucseapp.ui.board.search.SearchActivity
import com.example.knucseapp.ui.board.writeboard.WriteActivity
import com.google.android.material.tabs.TabLayoutMediator

class BoardHomeFragment : Fragment() {

    companion object {
        fun newInstance() = BoardHomeFragment()
        val TAG = "BoardHomeFragment"
    }

    private lateinit var boardHomeFragmentBinding : BoardHomeFragmentBinding
    private lateinit var toolBarTextView : TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var menuItem: Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        toolBarTextView = mainActivity.getToolbarTextView()
        boardHomeFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.board_home_fragment,container, false)

        return boardHomeFragmentBinding.root
    }


    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu_item, menu)
        menuItem = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_search -> {
                val intent = Intent(context,SearchActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.action_write -> {
                val intent = Intent(context,WriteActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)

        val fragmentList = listOf(BoardFragment(0), BoardFragment(1), NoticeBoardFragment())
        val adapter = FragmentAdapter(requireActivity())
        adapter.fragmentList = fragmentList
        boardHomeFragmentBinding.viewPager.adapter = adapter

        val tabTitles = listOf("자유게시판","QNA","학생회공지")
        TabLayoutMediator(boardHomeFragmentBinding.tabLayout,boardHomeFragmentBinding.viewPager){ tab, position ->
            tab.text = tabTitles.get(position)
        }.attach()

        boardHomeFragmentBinding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0, 1 -> {
                        toolBarTextView.text = tabTitles.get(position)
                        menuItem.findItem(R.id.action_write).isVisible = true
                    }
                    else -> {
                        toolBarTextView.text = tabTitles.get(position)
                        menuItem.findItem(R.id.action_write).isVisible = false
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "boardhome - onStart call")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "boardhome - onresume call")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "boardhome - onDetach call")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "boardhome - onStop call")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "boardhome - onPause call")
    }

}