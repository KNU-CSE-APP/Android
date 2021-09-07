package com.example.knucseapp.ui.reservation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.data.model.FindClassRoomDTO
import com.example.knucseapp.databinding.ReservationRecyclerBinding
import com.example.knucseapp.ui.reservation.seat.ReservationActivity

class ReservationAdapter : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<FindClassRoomDTO>()
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

    fun setData(item: List<FindClassRoomDTO>)
    {
        itemList.clear()
        itemList.addAll(item)
        notifyDataSetChanged()
    }
}

class Holder(val binding: ReservationRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: FindClassRoomDTO){
        binding.roomNum.text = "${item.building} - ${item.roomNumber}호"
        binding.numOfStd.text = "(${item.totalSeatNumber-item.reservedSeatNumber}/${item.totalSeatNumber})"
    }
}