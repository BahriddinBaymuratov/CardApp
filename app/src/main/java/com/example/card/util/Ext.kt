package com.example.card.util

import android.content.Context
import es.dmoral.toasty.Toasty

fun Context.success(text: String) {
    Toasty.success(this, text, Toasty.LENGTH_LONG).show()
}
fun Context.error(text: String) {
    Toasty.error(this, text, Toasty.LENGTH_LONG).show()
}