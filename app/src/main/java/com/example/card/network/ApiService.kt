package com.example.card.network

import com.example.card.model.CardDTO
import com.example.card.model.CardItem
import com.example.card.util.Constants
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET(Constants.END_PONT)
    fun getAllCardList(): Call<CardDTO>

    @POST(Constants.END_PONT)
    fun createCard(@Body cardDTOItem: CardItem): Call<CardItem>

    @PUT("${Constants.END_PONT}/{id}")
    fun updateCard(@Path("id") id: String, @Body cardDTOItem: CardItem): Call<CardItem>

    @DELETE("${Constants.END_PONT}/{id}")
    fun deleteCard(@Path("id") id: String): Call<CardItem>
}
