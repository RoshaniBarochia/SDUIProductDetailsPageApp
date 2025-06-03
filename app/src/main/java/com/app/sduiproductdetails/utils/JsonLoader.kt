package com.app.sduiproductdetails.utils

import android.content.Context
import com.app.sduiproductdetails.model.ProductUIConfig
import com.google.gson.Gson
import java.io.InputStreamReader

object JsonLoader {
    fun loadProductJson(context: Context): ProductUIConfig {
        val inputStream = context.assets.open("product-detail-sdui.json")
        val reader = InputStreamReader(inputStream)
        return Gson().fromJson(reader, ProductUIConfig::class.java)
    }
}
