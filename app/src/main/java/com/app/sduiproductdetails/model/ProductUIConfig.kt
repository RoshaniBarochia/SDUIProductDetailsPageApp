package com.app.sduiproductdetails.model

import com.google.gson.annotations.SerializedName


data class ProductUIConfig(
    val page: PageConfig,
    val product: ProductConfig,
    @SerializedName("ui_config")
    val uiConfig: UiConfig,
)

data class PageConfig(
    val id: String,
    val title: String,
    val theme: ThemeConfig,
    val components: List<UIComponent>,
)

data class ThemeConfig(
    val primary: String,
    val secondary: String,
    val success: String,
    val error: String,
    val warning: String,
    val background: String,
    val surface: String,
    val typography: Typography,
)

data class Typography(
    val headlineLarge: HeadlineLarge,
    val headlineMedium: HeadlineMedium,
    val bodyLarge: BodyLarge,
    val bodyMedium: BodyMedium,
    val bodySmall: BodySmall,
)

data class HeadlineLarge(
    val fontFamily: String,
    val fontSize: Long,
    val fontWeight: String,
)

data class HeadlineMedium(
    val fontFamily: String,
    val fontSize: Long,
    val fontWeight: String,
)

data class BodyLarge(
    val fontFamily: String,
    val fontSize: Long,
    val fontWeight: String,
)

data class BodyMedium(
    val fontFamily: String,
    val fontSize: Long,
    val fontWeight: String,
)

data class BodySmall(
    val fontFamily: String,
    val fontSize: Long,
    val fontWeight: String,
)

data class UIComponent(
    val type: String,
    val id: String,
    val props: Props,
)

data class Props(
    val title: String?,
    val showBackButton: Boolean?,
    val showSearchButton: Boolean?,
    val style: Style,
    val images: List<Image>?,
    val category: Category?,
    val brand: Brand?,
    val stock: Stock?,
    val price: Price?,
    val productCodes: ProductCodes?,
    val rating: Double?,
    val maxRating: Long?,
    val reviewCount: Long?,
    val showViewAll: Boolean?,
    val sizes: List<Size>?,
    val endTime: String?,
    val icon: String?,
    val details: List<Detail>?,
    val features: List<Feature>?,
    val buttons: List<Button>?,
    val layout: String?,
    val spacing: Long?,
)

data class Style(
    val backgroundColor: String?,
    val titleColor: String?,
    val iconColor: String?,
    val height: Long?,
    val cornerRadius: Long?,
    val showIndicators: Boolean?,
    val showDiscountBadge: Boolean?,
    val showShareButton: Boolean?,
    val padding: Long?,
    val spacing: Long?,
    val starColor: String?,
    val textColor: String?,
    val selectedStyle: SelectedStyle?,
    val background: String?,
    val timerBackground: String?,
    val timerTextColor: String?,
    val itemSpacing: Long?,
    val itemPadding: Long?,
    val itemBackground: String?,
    val itemCornerRadius: Long?,
    val layout: String?,
    val columns: Long?,
    val position: String?,
    val bottom: Long?,
    val borderTop: String?,
    val shadow: String?,
)

data class SelectedStyle(
    val backgroundColor: String,
    val borderColor: String,
    val textColor: String,
)

data class Image(
    val url: String,
    val thumbnail: String,
    val alt: String,
)

data class Category(
    val name: String,
    val style: Style2,
)

data class Style2(
    val backgroundColor: String,
    val textColor: String,
    val cornerRadius: Long,
    val padding: String,
)

data class Brand(
    val name: String,
    val style: Style3,
)

data class Style3(
    val backgroundColor: String,
    val textColor: String,
    val cornerRadius: Long,
    val padding: String,
)

data class Stock(
    val status: String,
    val quantity: Long,
    val unit: String,
    val style: Style4,
)

data class Style4(
    val backgroundColor: String,
    val textColor: String,
    val cornerRadius: Long,
    val padding: String,
)

data class Price(
    val current: Long,
    val original: Long,
    val currency: String,
    val savings: Long,
    val discountPercentage: Long,
)

data class ProductCodes(
    val productCode: String,
    val sku: String,
)

data class Size(
    val id: String,
    val label: String,
    val available: Boolean,
    val selected: Boolean,
)

data class Detail(
    val icon: String,
    val label: String,
    val value: String,
    val iconColor: String,
)

data class Feature(
    val icon: String,
    val title: String,
    val iconColor: String,
)

data class Button(
    val id: String,
    val type: String,
    val icon: String,
    val action: String,
    val style: Style5,
    val text: String?,
)

data class Style5(
    val backgroundColor: String,
    val iconColor: String?,
    val size: Long?,
    val cornerRadius: Long,
    val textColor: String?,
    val flex: Long?,
)

data class ProductConfig(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Price2,
    val images: List<String>,
    val thumbnails: List<String>,
    val description: String,
    val specifications: Specifications,
    val features: List<String>,
    val variants: Variants,
    val stock: Stock2,
    val rating: Rating,
    val metadata: Metadata,
    val seo: Seo,
    val availability: Availability,
)

data class Price2(
    val current: Double,
    val original: Double,
    val currency: String,
    val currencySymbol: String,
)

data class Specifications(
    val material: String,
    val gender: String,
    val condition: String,
    val type: String,
    val packaging: String,
    val quantity: String,
    val size: String,
)

data class Variants(
    val sizes: List<String>,
)

data class Stock2(
    val status: String,
    val quantity: Long,
    val lowStockThreshold: Long,
)

data class Rating(
    val average: Double,
    val count: Long,
    val distribution: Distribution,
)

data class Distribution(
    @SerializedName("5")
    val n5: Long,
    @SerializedName("4")
    val n4: Long,
    @SerializedName("3")
    val n3: Long,
    @SerializedName("2")
    val n2: Long,
    @SerializedName("1")
    val n1: Long,
)

data class Metadata(
    val productCode: String,
    val sku: String,
    val weight: String,
    val dimensions: String,
    val manufacturer: String,
    val countryOfOrigin: String,
)

data class Seo(
    val title: String,
    val description: String,
    val keywords: List<String>,
)

data class Availability(
    val status: String,
    val estimatedDelivery: String,
    val regions: List<String>,
)

data class UiConfig(
    val animations: Animations,
    val interactions: Interactions,
    val accessibility: Accessibility,
    val layout: Layout,
)

data class Animations(
    val enabled: Boolean,
    val duration: Long,
    val easing: String,
)

data class Interactions(
    val hapticFeedback: Boolean,
    val soundEffects: Boolean,
)

data class Accessibility(
    val highContrast: Boolean,
    val fontSize: String,
    val screenReader: Boolean,
)

data class Layout(
    val maxWidth: Long,
    val padding: Long,
    val spacing: Long,
)




