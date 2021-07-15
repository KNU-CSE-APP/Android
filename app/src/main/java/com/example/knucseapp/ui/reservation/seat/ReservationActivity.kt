package com.example.knucseapp.ui.reservation.seat

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knucseapp.databinding.ActivityReservationBinding

class ReservationActivity : AppCompatActivity() {
    val binding by lazy { ActivityReservationBinding.inflate(layoutInflater)}
    lateinit var roomnum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        roomnum = intent.getStringExtra("roomname").toString()
        setToolbar()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = SeatAdapter()
        adapter.itemList = setData()
        System.out.println(adapter.itemList.toString())
        binding.seatRecycler.adapter = adapter
        binding.seatRecycler.layoutManager = GridLayoutManager(this, 4)
    }

    private fun setData(): MutableList<Seat> {
        var itemList = mutableListOf<Seat>()
        for (i in 1..50) {
            val flag = if(i%7==0) false else true
            var seat = Seat(i, roomnum, flag)
            itemList.add(seat)
        }
        return itemList
    }

    private fun setToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.reservationToolbarTextview.text = roomnum
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}