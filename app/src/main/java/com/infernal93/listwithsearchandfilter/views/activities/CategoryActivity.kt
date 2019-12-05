package com.infernal93.listwithsearchandfilter.views.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.models.CategoryModel
import com.infernal93.listwithsearchandfilter.presenters.CategoryPresenter
import com.infernal93.listwithsearchandfilter.views.CategoryView
import com.infernal93.listwithsearchandfilter.views.adapters.CategoryAdapter
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : MvpAppCompatActivity(), CategoryView {

    @InjectPresenter
    lateinit var categoryPresenter: CategoryPresenter

    private lateinit var mAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        btn_sort.setOnClickListener {
            val builder = AlertDialog.Builder(this@CategoryActivity)
            builder.setTitle("Sorted category")
            builder.setMessage("Sorted by:")
            builder.setPositiveButton("By name") {dialogInterface, i ->
                mAdapter.sortByName()
            }

            builder.setNegativeButton("By price") {dialogInterface, i ->
                mAdapter.sortByPrice()
            }
            builder.show()
        }

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
