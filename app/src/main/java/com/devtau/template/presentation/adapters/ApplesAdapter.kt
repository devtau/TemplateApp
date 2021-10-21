package com.devtau.template.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devtau.template.data.model.Apple
import com.devtau.template.databinding.ListItemBinding
import com.devtau.template.presentation.viewmodels.ListViewModel
import com.devtau.template.presentation.adapters.ApplesAdapter.VH

/**
 * List adapter for [Apple]s
 * @param viewModel viewModel of list
 */
class ApplesAdapter(
    private val viewModel: ListViewModel
): ListAdapter<Apple, VH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH.from(parent, viewModel)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH private constructor(
        private val binding: ListItemBinding,
        private val viewModel: ListViewModel
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Apple) {
            binding.viewModel = viewModel
            binding.model = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: ListViewModel): VH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(inflater, parent, false)
                return VH(binding, viewModel)
            }
        }
    }

    private class DiffCallback: DiffUtil.ItemCallback<Apple>() {
        override fun areItemsTheSame(oldItem: Apple, newItem: Apple) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Apple, newItem: Apple) = oldItem == newItem
    }
}