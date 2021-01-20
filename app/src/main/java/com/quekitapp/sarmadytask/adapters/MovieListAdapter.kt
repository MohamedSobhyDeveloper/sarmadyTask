package com.quekitapp.sarmadytask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.quekitapp.sarmadytask.R
import com.quekitapp.sarmadytask.databinding.MoviePhotoLayoutBinding
import com.quekitapp.sarmadytask.model.Photo
import com.stfalcon.imageviewer.StfalconImageViewer
import java.util.ArrayList


class MovieListAdapter(private val context: Context,val movieList: MutableList<Photo>) : RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        val itemBinding = MoviePhotoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(context,movieList[position])
    }



    fun addList(movielist: MutableList<Photo>) {
        this.movieList.addAll(movielist)
        notifyDataSetChanged()
    }




    override fun getItemCount(): Int {
        return movieList.size
    }



    class MyViewHolder(private val itemBinding: MoviePhotoLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(context: Context, moviephoto: Photo) {


            if (moviephoto.farm.equals("0")) {
                Glide.with(context).load(R.drawable.ad_banner).into(itemBinding.moviePhoto)

            } else {

                val photoUrl =
                    "http://farm" + moviephoto.farm + ".static.flickr.com/" + moviephoto.server + "/" + moviephoto.id + "_" + moviephoto.secret + ".jpg"

                Glide.with(context).load(photoUrl).into(itemBinding.moviePhoto)
                itemBinding.moviePhoto.setOnClickListener {
                    val urls = ArrayList<String>()
                    urls.add(photoUrl)
                    StfalconImageViewer.Builder(
                        context,
                        urls
                    ) { imageView: ImageView?, image: String? ->
                        val with = Glide.with(context)
                        val requestBuilder: RequestBuilder<Drawable>
                        requestBuilder = with.load(image)

                        requestBuilder.into(imageView!!)
                    }.withStartPosition(0)
                        .withTransitionFrom(itemBinding.moviePhoto)
                        .withImageChangeListener { x: Int -> println(x) }
                        .show()
                }

            }
        }
    }
}