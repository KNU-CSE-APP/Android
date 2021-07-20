package com.example.knucseapp.ui.reservation.seat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityReservationBinding


class ReservationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationBinding
    lateinit var roomnum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        binding.lifecycleOwner = this

        roomnum = intent.getStringExtra("roomname").toString()
        setToolbar()
        setRecyclerView()
        setButton()
    }

    private fun setButton() {
        binding.btnShowSetLayout.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.seat_image_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            mBuilder.show()
        }
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