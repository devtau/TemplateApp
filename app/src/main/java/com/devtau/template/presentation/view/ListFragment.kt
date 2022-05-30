package com.devtau.template.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.devtau.template.App
import com.devtau.template.R
import com.devtau.template.data.model.Apple
import com.devtau.template.databinding.FragmentListBinding
import com.devtau.template.presentation.adapters.ApplesAdapter
import com.devtau.template.presentation.viewmodels.ListViewModel
import com.devtau.template.utils.EventObserver
import com.devtau.template.utils.observeTextChanges
import javax.inject.Inject

/**
 * Fragment containing list of [Apple]s
 */
class ListFragment: BaseFragment() {

    @Inject
    lateinit var _viewModel: ListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)

        listener = when {
            context is Listener -> context
            parentFragment is Listener -> parentFragment as Listener
            else -> throw ClassCastException("$context must implement ${Listener::class.qualifiedName}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        val binding = FragmentListBinding.inflate(inflater, container, false).apply {
            viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner
            _viewModel.subscribeToVM(this)

            (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
            listView.adapter = ApplesAdapter(_viewModel)
            val delay = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            fab.postDelayed({ fab.show() }, delay)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        processSearchMenuItem(menu.findItem(R.id.actionSearch))
        processFavoritesMenuItem(menu.findItem(R.id.actionFilterFavorites))
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun ListViewModel.subscribeToVM(binding: FragmentListBinding) {
        openAppleDetailsEvent.observe(viewLifecycleOwner, EventObserver { model ->
            model.id?.let {
                listener.showDetails(binding.toolbar, it, model.title)
            }
        })

        error.observe(viewLifecycleOwner, EventObserver {
            listener.showSnackBar(it)
        })
    }

    private fun processSearchMenuItem(item: MenuItem) {
        compositeDisposable.add(
            (item.actionView as SearchView)
                .observeTextChanges()
                .subscribe(_viewModel::searchQueryEntered)
        )
    }

    private fun processFavoritesMenuItem(item: MenuItem) {
        item.setOnMenuItemClickListener {
            _viewModel.onFavoritesFilterClicked()
            return@setOnMenuItemClickListener true
        }
        _viewModel.favoritesFilterIcon.observe(viewLifecycleOwner) {
            if (it != null) item.icon = it
        }
    }
}