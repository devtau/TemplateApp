package com.devtau.template.presentation.vmFactories

import androidx.lifecycle.SavedStateHandle
import com.devtau.template.data.source.repositories.ApplesRepository
import com.devtau.template.presentation.ResourceResolver
import com.devtau.template.presentation.viewmodels.DetailsViewModel
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    private val applesRepository: ApplesRepository,
    private val resourceResolver: ResourceResolver
): ViewModelAssistedFactory<DetailsViewModel> {
    override fun create(handle: SavedStateHandle): DetailsViewModel =
        DetailsViewModel(handle, applesRepository, resourceResolver)
}