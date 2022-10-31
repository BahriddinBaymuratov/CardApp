package com.example.card.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.card.databinding.ItemLayoutBinding
import com.example.card.model.CardItem
import com.example.card.util.Constants

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private lateinit var context: Context
    var cardList = mutableListOf<CardItem>()

    lateinit var onItemClick: (CardItem, color: Int) -> Unit
    lateinit var onItemLongClick: (CardItem, pos: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        context = parent.context
        return CardViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.find(cardList[position])
    }

    override fun getItemCount(): Int = cardList.size

    inner class CardViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun find(item: CardItem) {
            val color = Constants.randomColor()
            binding.apply {
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        color
                    )
                )
                textCardDate.text = "${item.cardData1}/${item.cardData2}"
                textCardHolderName.text = item.userName
                textCardName.text = item.bankName
                textCardNumbers.text = item.cardNumber
            }
            itemView.setOnClickListener {
                onItemClick(item, color)
            }
            itemView.setOnLongClickListener {
                onItemLongClick(item, adapterPosition)
                true
            }
        }
    }

}