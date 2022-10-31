package com.example.card.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.card.model.CardItem
import com.example.card.util.Constants

@Database(entities = [CardItem::class], version = 1, exportSchema = false)
abstract class CardDatabase: RoomDatabase() {
    abstract val dao: CardDao

    companion object{
        @Volatile
        private var instance :CardDatabase? = null
        operator fun invoke(context: Context) = instance ?: synchronized(Any()){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            CardDatabase::class.java,
            Constants.DB_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

}