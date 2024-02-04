package com.movieapppoc.core.presentation.common_components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.movieapppoc.R

sealed class MenuAction(
    @StringRes val label: Int,
    @DrawableRes val icon: Int) {

    object Account : MenuAction(R.string.account, android.R.drawable.ic_media_pause)
}