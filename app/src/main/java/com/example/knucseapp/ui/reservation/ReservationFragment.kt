package com.example.knucseapp.ui.reservation

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.data.repository.ReservationRepository
import com.example.knucseapp.databinding.ReservationFragmentBinding
import com.example.knucseapp.ui.util.DividerItemDecoration
import com.example.knucseapp.ui.util.MyApplication
import com.example.knucseapp.ui.util.NetworkConnection
import com.example.knucseapp.ui.util.NetworkStatus

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
        val connection = NetworkConnection(MyApplication.instance.context())
        connection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected)
            {
                binding.reservationRecycler.visibility = VISIBLE
                binding.disconnectedLayout.visibility = GONE
                NetworkStatus.status = true
                viewModel.searchAllClassRoom()
            }
            else
            {
                binding.reservationRecycler.visibility = GONE
                binding.disconnectedLayout.visibility = VISIBLE
                NetworkStatus.status = false
            }
        }
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
//        if(NetworkStatus.status)
//            viewModel.searchAllClassRoom()
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