package com.devtau.template.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.devtau.template.App
import com.devtau.template.databinding.FragmentDetailsBinding
import com.devtau.template.data.model.Apple
import com.devtau.template.presentation.viewmodels.DetailsViewModel
import com.devtau.template.presentation.vmFactories.DetailsViewModelFactory
import com.devtau.template.presentation.vmFactories.GenericSavedStateViewModelFactory
import com.devtau.template.utils.EventObserver
import javax.inject.Inject

/**
 * [Apple] details fragment
 */
class DetailsFragment: BaseFragment() {

    @Inject
    internal lateinit var detailViewModelFactory: DetailsViewModelFactory

    private val _viewModel: DetailsViewModel by viewModels {
        GenericSavedStateViewModelFactory(detailViewModelFactory, this, requireArguments())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = _viewModel
            content.viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner
            _viewModel.subscribeToVM(this)
            initNav(this)
        }
        return binding.root
    }

    private fun DetailsViewModel.subscribeToVM(binding: FragmentDetailsBinding) {
        error.observe(viewLifecycleOwner, EventObserver {
            listener.showSnackBar(it)
        })
    }

    private fun initNav(binding: FragmentDetailsBinding) {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}