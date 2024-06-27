package com.example.superheroesapp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.superheroesapp.R
import com.example.superheroesapp.data.SuperheroApiService
import com.example.superheroesapp.data.SuperheroResponse
import com.example.superheroesapp.databinding.ActivityDetailBinding
import com.example.superheroesapp.databinding.ActivityMainBinding
import com.example.superheroesapp.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var nameDetailTextView: TextView
    lateinit var superHero:SuperheroResponse.Superhero
    companion object {
        const val EXTRA_SUPERHERO_ID:String  = "EXTRA_SUPERHERO_ID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
        val id: Int = intent.getIntExtra(EXTRA_SUPERHERO_ID,-1)!!
        getSuperheroById(id)

    }

    private fun loadData() {
        binding.nameDetailTextView.text = superHero.name
        Picasso.get().load(superHero.image.url).into(binding.photoImageView)

        // Biography
        binding.content.realNameTextView.text = superHero.biography.realName
        binding.content.alterEgosTextView.text = superHero.biography.alterEgos
        binding.content.publisherTextView.text = superHero.biography.publisher
        binding.content.placeOfBirthTextView.text = superHero.biography.placeOfBirth
        binding.content.firstAppearanceTextView.text = superHero.biography.firstApperance
        binding.content.publisherTextView.text = superHero.biography.publisher
        binding.content.alignmentTextView.text = superHero.biography.alignment

        if (superHero.biography.alignment == "good") {
            binding.content.alignmentTextView.setTextColor(getResources().getColor(R.color.good_color))
        } else {
            binding.content.alignmentTextView.setTextColor(getResources().getColor(R.color.evil_color))
        }
        // Work
        binding.content.occupationTextView.text = superHero.work.occupation
        binding.content.baseTextView.text = superHero.work.base
        // Stats
        binding.content.intelligenceStatBar.progress = superHero.stats.intelligence
        binding.content.strengthStatBar.progress = superHero.stats.strength
        binding.content.speedStatBar.progress = superHero.stats.speed
        binding.content.durabilityStatBar.progress = superHero.stats.durability
        binding.content.powerStatBar.progress = superHero.stats.power
        binding.content.combatStatBar.progress = superHero.stats.combat
        //binding.content.radarChart.data.dataSets =
    }
    fun getSuperheroById(id:Int){

        //binding.progress.visibility = View.VISIBLE
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = RetrofitProvider.getSuperheroApiService()
                val result = apiService.getSuperheroeById(id)
                superHero = result
                runOnUiThread {
                    /*binding.progress.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyPlaceholder.visibility = View.GONE*/
                    /*if(result.response == "success")
                        superHero = result.
                    else superHero = emptyList()*/
                    Toast.makeText(this@DetailActivity,result.name, Toast.LENGTH_LONG).show()
                    loadData()
                }
                Log.i("HTTP", "${result.id}-->${result.name}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}