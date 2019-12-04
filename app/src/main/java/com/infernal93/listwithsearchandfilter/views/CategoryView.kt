package com.infernal93.listwithsearchandfilter.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Armen Mkhitaryan on 04.12.2019.
 */

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CategoryView: MvpView {
}