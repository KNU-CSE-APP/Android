package com.example.knucseapp.ui.reservation.seat

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.R
import com.example.knucseapp.data.model.ClassSeatDTO
import com.example.knucseapp.databinding.SeatRecyclerBinding


class SeatAdapter(var link:ReservationActivity.changeActivity) : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<ClassSeatDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SeatRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val seat = itemList.get(position)
        holder.setItem(seat)
        if(seat.status == "UNRESERVED"){
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

    fun setData(data: List<ClassSeatDTO>)
    {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }
}
class Holder(val binding: SeatRecyclerBinding): RecyclerView.ViewHolder(binding.root) {

    fun setItem(seat: ClassSeatDTO) {
        binding.seatTextview.text = "${seat.number}"
        if(seat.status == "RESERVED") { //false면
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