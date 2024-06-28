package com.example.superheroesapp.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroesapp.R
import com.example.superheroesapp.data.SuperheroResponse
import com.example.superheroesapp.databinding.ActivityDetailBinding
import com.example.superheroesapp.utils.RetrofitProvider
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet
import com.squareup.picasso.Picasso
import com.xxmassdeveloper.mpchartexample.custom.RadarMarkerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var nameDetailTextView: TextView
    lateinit var superHero:SuperheroResponse.Superhero
    private var chart: RadarChart? = null
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


        chart = binding.content.radarChart !!

        chart!!.setBackgroundColor(Color.rgb(60, 65, 82))

        chart!!.description.isEnabled = false

        chart!!.webLineWidth = 1f
        chart!!.webColor = Color.LTGRAY
        chart!!.webLineWidthInner = 1f
        chart!!.webColorInner = Color.LTGRAY
        chart!!.webAlpha = 100


        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        val mv: MarkerView = RadarMarkerView(this, R.layout.custom_marker_view)
        mv.chartView = chart // For bounds control
        chart!!.marker = mv // Set the marker to the chart

        setDataRadarChart();
    }

    private fun setDataRadarChart() {

        val mul : Float = 80f;
        val min : Float = 20f;
        var cnt: Int = 5;

        var entries1 : ArrayList<RadarEntry> = ArrayList<RadarEntry>();
        var entries2 : ArrayList<RadarEntry>  = ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        for ( i in 0..< cnt) {
            var val1 : Float =  ((Math.random() * mul) + min).toFloat()
            entries1.add(RadarEntry(val1))

            var val2 : Float = ((Math.random() * mul) + min).toFloat()
            entries2.add(RadarEntry(val2));
        }

        var set1 : RadarDataSet =  RadarDataSet(entries1, "Last Week");
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        var set2 : RadarDataSet =  RadarDataSet(entries2, "Last Week");
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(2f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        var sets : ArrayList<IRadarDataSet> = ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        var data : RadarData =  RadarData(sets);
        //data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart?.setData(data);
        chart?.invalidate();

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