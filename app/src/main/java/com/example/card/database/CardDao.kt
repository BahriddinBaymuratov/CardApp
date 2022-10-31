package com.example.card.database

import androidx.room.*
import com.example.card.model.CardItem
import com.example.card.util.Constants


@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCard(cardItem: CardItem)

    @Insert
    fun saveCardList(cardList: List<CardItem>)

    @Update
    fun updateCard(cardItem: CardItem)

    @Delete
    fun deleteCard(cardItem: CardItem)

    @Query("SELECT * FROM ${Constants.TABLE_NAME}")
    fun getAllCards(): List<CardItem>

    @Query("DELETE FROM ${Constants.TABLE_NAME}")
    fun deleteAllCards()

}