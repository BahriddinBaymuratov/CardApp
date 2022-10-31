package com.example.card.util

import com.example.card.R
import java.util.*
import kotlin.collections.ArrayList

object Constants {
    const val TABLE_NAME = "CardTable"
    const val DB_NAME = "Card.db"
    const val END_PONT = "cardList"
    fun randomColor(): Int{
        val colorList = ArrayList<Int>()
        colorList.add(R.color.color1)
        colorList.add(R.color.color2)
        colorList.add(R.color.color3)
        colorList.add(R.color.color5)
        colorList.add(R.color.color6)
        colorList.add(R.color.color7)
        colorList.add(R.color.color9)

        val random = Random()
        val randomColor = random.nextInt(colorList.size)
        return colorList[randomColor]
    }
}