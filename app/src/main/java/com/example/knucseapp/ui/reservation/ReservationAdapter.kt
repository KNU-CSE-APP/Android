package com.example.knucseapp.ui.reservation

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.ReservationRecyclerBinding
import com.example.knucseapp.ui.reservation.seat.ReservationActivity

class ReservationAdapter : RecyclerView.Adapter<Holder>() {
    var itemList = mutableListOf<ClassRoom>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ReservationRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val menu = itemList.get(position)
        holder.setItem(menu)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

class Holder(val binding: ReservationRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener{
            val intent = Intent(it.context, ReservationActivity::class.java)
            intent.putExtra("roomname", binding.roomNum.text)
            intent.putExtra("sit", binding.numOfStd.text)
            it.context.startActivity(intent)
        }
    }
    fun setItem(item: ClassRoom){
        binding.roomNum.text = "${item.Building}-${item.Room_number}"
        binding.numOfStd.text = "(${item.reserved_num}/${item.total_num})"
    }
}