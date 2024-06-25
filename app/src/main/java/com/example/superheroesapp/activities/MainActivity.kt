package com.example.superheroesapp.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superheroesapp.R
import com.example.superheroesapp.adapters.SuperheroAdapter
import com.example.superheroesapp.data.SuperheroApiService
import com.example.superheroesapp.data.SuperheroResponse
import com.example.superheroesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity() {
    var superHeroList: List<SuperheroResponse.Superhero> = emptyList()
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = SuperheroAdapter ( superHeroList){position->
                navigateToDetail(superHeroList[position])
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        /*binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchSuperheroes(searchText)
        }*/
        //searchByName("a")
        binding.progress.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
    }

    private fun navigateToDetail(superhero: SuperheroResponse.Superhero) {
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO_ID, superhero.id)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)

        initSearchView(menu?.findItem(R.id.menu_search))

        return true
    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            var searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //searchSuperheroes(query!!)
                    searchByName(query!!)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }
    }

    private fun searchByName(query: String){
        binding.progress.visibility = View.VISIBLE
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(SuperheroApiService::class.java)
                val result = apiService.findSuperheroesByName(query)
                runOnUiThread {
                    binding.progress.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyPlaceholder.visibility = View.GONE
                    superHeroList = result.results
                    adapter.updateData(result.results)
                }
                //Log.i("HTTP", "${result.results}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun getRetrofit(): Retrofit {
        /*val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()*/
        return Retrofit.Builder()
            .baseUrl("https://superheroapi.com/api/7252591128153666/")
            //.client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



}