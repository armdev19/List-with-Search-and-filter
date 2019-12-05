package com.infernal93.listwithsearchandfilter.providers

import android.os.Handler
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.models.CategoryModel
import com.infernal93.listwithsearchandfilter.presenters.CategoryPresenter

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */
class CategoryProvider(var presenter: CategoryPresenter) {

    fun testLoadCategory(hasCategory: Boolean) {
        Handler().postDelayed({
            val categoryList: ArrayList<CategoryModel> = ArrayList()
            if (hasCategory) {
                val category1 = CategoryModel(name = "Books", icon = "https://cdn.icon-icons.com/icons2/405/PNG/512/Books_40673.png", category = "Books", price = 2965)
                val category2 = CategoryModel(name = "Sporting Goods", icon = "https://cdn.icon-icons.com/icons2/716/PNG/512/Goal_icon-icons.com_62267.png", category = "Sporting Goods", price = 999)
                val category3 = CategoryModel(name = "Music", icon = "https://cdn.icon-icons.com/icons2/279/PNG/512/Audicity_30229.png", category = "Music", price = 15)
                val category4 = CategoryModel(name = "Travel", icon = "https://cdn.icon-icons.com/icons2/1949/PNG/512/free-30-instagram-stories-icons33_122580.png", category = "Travel", price = 0)
                val category5 = CategoryModel(name = "Electronics", icon = "https://cdn.icon-icons.com/icons2/892/PNG/512/electronics_icon-icons.com_69106.png", category = "Electronics", price = 100)
                val category6 = CategoryModel(name = "Other", icon = "https://cdn.icon-icons.com/icons2/1154/PNG/512/1486564397-menu-green_81507.png", category = "Other", price = 50)

                categoryList.add(category1)
                categoryList.add(category2)
                categoryList.add(category3)
                categoryList.add(category4)
                categoryList.add(category5)
                categoryList.add(category6)
            } else {

            }

            presenter.categoryLoaded(categoryList = categoryList)
        }, 2000)
    }
}