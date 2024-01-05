package com.wzp.androidknowledge

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wzp.androidknowledge.databinding.ItemMainBinding
import kotlin.math.floor

class RecycleViewAdapter(
    private val data: List<String>,
    val callBack: (title: String) -> Unit
) : RecyclerView.Adapter<RecycleViewAdapter.ItemHolder>() {
    inner class ItemHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(content: String) {
            binding.content.text = content
            binding.content.setTextColor(Color.parseColor(getRandomColor()))
            binding.root.setOnClickListener { callBack(content) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindView(data[position])
    }

    private fun getRandomColor(): String {
        // 生成随机颜色
        val letters = "0123456789ABCDEF"
        var color = "#"
        for (i in 0 until 6) {
            color += letters[floor(Math.random() * 16).toInt()]
        }
        return color
    }
}