package com.example.knucseapp.ui.reservation.seat

import android.content.DialogInterface
import android.content.Intent
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


class SeatAdapter(var link:ReservationActivity.changeActivity) : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<Seat>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SeatRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val seat = itemList.get(position)
        holder.setItem(seat)
        if(seat.status){
            holder.binding.seatPossible.setOnClickListener {
                link.callReservationConfirmActivity(seat)
            }
        }
        else{
            holder.binding.seatSelected.setOnClickListener {
                link.callReservationConfirmActivity(seat)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
class Holder(val binding: SeatRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

    fun setItem(seat: Seat) {
        binding.seatTextview.text = "${seat.Seat_number}"
        if(!seat.status) { //false면
            binding.run {
                seatTextview.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                seatPossible.visibility = INVISIBLE
                seatSelected.visibility = VISIBLE
            }
        }
        else {
            binding.run {
                seatTextview.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                seatPossible.visibility = VISIBLE
                seatSelected.visibility = INVISIBLE
            }

        }

    }
}