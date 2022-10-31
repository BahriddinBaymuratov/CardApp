package com.example.card.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.card.adapter.CardAdapter
import com.example.card.database.CardDatabase
import com.example.card.databinding.ActivityMainBinding
import com.example.card.model.CardDTO
import com.example.card.model.CardItem
import com.example.card.network.RetroInstance
import com.example.card.util.NetworkUtil
import com.example.card.util.error
import com.example.card.util.success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val cardAdapter by lazy { CardAdapter() }
    private val cardDatabase by lazy { CardDatabase(this) }
    private lateinit var networkUtils: NetworkUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        networkUtils = NetworkUtil(this)
        setupRv()
        if (networkUtils.isNetworkConnected()) {
            getFromApi()
            success("From API")
        } else {
            getFromDatabase()
            success("From Database")
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            cardAdapter.notifyDataSetChanged()
        }
        binding.fab.setOnClickListener {
            if (networkUtils.isNetworkConnected()) {
                startActivity(Intent(this, AddUpdateActivity::class.java))
            } else {
                error("Check internet connection")
            }
        }
        cardAdapter.onItemClick = { item, color ->
            if (networkUtils.isNetworkConnected()) {
                val bundle = bundleOf(":card" to item)
                val intent = Intent(this, AddUpdateActivity::class.java)
                intent.putExtra("color", color)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                error("Check internet connection")
            }
        }
        cardAdapter.onItemLongClick = { item, pos ->
            deleteCard(item, pos)
        }
    }

    private fun deleteCard(item: CardItem, pos: Int) {
        cardDatabase.dao.deleteCard(item)
        cardAdapter.notifyItemRemoved(pos)
        cardAdapter.cardList.removeAt(pos)
        RetroInstance.apiService.deleteCard(item.id).enqueue(object : Callback<CardItem> {
            override fun onResponse(call: Call<CardItem>, response: Response<CardItem>) {
                if (response.isSuccessful) {
                    success("Successfully deleted")
                }
            }

            override fun onFailure(call: Call<CardItem>, t: Throwable) {
                error(t.message.toString())
            }
        })
    }

    private fun getFromApi() {
        RetroInstance.apiService.getAllCardList().enqueue(object : Callback<CardDTO> {
            override fun onResponse(call: Call<CardDTO>, response: Response<CardDTO>) {
                if (response.isSuccessful) {
                    binding.progressBar.isVisible = false
                    cardAdapter.cardList = response.body()!!
                    cardDatabase.dao.deleteAllCards()
                    cardDatabase.dao.saveCardList(response.body()!!)
                    cardAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<CardDTO>, t: Throwable) {
                error(t.message.toString())
            }
        })
    }

    private fun getFromDatabase() {
        cardAdapter.cardList = cardDatabase.dao.getAllCards().toMutableList()
        binding.progressBar.isVisible = false
        cardAdapter.notifyDataSetChanged()
    }

    private fun setupRv() = binding.recyclerView.apply {
        adapter = cardAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    override fun onRestart() {
        super.onRestart()
        getFromApi()
    }

}