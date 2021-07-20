package com.example.knucseapp.ui.reservation

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.BoardFragmentBinding
import com.example.knucseapp.databinding.ReservationFragmentBinding
import com.example.knucseapp.ui.mypage.MyPageAdapter
import com.example.knucseapp.ui.mypage.MyPageMenu

class ReservationFragment : Fragment() {

    companion object {
        fun newInstance() = ReservationFragment()
    }
    private lateinit var reservationFragmentBinding: ReservationFragmentBinding
    private lateinit var viewModel: ReservationViewModel
    var itemlist = mutableListOf<ClassRoom>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //TODO:변경~~~
        reservationFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.reservation_fragment, container, false)
        //TODO:변경~~~

        return reservationFragmentBinding.root
    }

    private val backPressedDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(backPressedDispatcher)

        viewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)

        //TODO:xml 파일에서 viewModel 선언 후 아래처럼 하면 xml 파일에서 viewmodel 데이터, 함수 사용 가능합니다.
        reservationFragmentBinding.viewModel = viewModel
        //TODO:변경~~~

        loadItem()
        var adapter = ReservationAdapter()
        adapter.itemList = itemlist

//        val decoration = DividerItemDecoration(activity, VERTICAL)
        val decoration = com.example.knucseapp.ui.DividerItemDecoration(1f, 1f, Color.LTGRAY)

        reservationFragmentBinding.reservationRecycler.addItemDecoration(decoration)
        reservationFragmentBinding.reservationRecycler.adapter = adapter
        reservationFragmentBinding.reservationRecycler.layoutManager = LinearLayoutManager(activity)

    }

    fun loadItem(){
        val roomnum = listOf("IT4-101호", "IT4-102호", "IT4-103호", "IT5-104호", "IT5-105호")
        val num1 = listOf(1, 2, 3, 4, 5)
        val num2 = listOf(10, 20, 30, 10, 20)

        for(i in 0..4){
            itemlist.add(ClassRoom(roomnum[i], num1[i], num2[i]))
        }

    }



}