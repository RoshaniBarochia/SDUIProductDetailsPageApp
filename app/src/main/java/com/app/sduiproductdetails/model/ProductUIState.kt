package com.app.sduiproductdetails.model

data class ProductUIState(
    val isLoading: Boolean = false,
    val page: PageConfig? = null,
    val theme: ThemeConfig? = null,
    val error: String? = null
)



