package com.infernal93.listwithsearchandfilter.views.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.models.Category
import com.infernal93.listwithsearchandfilter.models.CategoryApi
import com.infernal93.listwithsearchandfilter.models.CategoryModel
import com.infernal93.listwithsearchandfilter.presenters.CategoryPresenter
import com.infernal93.listwithsearchandfilter.views.CategoryView
import com.infernal93.listwithsearchandfilter.views.adapters.CategoryAdapter
import kotlinx.android.synthetic.main.activity_category.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class CategoryActivity : MvpAppCompatActivity(), CategoryView {
    private val TAG = "CategoryActivity"
    var KEY = "5de979d34658275ac9dc2375"

    @InjectPresenter
    lateinit var categoryPresenter: CategoryPresenter

    private lateinit var mAdapter: CategoryAdapter

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        // перенести отсюда
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
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            return httpClient.build()
        }




        var retrofit = Retrofit.Builder()
            .baseUrl("https://testcategory-d6d7.restdb.io/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

        val api = retrofit.create(CategoryApi::class.java)
        api.fetchAllCategory().enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                Log.d(TAG, "onResponse: ${response.body()!![0].name}")
            }

        })

        // Filter list
        btn_filter.setOnClickListener {

        }

        // Sort list
        btn_sort.setOnClickListener {
            val builder = AlertDialog.Builder(this@CategoryActivity)
            builder.setTitle(R.string.alert_dialog_title)
            builder.setMessage(R.string.alert_dialog_message)
            builder.setPositiveButton(R.string.alert_dialog_first_button) {dialogInterface, i ->
                mAdapter.sortByName()
            }

            builder.setNegativeButton(R.string.alert_dialog_second_button) {dialogInterface, i ->
                mAdapter.sortByPrice()
            }
            builder.show()
        }
        // Search
        txt_category_search.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.search(s.toString())
            }

        })

        categoryPresenter.loadCategory()

        mAdapter = CategoryAdapter()
        recycler_category.adapter = mAdapter
        recycler_category.layoutManager = LinearLayoutManager(applicationContext, OrientationHelper.VERTICAL, false)
        recycler_category.setHasFixedSize(true)
    }

    override fun showError(textResource: Int) {
        txt_category_no_items.text = getString(textResource)
    }

    override fun setupEmptyList() {
        recycler_category.visibility = View.GONE
        btn_filter.visibility = View.GONE
        btn_sort.visibility = View.GONE
        txt_category_no_items.visibility = View.VISIBLE
    }

    override fun setupCategoryList(categoryList: ArrayList<CategoryModel>) {
        recycler_category.visibility = View.VISIBLE
        btn_filter.visibility = View.VISIBLE
        btn_sort.visibility = View.VISIBLE
        txt_category_no_items.visibility = View.GONE

        mAdapter.setupCategory(categoryList = categoryList)
    }

    override fun startLoading() {
        recycler_category.visibility = View.GONE
        txt_category_no_items.visibility = View.GONE
        btn_filter.visibility = View.GONE
        btn_sort.visibility = View.GONE
        cpv_category.visibility = View.VISIBLE
    }

    override fun endLoading() {
        cpv_category.visibility = View.GONE
    }
}
