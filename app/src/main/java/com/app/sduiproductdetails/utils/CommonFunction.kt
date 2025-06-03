package com.app.sduiproductdetails.utils

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.sduiproductdetails.model.ThemeConfig


// Converts theme JSON to Material3 ColorScheme
fun ThemeConfig.toColorScheme(): ColorScheme {
    return lightColorScheme(
        primary = Color(android.graphics.Color.parseColor(primary)),
        secondary = Color(android.graphics.Color.parseColor(secondary)),
        background = Color(android.graphics.Color.parseColor(surface)),
        surface = Color(android.graphics.Color.parseColor(surface)),
        error = Color(android.graphics.Color.parseColor(error))
    )
}

// Converts theme JSON to Material3 Typography
fun ThemeConfig.toTypography(): androidx.compose.material3.Typography {
    return androidx.compose.material3.Typography(
        headlineLarge = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        headlineMedium = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        bodyLarge = TextStyle(fontSize = 16.sp),
        bodyMedium = TextStyle(fontSize = 14.sp),
        bodySmall = TextStyle(fontSize = 12.sp)
    )
}



// Helper function to parse RGBA color string
fun parseRgba(rgba: String): Color {
    val regex = Regex("rgba\\((\\d+),\\s*(\\d+),\\s*(\\d+),\\s*([\\d.]+)\\)")
    val matchResult = regex.find(rgba)
    return if (matchResult != null) {
        val (r, g, b, a) = matchResult.destructured
        Color(r.toInt(), g.toInt(), b.toInt(), (a.toFloat() * 255).toInt())
    } else {
        Color.Transparent // Or throw an error, depending on your error handling
    }
}

// Helper function to parse hex color string
fun parseHexColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}

// Helper function to parse padding string "top,bottom,left,right"
fun parsePadding(padding: String): PaddingValues {
    val parts = padding.split(",").map { it.trim().toInt().dp }
    return when (parts.size) {
        1 -> PaddingValues(all = parts[0])
        2 -> PaddingValues(horizontal = parts[1], vertical = parts[0])
        4 -> PaddingValues(start = parts[3], top = parts[0], end = parts[1], bottom = parts[2]) // Assuming "top,right,bottom,left" or similar
        else -> PaddingValues(0.dp) // Default or error
    }
}

// Another helper to parse "vertical,horizontal" for consistency
fun parsePaddingHorizontalVertical(padding: String): PaddingValues {
    val parts = padding.split(",").map { it.trim().toInt().dp }
    return if (parts.size == 2) {
        PaddingValues(horizontal = parts[1], vertical = parts[0])
    } else {
        PaddingValues(0.dp)
    }
}

fun String.parseHexColor1(): Color = Color(android.graphics.Color.parseColor(this))

// Helper function to parse linear-gradient string to Brush
fun parseLinearGradient(gradientString: String): Brush {
    // Example: "linear-gradient(135deg, #04C7D0, #03A2AA)"
    val regex = "linear-gradient\\((\\d+)deg,\\s*([^,]+),\\s*([^)]+)\\)".toRegex()
    val matchResult = regex.find(gradientString)

    return if (matchResult != null && matchResult.groupValues.size == 4) {
        // angle (not directly used for simple linear gradient but can be if needed for start/end points)
        // val angle = matchResult.groupValues[1].toFloat()
        val color1 = matchResult.groupValues[2].trim().parseHexColor1()
        val color2 = matchResult.groupValues[3].trim().parseHexColor1()

        // For a simple two-color linear gradient, we'll just use top-start to bottom-end
        // If the angle needs to be precise, more complex calculation of start/end offset is needed.
        Brush.linearGradient(
            colors = listOf(color1, color2),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    } else {
        // Fallback to a solid color or default gradient if parsing fails
        Brush.linearGradient(
            colors = listOf(Color(0xFF04C7D0), Color(0xFF03A2AA)),
            start = Offset.Zero,
            end = Offset.Infinite
        )
    }
}
