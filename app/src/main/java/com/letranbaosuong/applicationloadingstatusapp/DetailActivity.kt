package com.letranbaosuong.applicationloadingstatusapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.letranbaosuong.applicationloadingstatusapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }
}
