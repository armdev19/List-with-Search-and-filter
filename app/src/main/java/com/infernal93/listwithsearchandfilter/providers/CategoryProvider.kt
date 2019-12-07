package com.infernal93.listwithsearchandfilter.providers

import android.os.Handler
import com.infernal93.listwithsearchandfilter.models.Category
import com.infernal93.listwithsearchandfilter.models.CategoryApi
import com.infernal93.listwithsearchandfilter.models.CategoryTest
import com.infernal93.listwithsearchandfilter.presenters.CategoryPresenter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */
class CategoryProvider(var presenter: CategoryPresenter) {

    companion object {
        const val KEY = "5de979d34658275ac9dc2375"
    }

    fun testLoadCategory(hasCategory: Boolean) {
        Handler().postDelayed({
            val categoryList: ArrayList<CategoryTest> = ArrayList()
            if (hasCategory) {
                val category1 = CategoryTest(name = "Books", icon = "https://cdn.icon-icons.com/icons2/405/PNG/512/Books_40673.png", category = "Books", price = 2965)
                val category2 = CategoryTest(name = "Sporting Goods", icon = "https://cdn.icon-icons.com/icons2/716/PNG/512/Goal_icon-icons.com_62267.png", category = "Sporting Goods", price = 999)
                val category3 = CategoryTest(name = "Music", icon = "https://cdn.icon-icons.com/icons2/279/PNG/512/Audicity_30229.png", category = "Music", price = 15)
                val category4 = CategoryTest(name = "Travel", icon = "https://cdn.icon-icons.com/icons2/1949/PNG/512/free-30-instagram-stories-icons33_122580.png", category = "Travel", price = 0)
                val category5 = CategoryTest(name = "Electronics", icon = "https://cdn.icon-icons.com/icons2/892/PNG/512/electronics_icon-icons.com_69106.png", category = "Electronics", price = 100)
                val category6 = CategoryTest(name = "Other", icon = "https://cdn.icon-icons.com/icons2/1154/PNG/512/1486564397-menu-green_81507.png", category = "Other", price = 50)

                categoryList.add(category1)
                categoryList.add(category2)
                categoryList.add(category3)
                categoryList.add(category4)
                categoryList.add(category5)
                categoryList.add(category6)
            }
            //presenter.categoryLoaded(categoryList = categoryList)
        }, 2000)
    }

    fun loadCategory() {
        // x-apikey interceptor for restdb API
        fun createOkHttpClient(): OkHttpClient? {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val original = chain.request()
                    val originalHttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", KEY)
                        .build()
                    val requestBuilder = original.newBuilder()
                        .url(url)
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
            // logging interceptor
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            return httpClient.build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://testcategory-d6d7.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val api = retrofit.create(CategoryApi::class.java)
        api.fetchAllCategory().enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                //Log.d(TAG, "onResponse: ${response.body()!![0].name}")
                val category: List<Category> = response.body()!!
                presenter.categoryLoaded(categoryList = category as ArrayList<Category>)
            }
        })
    }
}