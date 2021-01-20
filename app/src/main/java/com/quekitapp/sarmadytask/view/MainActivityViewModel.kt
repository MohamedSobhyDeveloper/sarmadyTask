package com.quekitapp.sarmadytask.view

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.interactive.ksi.propertyturkeybooking.interfaces.HandleRetrofitResp
import com.interactive.ksi.propertyturkeybooking.retrofitconfig.HandelCalls
import com.interactive.ksi.propertyturkeybooking.utlitites.DataEnum
import com.quekitapp.sarmadytask.model.MovieModel
import java.util.HashMap

class MainActivityViewModel:ViewModel(),HandleRetrofitResp {

    @JvmField
    var movieResponseLiveData = MutableLiveData<MovieModel>()


    fun getMoviePhoto(context:Context,requestBody: HashMap<String, String?>?){
        HandelCalls.getInstance(context)?.call(DataEnum.moviephoto.name,requestBody,true,this)

    }

    override fun onResponseSuccess(flag: String?, o: Any?) {
         if (flag==DataEnum.moviephoto.name){
             val movieModel: MovieModel = o as MovieModel
             movieResponseLiveData.value=movieModel
         }

    }

    override fun onResponseFailure(flag: String?, o: String?) {
    }

    override fun onNoContent(flag: String?, code: Int) {
    }

    override fun onBadRequest(flag: String?, o: Any?) {
    }


}