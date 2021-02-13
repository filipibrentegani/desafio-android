package com.picpay.desafio.android.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("setVisible")
fun setVisible(view: View, visible: Boolean) {
    view.setVisible(visible)
}