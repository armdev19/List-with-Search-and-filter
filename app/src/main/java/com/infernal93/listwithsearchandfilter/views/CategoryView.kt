package com.infernal93.listwithsearchandfilter.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.infernal93.listwithsearchandfilter.models.Category

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CategoryView: MvpView {

    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupCategoryList(categoryList: ArrayList<Category>)
    fun startLoading()
    fun endLoading()
    fun logOut()
}