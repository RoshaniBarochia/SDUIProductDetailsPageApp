package com.app.sduiproductdetails.repository

import android.content.Context
import com.app.sduiproductdetails.model.ProductUIState
import com.app.sduiproductdetails.utils.JsonLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    suspend fun fetchProductUI(context: Context): ProductUIState = withContext(Dispatchers.IO) {
        val productDetail = JsonLoader.loadProductJson(context)
        ProductUIState(
            page = productDetail.page,
            theme = productDetail.page.theme
        )
    }
}