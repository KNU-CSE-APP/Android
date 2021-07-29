package com.example.knucseapp.ui.reservation.seat

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.system.Os.accept
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.knucseapp.R
import com.example.knucseapp.databinding.ActivityReservationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ReservationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReservationBinding
    lateinit var roomnum: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation)
        binding.lifecycleOwner = this

        roomnum = intent.getStringExtra("roomname").toString()
        getRoomData()
        setToolbar()
        setRecyclerView()
        setButton()

    }

    private fun getRoomData(){
        val data = roomnum.replace(" ", "")
        val arr = data.split("-", " ")
        println(arr)
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
        var link = changeActivity()
        val adapter = SeatAdapter(link)
        adapter.itemList = setData()
        binding.seatRecycler.adapter = adapter
        binding.seatRecycler.layoutManager = GridLayoutManager(this, 4)
    }

    private fun setData(): MutableList<Seat> {
        var itemList = mutableListOf<Seat>()
        for (i in 1..50) {
            val flag = if(i%7==0) false else true
            var seat = Seat(i, i, roomnum, flag)
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

    inner class changeActivity{
        fun callReservationConfirmActivity(seat: Seat) {
            seat.apply {
                if(!status){
                    Toast.makeText(this@ReservationActivity, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show()
                }
                else{
//                    Toast.makeText(
//                        this@ReservationActivity,
//                        "${seat.Seat_number} 번 좌석을 선택하셨습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    MaterialAlertDialogBuilder(binding.root.context)
                        .setTitle("좌석 확인")
                        .setMessage("다음 좌석을 예약하시겠습니까? \n${seat.Room_number} / ${seat.Seat_number}번 좌석\n\n※반드시 착석후 좌석을 예약해주세요.")
                        .setPositiveButton("예약") { dialog, whichButton ->
                            val intent = Intent(
                                this@ReservationActivity,
                                ReservationConfirmActivity::class.java
                            )
                            intent.putExtra("seat", seat)
                            //TODO: ViewModel을 쓴다면, position만 받아와서 viewmodel 에 해당 위치의 데이터를 사용하는 것도 좋을것 같다.

                            this@ReservationActivity.startActivity(intent)
                        }
                        .setNegativeButton("취소") { dialog, whichButton -> // 취소시 처리 로직
                        }
                        .show()

                }
            }

        }

    }
}