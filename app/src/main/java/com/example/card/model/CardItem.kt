package com.example.card.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.card.util.Constants
import kotlinx.parcelize.Parcelize

@Entity(tableName = Constants.TABLE_NAME)
@Parcelize
data class CardItem(
    @PrimaryKey(autoGenerate = true)
    val _id:Int,
    val bankName:String,
    val cardData1:String,
    val cardData2: String,
    val cardNumber:String,
    val id:String,
    val userName:String,
    val cvv:String,
): Parcelable