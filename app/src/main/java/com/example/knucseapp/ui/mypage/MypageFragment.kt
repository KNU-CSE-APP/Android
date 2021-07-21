package com.example.knucseapp.ui.mypage

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.MypageFragmentBinding

class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    private lateinit var viewModel: MypageViewModel
    lateinit var binding: MypageFragmentBinding
    var menulist = mutableListOf<MyPageMenu>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = MypageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)
        viewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
        loadMenu()
        var adapter = MyPageAdapter()
        adapter.menulist = menulist

        val decoration = com.example.knucseapp.ui.DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.mypageRecycler.addItemDecoration(decoration)
        binding.mypageRecycler.adapter = adapter
        binding.mypageRecycler.layoutManager = LinearLayoutManager(activity)

    }

    fun loadMenu(){
        val res_list = listOf(R.drawable.settings)

        Menuname.grouplist.forEach { name ->
            menulist.add(MyPageMenu(res_list.get(0), name))
        }
    }

}