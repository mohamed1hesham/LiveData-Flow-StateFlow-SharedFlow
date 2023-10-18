package com.example.observables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.observables.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        binding.btnLivedata.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.btnStateflow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it.toString()
                }
            }
        }

        binding.btnSharedflow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerSharedFlow()
            }
        }

        subscribeToObservables()
    }

    fun subscribeToObservables() {
        viewModel.livedata().observe(this) {
            binding.tvLivedata.text = it
        }

        lifecycleScope.launch {
            viewModel.sharedflow().collectLatest {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}