package com.example.knucseapp.ui.board.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ReservationFragmentBinding
import com.example.knucseapp.databinding.SearchResultFragmentBinding
import com.example.knucseapp.ui.reservation.ClassRoom
import com.example.knucseapp.ui.reservation.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchResultFragment : Fragment() {

    companion object {
        fun newInstance() = SearchResultFragment()
    }

    private lateinit var binding: SearchResultFragmentBinding
    private lateinit var viewModel: SearchResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_result_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        binding.progressBar.visibility = VISIBLE
        setText()
    }

    fun setText(){
        binding.resultTextview.text = arguments?.getString("keyword")
        binding.progressBar.visibility = GONE
    }


}