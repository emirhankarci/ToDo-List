package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.RecyclerRowBinding
import com.example.todolist.model.Not

class NotAdapter(val notlar: List<Not>): RecyclerView.Adapter<NotAdapter.NotHolder>() {
    class NotHolder(val binding: RecyclerRowBinding):RecyclerView.ViewHolder(binding.root)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotHolder {
        val recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return notlar.size
    }

    override fun onBindViewHolder(holder: NotHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = notlar.get(position).yazi
        holder.itemView.setOnClickListener {
            val action = MainFragmentoDirections.actionMainFragmentoToBosFragment(bilgi = "eski",id = notlar.get(position).id)
            Navigation.findNavController(it).navigate(action)

        }
    }
}