package com.infernal93.listwithsearchandfilter.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.models.CategoryModel

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */
class CategoryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mCategoryList: ArrayList<CategoryModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_category, parent, false)
        return CategoryViewHolder(itemView = itemView)

    }

    override fun getItemCount(): Int {
        return mCategoryList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}