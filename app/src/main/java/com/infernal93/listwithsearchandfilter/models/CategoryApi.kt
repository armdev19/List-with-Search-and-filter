package com.infernal93.listwithsearchandfilter.models

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Armen Mkhitaryan on 05.12.2019.
 */
interface CategoryApi {

    @GET(value = "Mycategory")

    fun fetchAllCategory(): Call<List<Category>>
}