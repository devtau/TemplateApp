package com.devtau.template.data.source.remote.response

import com.devtau.template.data.model.Apple

/**
 * Backend response class on [Apple]s list saved
 * @param savedItemsCount count of successfully saved objects
 */
class ApplesListSavedResponse(
    val savedItemsCount: Int
): BaseResponse()