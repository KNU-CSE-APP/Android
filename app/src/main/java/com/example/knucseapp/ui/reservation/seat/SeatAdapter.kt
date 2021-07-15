package com.example.knucseapp.ui.reservation.seat

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.R
import com.example.knucseapp.databinding.SeatRecyclerBinding


class SeatAdapter : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<Seat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SeatRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val seat = itemList.get(position)
        holder.setItem(seat)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
class Holder(val binding: SeatRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

    fun setItem(seat: Seat) {
        binding.seatTextview.text = "${seat.seatnum}"
        if(!seat.reserved) { //false면
            binding.run {
                seatTextview.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                seatPossible.visibility = INVISIBLE
                seatSelected.visibility = VISIBLE
                seatSelected.setOnClickListener {
                    Toast.makeText(binding.root.context, "이미 예약된 좌석입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            binding.run {
                seatTextview.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                seatPossible.visibility = VISIBLE
                seatSelected.visibility = INVISIBLE
                seatPossible.setOnClickListener {
                    Toast.makeText(binding.root.context, "${seat.seatnum} 번 좌석을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(binding.root.context)
                            .setTitle("배정좌석확인")
                            .setMessage("아래의 좌석으로 배정하시겠습니까?\n ${seat.seatnum} 번 좌석")
                            .setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener { dialog, whichButton -> // 확인시 처리 로직
                            })
                            .setNegativeButton(android.R.string.no, DialogInterface.OnClickListener { dialog, whichButton -> // 취소시 처리 로직
                            })
                            .show()
                }
            }

        }

    }
}