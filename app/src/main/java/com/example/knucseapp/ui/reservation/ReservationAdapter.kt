package com.example.knucseapp.ui.reservation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.ClassRoomDTO
import com.example.knucseapp.databinding.ReservationRecyclerBinding
import com.example.knucseapp.ui.reservation.seat.ReservationActivity

class ReservationAdapter : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<ClassRoomDTO>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ReservationRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = itemList.get(position)
        holder.setItem(menu)
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ReservationActivity::class.java)
            intent.putExtra("room", menu)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class Holder(val binding: ReservationRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: ClassRoomDTO){
        binding.roomNum.text = "${item.building}-${item.roomNumber}"
        binding.numOfStd.text = "(0/${item.totalSeatNumber})"
    }
}