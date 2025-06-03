package com.app.sduiproductdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.app.sduiproductdetails.nav.NavGraph
import com.app.sduiproductdetails.ui.theme.SDUIProductDetailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SDUIProductDetailsTheme {
                NavGraph()
            }
        }

    }
}



