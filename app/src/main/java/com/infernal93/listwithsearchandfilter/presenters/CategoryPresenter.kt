package com.infernal93.listwithsearchandfilter.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.infernal93.listwithsearchandfilter.R
import com.infernal93.listwithsearchandfilter.models.Category
import com.infernal93.listwithsearchandfilter.providers.CategoryProvider
import com.infernal93.listwithsearchandfilter.views.CategoryView

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */

@InjectViewState
class CategoryPresenter: MvpPresenter<CategoryView>() {

    private lateinit var mAuth: FirebaseAuth

    fun loadCategory() {
        viewState.startLoading()
        //CategoryProvider(presenter = this).testLoadCategory(hasCategory = true)
        CategoryProvider(presenter = this).loadCategory()
    }

    fun categoryLoaded(categoryList: ArrayList<Category>) {
        viewState.endLoading()
        if (categoryList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.category_no_items_error)
        } else {
            viewState.setupCategoryList(categoryList = categoryList)
        }
    }

    fun logOut() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        viewState.logOut()
    }
}