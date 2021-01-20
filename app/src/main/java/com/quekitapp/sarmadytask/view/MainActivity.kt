package com.quekitapp.sarmadytask.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.quekitapp.sarmadytask.adapters.MovieListAdapter
import com.quekitapp.sarmadytask.databinding.ActivityMainBinding
import com.quekitapp.sarmadytask.model.Photo

class MainActivity : AppCompatActivity() {
    private var movieViewModel: MainActivityViewModel? = null

    val layoutManager = LinearLayoutManager(this)
    var adapter: MovieListAdapter? = null
    private var totalPage = 0
    private var currentPage = 1
    private var isLoading = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initialize(binding)

        getMoviePhoto(binding)


    }

    private fun initialize(binding: ActivityMainBinding) {
        movieViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        binding.movieRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.e("TAG", "onScrollStateChanged: ")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                    if (currentPage <= totalPage && isLoading) {
                        isLoading = !isLoading
                        currentPage++
                        val meMap = LinkedHashMap<String, String?>()
                        meMap["method"] ="flickr.photos.search"
                        meMap["format"] ="json"
                        meMap["nojsoncallback"] ="50"
                        meMap["text"] ="a"
                        meMap["page"] = currentPage.toString()
                        meMap["per_page"] ="20"
                        meMap["api_key"] ="d17378e37e555ebef55ab86c4180e8dc"

                        movieViewModel!!.getMoviePhoto(this@MainActivity, meMap)
                    }
                }
            }
        })

    }

    private fun getMoviePhoto(binding: ActivityMainBinding) {
        val meMap = LinkedHashMap<String, String?>()
        meMap["method"] ="flickr.photos.search"
        meMap["format"] ="json"
        meMap["nojsoncallback"] ="50"
        meMap["text"] ="a"
        meMap["page"] = currentPage.toString()
        meMap["per_page"] ="20"
        meMap["api_key"] ="d17378e37e555ebef55ab86c4180e8dc"

        movieViewModel!!.getMoviePhoto(this, meMap)
        movieViewModel!!.movieResponseLiveData.observe(this, {
            val size=it.photos.photo.size
            totalPage=it.photos.pages
            val movieList: MutableList<Photo> = it.photos.photo
            var position = 5
            for (i in 0..size) {
            if (i==position){
                val photo=Photo("0","","","","","","","","")
                 movieList.add(i, photo)
                 position=position+6
            }
            }

            if (currentPage == 1) {
                binding.movieRecycler.setLayoutManager(layoutManager)
                adapter = MovieListAdapter(this, movieList)
                binding.movieRecycler.setAdapter(adapter)
            } else {
                adapter?.addList(movieList)
            }

            isLoading = true

        })

    }

}