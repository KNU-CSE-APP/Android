package com.example.knucseapp.ui.reservation

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ClassRoomDTO
import com.example.knucseapp.data.repository.BoardRepository
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ReservationFragmentBinding
import com.example.knucseapp.ui.board.BoardViewModel
import com.example.knucseapp.ui.board.BoardViewModelFactory
import com.example.knucseapp.ui.util.DividerItemDecoration

class ReservationFragment : Fragment() {

    companion object {
        fun newInstance() = ReservationFragment()
    }
    private lateinit var viewModelFactory: ReservationViewModelFactory
    private lateinit var binding: ReservationFragmentBinding
    private lateinit var viewModel: ReservationViewModel
    private lateinit var adapter: ReservationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.reservation_fragment, container, false)
        viewModelFactory = ReservationViewModelFactory(ReservationRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(ReservationViewModel::class.java)
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
        setviewModel()
        setRecycler()
    }

    private fun setRecycler() {
        adapter = ReservationAdapter()
        val decoration = DividerItemDecoration(1f, 1f, Color.LTGRAY)
        binding.reservationRecycler.addItemDecoration(decoration)
        binding.reservationRecycler.adapter = adapter
        binding.reservationRecycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun setviewModel() {
        viewModel.searchAllClassRoom()
        viewModel.allClassRoomData.observe(viewLifecycleOwner) {
            if(it.success){
                adapter.setData(it.response)
            }
            else {
                Log.d("ReservationFragment", "${it.error}")
            }
        }
    }

}