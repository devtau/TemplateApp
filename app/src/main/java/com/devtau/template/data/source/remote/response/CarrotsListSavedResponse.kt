package com.devtau.template.data.source.remote.response

import com.devtau.template.data.model.Carrot

/**
 * Backend response class on [Carrot]s list saved
 * @param savedItemsCount count of successfully saved objects
 */
class CarrotsListSavedResponse(
    val savedItemsCount: Int
): BaseResponse()