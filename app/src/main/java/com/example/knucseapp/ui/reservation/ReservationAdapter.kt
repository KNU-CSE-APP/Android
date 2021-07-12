package com.example.knucseapp.ui.reservation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knucseapp.databinding.MypageRecyclerBinding
import com.example.knucseapp.databinding.ReservationRecyclerBinding

class MyPageAdapter : RecyclerView.Adapter<Holder>() {
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
            Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.roomNum.text}", Toast.LENGTH_LONG).show()
        }
    }
    fun setItem(item: ClassRoom){
        binding.roomNum.text = item.roomnum
        binding.numOfStd.text = "(${item.reserved_num}/${item.total_num})"
    }
}