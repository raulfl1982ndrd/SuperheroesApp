package com.example.superheroesapp.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroesapp.R
import com.example.superheroesapp.databinding.ActivityDetailBinding
import com.example.superheroesapp.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var textView: TextView
    companion object {
        const val EXTRA_SUPERHERO_ID:String  = "EXTRA_SUPERHERO_ID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val id: String = intent.getStringExtra(EXTRA_SUPERHERO_ID)!!

        binding.TextViewtext.text = id
    }


}