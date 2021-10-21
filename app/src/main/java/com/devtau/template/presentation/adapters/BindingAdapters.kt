package com.devtau.template.presentation.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.devtau.template.data.model.Apple
import com.devtau.template.presentation.viewmodels.DetailsViewModel
import com.devtau.template.presentation.viewmodels.ListViewModel

@BindingAdapter("circleImage")
fun ImageView.loadCircleImage(iconUrl: String?) {
    loadImage(iconUrl, true)
}

@BindingAdapter("rectImage")
fun ImageView.loadRectangleImage(iconUrl: String?) {
    loadImage(iconUrl, false)
}

private fun ImageView.loadImage(iconUrl: String?, isCircle: Boolean) {
    val glide = Glide.with(this).load(iconUrl)
    if (isCircle) glide.transform(CircleCrop())
    glide.transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("listItems")
fun RecyclerView.setListItems(entries: List<Apple>?) = (adapter as ApplesAdapter).submitList(entries)

/**
 * Reloads the data when the pull-to-refresh is triggered.
 *
 * Creates the `android:onRefresh` for a [SwipeRefreshLayout].
 */
@BindingAdapter("android:onRefresh")
fun SwipeRefreshLayout.setOnRefreshListener(viewModel: ListViewModel) {
    setOnRefreshListener { viewModel.fetchItemsFromBackend() }
}

/**
 * Reloads the data when the pull-to-refresh is triggered.
 *
 * Creates the `android:onRefresh` for a [SwipeRefreshLayout].
 */
@BindingAdapter("android:onRefresh")
fun SwipeRefreshLayout.setOnRefreshListener(viewModel: DetailsViewModel) {
    setOnRefreshListener { viewModel.fetchItemFromBackend() }
}