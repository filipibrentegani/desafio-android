package com.picpay.desafio.android.contacts.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityContactsBinding

class ContactsActivity : AppCompatActivity() {

    private lateinit var adapter: UserListAdapter
    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityContactsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_contacts)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        adapter =
            UserListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.contacts.observe(this, androidx.lifecycle.Observer {
            adapter.users = it
        })
        viewModel.showError.observe(this, Observer { resourceString ->
            resourceString?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
        binding.executePendingBindings()
        if (savedInstanceState == null) {
            viewModel.getContactData()
        }
    }

}
