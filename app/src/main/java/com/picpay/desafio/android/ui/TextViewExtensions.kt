package com.picpay.desafio.android.ui

import android.view.View
import android.widget.TextView

fun TextView.setTextOrGone(newText: String?) {
    newText?.let {
        if (it.isNotEmpty()) {
            text = it
            visibility = View.VISIBLE
        } else {
            goneView(this)
        }
    } ?: run {
        goneView(this)
    }
}

private fun goneView(view: View) {
    view.visibility = View.GONE
}