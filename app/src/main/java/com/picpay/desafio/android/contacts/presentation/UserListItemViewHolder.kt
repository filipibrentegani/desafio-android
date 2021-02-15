package com.picpay.desafio.android.contacts.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.contacts.data.User
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.ui.setTextOrGone
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.name.setTextOrGone(user.name)
        binding.username.setTextOrGone(user.username)
        binding.progressBar.visibility = View.VISIBLE
            user.img?.let {
                Picasso.get()
                    .load(it)
                    .error(R.drawable.ic_round_account_circle)
                    .into(binding.picture, object : Callback {
                        override fun onSuccess() {
                            binding.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception?) {
                            binding.progressBar.visibility = View.GONE
                        }
                    })
            }
    }

    fun unbind() {
        binding.picture.setImageDrawable(null)
    }

    companion object {
        fun newInstance(parent: ViewGroup): UserListItemViewHolder {
            val binding = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return UserListItemViewHolder(binding)
        }
    }
}