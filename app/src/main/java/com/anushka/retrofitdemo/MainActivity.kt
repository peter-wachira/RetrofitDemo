package com.anushka.retrofitdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getSortedAlbums(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumsItem = albumList.next()
                    Log.i("MYTAG", albumsItem.title)
                    val  result = " "+"AlbumTitle : ${albumsItem.title}"+"\n" +
                                  " "+"Album id : ${albumsItem.id}"+"\n" +
                                  " "+"User id : ${albumsItem.userId}"+"\n" +"\n\n\n\n"
                    text_view.append(result)

                }
            }
        })


    }
}
