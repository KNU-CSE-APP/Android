package com.example.knucseapp.ui.board

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.databinding.BoardHomeFragmentBinding
import com.example.knucseapp.ui.MainActivity
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

    private lateinit var viewModelFactory: BoardViewModelFactory
    private lateinit var viewModel: BoardViewModel
    private lateinit var binding : BoardHomeFragmentBinding
    private lateinit var toolBarTextView : TextView
    private lateinit var mainActivity: MainActivity
    private lateinit var menuItem: Menu
    lateinit var adapter: FragmentAdapter
    lateinit var fragmentList: List<Fragment>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        toolBarTextView = mainActivity.getToolbarTextView()
        binding = DataBindingUtil.inflate(inflater, R.layout.board_home_fragment, container, false)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "BoardHomeFragment - onAttach call")
        mainActivity = context as MainActivity
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "BoardHomeFragment - onStart call")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "BoardHomeFragment - onResume call")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_toolbar_menu_item, menu)
        menuItem = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_search -> {
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.action_write -> {
//                val intent = Intent(context, WriteActivity::class.java)
//                startActivity(intent)
                openActivityForResult()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)

        fragmentList = listOf(BoardFragment(0), BoardFragment(1), NoticeBoardFragment())
        adapter = FragmentAdapter(requireActivity())
        adapter.fragmentList = fragmentList
        binding.viewPager.adapter = adapter

        val tabTitles = listOf("자유게시판", "QNA", "학생회공지")
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab, position ->
            tab.text = tabTitles.get(position)
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
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
        })
    }

    fun openActivityForResult()
    {
        startForResult.launch(Intent(context, WriteActivity::class.java))
    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK)
        {
            val intent = result.data // Handle the Intent //do stuff here } }
            if (intent != null) {

                if(intent.getStringExtra("category") == "FREE") {
                    val frag = adapter.getItem(0) as BoardFragment
                    binding.viewPager.setCurrentItem(0)
                    frag.refresh()
                }
                else {
                    Log.d("BoardHomeFragment", "get data done!")
                    binding.viewPager.setCurrentItem(1)
                    val frag = adapter.getItem(1) as BoardFragment
                    frag.refresh()
                }
            }

        }

    }
}