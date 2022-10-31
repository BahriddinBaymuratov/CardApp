package com.example.card.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.card.R
import com.example.card.databinding.ActivityAddUpdateBinding
import com.example.card.model.CardItem
import com.example.card.network.RetroInstance
import com.example.card.util.success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUpdateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddUpdateBinding.inflate(layoutInflater) }
    private var isForUpdate = false
    private var card: CardItem? = null
    var s: String = ""
    var s2: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            finish()
        }

        try {
            card = intent.getParcelableExtra("card")!!
            val color = intent.extras?.getInt("color") ?: R.color.purple_200
            card?.let {
                isForUpdate = true
                binding.apply {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(this@AddUpdateActivity, color)
                    )
                    editCardNumbers.setText(it.cardNumber)
                    editCvv.setText(it.cvv)
                    editDate1.setText(it.cardData1)
                    editDate2.setText(it.cardData2)
                    editHolderName.setText(it.userName)
                    textCardName.text = it.bankName
                    textCardHolderName.text = it.userName
                    textDate1.text = it.cardData1
                    textDate2.text = it.cardData2
                    textCardNumbers.text = it.cardNumber
                    binding.btnAddNewCard.text = "Update"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        editTexts()

        binding.btnAddNewCard.setOnClickListener {
            val cardNumber = binding.editCardNumbers.text.toString().trim()
            val date1 = binding.editDate1.text.toString().trim()
            val date2 = binding.editDate2.text.toString().trim()
            val name = binding.editHolderName.text.toString().trim()
            val cvv = binding.editCvv.text.toString().trim()
            if (isForUpdate) {
                RetroInstance.apiService.updateCard(
                    card?.id!!,
                    CardItem(
                        0,
                        "Anor Bank",
                        date1,
                        date2,
                        cardNumber,
                        card?.id!!,
                        name,
                        cvv
                    )
                ).enqueue(object : Callback<CardItem> {
                    override fun onResponse(
                        call: Call<CardItem>,
                        response: Response<CardItem>
                    ) {
                        if (response.isSuccessful) {
                            success("Successfully updated")
                        }
                    }

                    override fun onFailure(call: Call<CardItem>, t: Throwable) {
                        error(t.message.toString())
                    }
                })
            } else {
                RetroInstance.apiService.createCard(
                    CardItem(
                        0,
                        "Anor Bank",
                        date1,
                        date2,
                        cardNumber,
                        "0",
                        name,
                        cvv
                    )
                ).enqueue(object : Callback<CardItem> {
                    override fun onResponse(
                        call: Call<CardItem>,
                        response: Response<CardItem>
                    ) {
                        if (response.isSuccessful) {
                            success("Successfully created")
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<CardItem>, t: Throwable) {
                        error(t.message.toString())
                    }
                })
            }
        }
    }

    private fun editTexts() {
        binding.apply {
            editCardNumbers.addTextChangedListener { text ->
                s = text.toString()
                textCardNumbers.text = s
                when {
                    s.length > 12 -> {
                        val saveText = "${s.substring(0, 4)}  ${s.substring(4, 8)}  ${
                            s.substring(
                                8,
                                12
                            )
                        }  ${s.substring(12)}"
                        textCardNumbers.text = saveText
                    }
                    s.length > 8 -> {
                        val saveText =
                            "${s.substring(0, 4)}  ${s.substring(4, 8)}  ${s.substring(8)}"
                        textCardNumbers.text = saveText
                    }
                    s.length > 4 -> {
                        val saveText = "${s.substring(0, 4)}  ${s.substring(4)}"
                        textCardNumbers.text = saveText
                    }
                }
            }
            editHolderName.addTextChangedListener {
                textCardHolderName.text = it.toString()
            }
            editDate1.addTextChangedListener {
                s2 = it.toString()
                if (s2.length == 2) {
                    s2 += "/"
                }
                textDate1.text = s2
            }
            editDate2.addTextChangedListener {
                textDate2.text = it.toString()
            }
        }
    }
}