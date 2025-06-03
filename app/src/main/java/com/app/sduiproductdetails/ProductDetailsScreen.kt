package com.app.sduiproductdetails

import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.sduiproductdetails.model.Size
import com.app.sduiproductdetails.model.Style
import com.app.sduiproductdetails.model.UIComponent
import com.app.sduiproductdetails.utils.parseHexColor
import com.app.sduiproductdetails.utils.parseHexColor1
import com.app.sduiproductdetails.utils.parseLinearGradient
import com.app.sduiproductdetails.utils.parsePaddingHorizontalVertical
import com.app.sduiproductdetails.utils.parseRgba
import com.app.sduiproductdetails.utils.toColorScheme
import com.app.sduiproductdetails.utils.toTypography
import com.app.sduiproductdetails.viewmodel.ProductScrViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.max


@Composable
fun ProductDetailsScreen(
    navController: NavHostController,
    viewModel: ProductScrViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val theme = uiState.theme

    if (theme == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val colorTheme = theme.toColorScheme()
    val typography = theme.toTypography()

    MaterialTheme(
        colorScheme = colorTheme,
        typography = typography
    ) {
        // The entire screen content is now within the LazyColumn
        Column (
            modifier = Modifier
                .fillMaxSize() // LazyColumn fills the entire screen
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.isLoading) {
                    // Center the progress indicator within its item slot
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Fills the available space in LazyColumn
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

            } else if (uiState.error != null) {
                    // Center the error text within its item slot
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), // Fills the available space in LazyColumn
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                    }

            } else {
                // Iterate through all components and render them as individual items
                //header
                val activity = (LocalActivity.current as? Activity)

                val headerComponent = uiState.page?.components?.find { it.type == "header" }
                if (headerComponent != null) {
                    ProductHeaderBar(component = headerComponent, onBack = {
                        Log.d("TAG", "ProductDetailsScreen: header")
                        activity?.finish()

                    })
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    uiState.page?.components?.forEach { component ->
                        // IMPORTANT: Ensure 'component' has a unique 'id' for the key.
                        when (component.type) {

                            "image_gallery" -> ProductImageGallery(component)
                            "product_info" -> ProductInfo(component)
                            "ratings" -> RatingSection(component)
                            "variant_selector" -> SizeSelector(
                                component.props.sizes,
                                component.props.style
                            )

                            "offer_timer" -> OfferTimer(component)
                            "product_details" -> ProductDetailSpecs(component)
                            "features_list" -> ProductFeatures(component)
                        }
                    }
                }
                //footer
                val footerComp = uiState.page?.components?.find { it.type == "action_buttons" }
                if (footerComp != null) {
                    BottomActionBar(footerComp)
                }
           }
        }
    }
}

// Bottom Bar Composable
@Composable
fun BottomActionBar(component: UIComponent?) {
    if (component?.type != "action_buttons") return

    val props = component.props
    val layoutStyle = props.style
    val padding = layoutStyle.padding?.toInt() ?: 16
    val backgroundColor = layoutStyle.backgroundColor ?: "#FFFFFF"
    val borderTop = layoutStyle.borderTop // "1px solid #E8E8EC"
    val spacing = props.spacing?.toInt() ?: 12
    var isFavorite by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // prevent infinite height
            .background(Color(android.graphics.Color.parseColor(backgroundColor)))
            .then(
                if (borderTop != null) {
                    val (widthStr, type, colorStr) = borderTop.split(" ")
                    val borderWidth = widthStr.replace("px", "").toFloat().dp
                    Modifier.border(width = borderWidth, color = Color(android.graphics.Color.parseColor(colorStr)))
                } else Modifier
            )
            .padding(padding.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            props.buttons?.forEach { button ->
                when (button.type) {
                    "icon" -> {
                        val iconButtonStyle = button.style
                        val iconBgColor = iconButtonStyle.backgroundColor ?: "#E8E8EC"
                        val iconColor = iconButtonStyle.iconColor ?: "#6E6E7A"
                        val iconSize = iconButtonStyle.size?.toInt() ?: 48
                        val cornerRadius = iconButtonStyle.cornerRadius?.toInt() ?: 24 // Used for CircleShape below

                        IconButton(
                            onClick = {
                                if (button.id == "favorite") {
                                    isFavorite = !isFavorite // Toggle favorite state
                                    // You can also trigger an action here, e.g., save favorite status
                                }
                            },
                            modifier = Modifier
                                .size(iconSize.dp)
                                .background(Color(android.graphics.Color.parseColor(iconBgColor)), CircleShape) // Use CircleShape for circular buttons
                        ) {
                            val iconToDisplay = if (button.id == "favorite" && isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                mapActionButtonIcon(button.icon)
                            }
                            Icon(
                                imageVector = iconToDisplay,
                                contentDescription = button.id,
                                tint = Color(android.graphics.Color.parseColor(iconColor)),
                                modifier = Modifier.size(24.dp) // Icon size inside the button
                            )
                        }
                    }
                    "primary" -> {
                        val primaryButtonStyle = button.style
                        val primaryBgColor = primaryButtonStyle?.backgroundColor ?: "#04C7D0"
                        val primaryTextColor = primaryButtonStyle?.textColor ?: "#FFFFFF"
                        val primaryCornerRadius = primaryButtonStyle?.cornerRadius?.toInt() ?: 24
                        val flexWeight = primaryButtonStyle?.flex?.toFloat() ?: 1f

                        Button(
                            onClick = { /* add to cart */ },
                            modifier = Modifier
                                .weight(flexWeight)
                                .height(48.dp), // Fixed height for consistency
                            shape = RoundedCornerShape(primaryCornerRadius.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(android.graphics.Color.parseColor(primaryBgColor)),
                                contentColor = Color(android.graphics.Color.parseColor(primaryTextColor))
                            )
                        ) {
                            Icon(
                                imageVector = mapActionButtonIcon(button.icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp) // Adjust icon size within the button
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(button.text ?: "")
                        }
                    }
                }
            }
        }
    }
}

// Helper function to map icon names to ImageVector
fun mapActionButtonIcon(name: String?): ImageVector {
    return when (name) {
        "heart" -> Icons.Outlined.FavoriteBorder // Using Outlined for consistency with image
        "shopping-cart" -> Icons.Default.ShoppingCart
        "share-2" -> Icons.Outlined.Share // Using Outlined for consistency with image
        else -> Icons.Default.Favorite // Default to something if no match
    }
}

@Composable
fun ProductHeaderBar(component: UIComponent, onBack: () -> Unit = {}, onSearch: () -> Unit = {}) {
    val props = component.props
    val title = props.title ?: ""
    val showBack = props.showBackButton ?: true
    val showSearch = props.showSearchButton ?: true
    val bgColor =
        Color(android.graphics.Color.parseColor(props.style.backgroundColor ?: "#FFFFFF"))
    val titleColor = Color(android.graphics.Color.parseColor(props.style.titleColor ?: "#000000"))
    val iconColor = Color(android.graphics.Color.parseColor(props.style.iconColor ?: "#000000"))
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight() // prevent infinite height
    ) {
        Spacer(modifier = Modifier.height(10.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgColor)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            // Title in center
            Text(
                text = title,
                color = titleColor,
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,

                )

            // Icons on left and right using Row overlay
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBack) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = iconColor)
                    }
                } else {
                    Spacer(modifier = Modifier.size(18.dp)) // Maintain space
                }

                if (showSearch) {
                    IconButton(onClick = onSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = iconColor)
                    }
                } else {
                    Spacer(modifier = Modifier.size(18.dp))
                }
            }
        }

    }
}

@Composable
fun ProductDetailSpecs(component: UIComponent) {
    val details = component.props.details ?: return
    val title = component.props.title ?: "Product Details"
    val style = component.props.style

    val itemSpacing = (style.itemSpacing as? Number)?.toInt() ?: 12
    val itemPadding = (style.itemPadding as? Number)?.toInt() ?: 12
    val itemBgColor = (style.itemBackground as? String) ?: "#F8F8F9"
    val cornerRadius = (style.itemCornerRadius as? Number)?.toInt() ?: 8
    val contentPadding = (style.padding as? Number)?.toInt() ?: 16 // Use the padding from style

    Column(modifier = Modifier.padding(contentPadding.dp).wrapContentHeight() // prevent infinite height
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))

        details.forEach { detail ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = itemSpacing.dp)
                    .background(
                        color = Color(android.graphics.Color.parseColor(itemBgColor)),
                        shape = RoundedCornerShape(cornerRadius.dp)
                    )
                    .padding(itemPadding.dp)
            ) {
                // You can replace this with a custom icon mapping based on `detail.icon`
                Icon(
                    imageVector = mapIcon(detail.icon),
                    contentDescription = detail.label,
                    tint = Color(android.graphics.Color.parseColor(detail.iconColor ?: "#000000")),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${detail.label}: ",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = detail.value ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun mapIcon(name: String): ImageVector {
    return when (name) {
        "package" -> Icons.Filled.Inventory // Renamed from "package" in the original JSON to match your mapping
        "users" -> Icons.Filled.People
        "recycle" -> Icons.Filled.Loop
        "tag" -> Icons.Filled.Label
        "box" -> Icons.Filled.AllInbox
        else -> Icons.Default.Info
    }
}

@Composable
fun ProductFeatures(component: UIComponent) {
    val features = component.props.features ?: return
    val style = component.props.style
    val columns = style.columns?.toInt() ?: 2
    val spacing = style.spacing?.toInt() ?: 12
    val itemPadding = style.itemPadding?.toInt() ?: 12
    val itemBgColor = style.itemBackground ?: "#F8F8F9"
    val cornerRadius = style.itemCornerRadius?.toInt() ?: 8

    Column(modifier = Modifier.padding(16.dp).wrapContentHeight() // prevent infinite height
    ) {
        Text(
            text = component.props.title ?: "Key Features",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold // Added font weight as in the image
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp), // or requiredHeight(300.dp),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp),
            verticalArrangement = Arrangement.spacedBy(spacing.dp)
        ) {
            items(features.size) { index ->
                val feature = features[index]
                Row(
                    modifier = Modifier
                        .background(
                            Color(android.graphics.Color.parseColor(itemBgColor)),
                            RoundedCornerShape(cornerRadius.dp)
                        )
                        .padding(itemPadding.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // Align content to the start
                ) {
                    Icon(
                        imageVector = mapFeatureIcon(feature.icon),
                        contentDescription = feature.title,
                        tint = Color(android.graphics.Color.parseColor(feature.iconColor ?: "#000000")),
                        modifier = Modifier.size(20.dp) // Adjust icon size as needed
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                    Text(
                        text = feature.title ?: "",
                        style = MaterialTheme.typography.bodyMedium // Adjust typography if needed
                    )
                }
            }
        }
    }
}

// Helper function to map icon names to ImageVector
fun mapFeatureIcon(name: String): ImageVector {
    return when (name) {
        "droplets" -> Icons.Default.WaterDrop
        "wind" -> Icons.Default.Air
        "heart" -> Icons.Default.FavoriteBorder
        "shield" -> Icons.Default.VerifiedUser
        else -> Icons.Default.Info
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImageGallery(component: UIComponent) {
    val images = component.props?.images ?: emptyList()
    if (images.isEmpty()) return
    val coroutineScope = rememberCoroutineScope() // THIS IS THE KEY CHANGE

    // Pager state for the main image gallery
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })

    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight() // prevent infinite height
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(Color(0xFFE0E0E0)) // Light gray background from image
        ) {
            // Main image pager
            HorizontalPager(
                //count = images.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            ) { page ->
                AsyncImage(
                    model = images[page].url,
                    contentDescription = images[page].alt,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(0.dp))
                )
            }

            // Discount Badge
            if (true) {
                Card(
                    shape = RoundedCornerShape(
                        bottomEnd = 8.dp,
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomStart = 8.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error), // Use error color for red badge
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)

                ) {
                    Text(
                        text = "-17%", // Replace with actual discount value from props
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }


            // Share Icon
            IconButton(
                onClick = { /* Handle share action */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f)) // Semi-transparent white background for icon
                    .size(40.dp) // Adjust size as needed
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.Black
                )
            }

            // Pager dots indicator
            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(start = 12.dp))

        // Horizontal thumbnails
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()

                .background(Color(0xFFE0E0E0))
        ) {
            itemsIndexed(images) { index, image -> // Use itemsIndexed to get the index
                AsyncImage(
                    model = image.thumbnail ?: image.url,
                    contentDescription = image.alt,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.2f
                            )
                            else (Color(0xFFE0E0E0))
                        )
                        .clickable {
                            // Update pager state when thumbnail is clicked
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                )
            }
        }
    }
}


@Composable
fun ProductInfo(component: UIComponent) {
    val props = component.props ?: return

    val componentPadding = props.style.padding?.toInt()?.dp ?: 16.dp
    val componentSpacing = props.style.spacing?.toInt()?.dp ?: 12.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // prevent infinite height
            .padding(componentPadding)
    ) {
        // Top Row: Category, Brand, In Stock
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                props.category?.let { category ->
                    val bgColor =
                        category.style.backgroundColor.let { parseRgba(it) } ?: Color.Transparent
                    val textColor =
                        category.style.textColor.let { parseHexColor(it) } ?: Color.Black
                    val cornerRadius = category.style.cornerRadius.toInt().dp ?: 0.dp
                    val padding =
                        category.style.padding.let { parsePaddingHorizontalVertical(it) }
                            ?: PaddingValues(4.dp, 8.dp) // Default if parsing fails

                    Text(
                        text = category.name,
                        color = textColor,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        modifier = Modifier
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(bgColor)
                            .padding(padding)
                    )
                }

                props.brand?.let { brand ->
                    val bgColor =
                        brand.style.backgroundColor.let { parseRgba(it) } ?: Color.Transparent
                    val textColor = brand.style.textColor.let { parseHexColor(it) } ?: Color.Black
                    val cornerRadius = brand.style.cornerRadius.toInt().dp ?: 0.dp
                    val padding = brand.style.padding.let { parsePaddingHorizontalVertical(it) }
                        ?: PaddingValues(4.dp, 8.dp)

                    Text(
                        text = brand.name,
                        color = textColor,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        modifier = Modifier
                            .clip(RoundedCornerShape(cornerRadius))
                            .background(bgColor)
                            .padding(padding)
                    )
                }
            }

            props.stock?.let { stock ->
                val bgColor =
                    stock.style.backgroundColor.let { parseRgba(it) } ?: Color.Transparent
                val textColor = stock.style.textColor.let { parseHexColor(it) } ?: Color.Black
                val cornerRadius = stock.style.cornerRadius.toInt().dp ?: 0.dp
                val padding = stock.style.padding.let { parsePaddingHorizontalVertical(it) }
                    ?: PaddingValues(4.dp, 8.dp)

                Text(
                    text = "${stock.status}\n${stock.quantity} ${stock.unit}",
                    color = textColor,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    modifier = Modifier
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(bgColor)
                        .padding(padding),
                    // Align text to end if needed for multiline, or use Column inside to align
                    // If you want "In Stock" centered above "24 units", use Column
                    textAlign = androidx.compose.ui.text.style.TextAlign.End // For right alignment
                )
            }
        }

        Spacer(modifier = Modifier.height(componentSpacing))

        // Product Title and Prices
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = props.title ?: "",
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 38.sp,
                modifier = Modifier.weight(1f) // Take remaining space
            )

            props.price?.let { price ->
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${price.currency}${price.current}",
                        style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onSurface) // Changed to onSurface for contrast
                    )
                    if (price.original > price.current) { // Only show original if there's a discount
                        Text(
                            text = "${price.currency}${price.original}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        )
                        Text(
                            text = "Save ${price.currency}${price.savings}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant) // Use a subtle color
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(componentSpacing))

        // Product Codes (Product Code and SKU) - Use a Card for the background
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp), // Adjust corner radius as per image
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)) // Light grey background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp), // Inner padding for the card content
                horizontalArrangement = Arrangement.spacedBy(80.dp)
            ) {
                props.productCodes?.let { codes ->
                    Column {
                        Text(
                            text = "Product Code:",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                        Text(
                            text = codes.productCode,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Column {
                        Text(
                            text = "SKU:",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                            modifier = Modifier.align(Alignment.Start) // Align SKU text to end
                        )
                        Text(
                            text = codes.sku,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.align(Alignment.Start) // Align SKU value to end
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TagSection(component: UIComponent) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        component.props?.details?.forEach { tag ->
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
            ) {
                Text(
                    text = tag.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Composable
fun RatingSection(component: UIComponent) {
    // Safely cast props to RatingsProps
    val props = component.props ?: return

    val starColor =
        props.style.starColor?.let { parseHexColor(it) } ?: Color(0xFFFFC233) // Default to yellow
    val textColor = props.style.textColor?.let { parseHexColor(it) }
        ?: Color(0xFF4A4A54) // Default to dark gray
    val sectionPadding = props.style.padding?.toInt()?.dp ?: 8.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // prevent infinite height
            .padding(sectionPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Distribute items horizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Render stars
            val fullStars = props.rating?.toInt()!!
            val hasHalfStar = props.rating!! - fullStars >= 0.5f

            repeat(fullStars) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null, // Content description for decorative icons can be null
                    tint = starColor,
                    modifier = Modifier.size(20.dp) // Adjust star size as needed
                )
            }
            if (hasHalfStar) {
                Icon(
                    imageVector = Icons.Filled.StarHalf,
                    contentDescription = null,
                    tint = starColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            // Fill remaining with outlined stars up to maxRating
            val emptyStars = props.maxRating!! - fullStars - (if (hasHalfStar) 1 else 0)
            repeat(emptyStars.toInt()) {
                Icon(
                    imageVector = Icons.Filled.StarOutline,
                    contentDescription = null,
                    tint = starColor, // Use starColor for outline too, or a lighter version
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Rating value
            Text(
                text = "${props.rating}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight // Keep original fontWeight
                )
            )

            Spacer(modifier = Modifier.width(4.dp)) // Smaller spacer

            // Review count
            Text(
                text = "(${props.reviewCount} Ratings)",
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
        }

        // View All button
        if (props.showViewAll == true) {
            TextButton(onClick = { /* Handle view all ratings click */ }) {
                Text(
                    text = "View All",
                    color = MaterialTheme.colorScheme.primary, // Use primary color for "View All"
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize, // Keep consistent font size
                    // You might need a trailing icon for the arrow
                    modifier = Modifier.padding(end = 4.dp)
                )
                // Add the arrow icon
                Icon(
                    imageVector = Icons.Default.ChevronRight, // Or a custom arrow icon
                    contentDescription = "View all reviews",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun SizeSelector(
    sizes: List<Size>?,
    style: Style? // Pass the style object from JSON
) {
    if (sizes.isNullOrEmpty()) return

    // Find the initially selected size, or default to the first available one
    var selectedSize by remember {
        mutableStateOf(sizes.firstOrNull { it.selected } ?: sizes.first())
    }

    val itemPadding = style?.padding?.toInt()?.dp ?: 16.dp
    val itemSpacing = style?.spacing?.toInt()?.dp ?: 8.dp

    Column(modifier = Modifier.padding(itemPadding).wrapContentHeight() // prevent infinite height
    ) {
        Text(
            "Size",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        ) // Changed to "Size" as per image
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(horizontalArrangement = Arrangement.spacedBy(itemSpacing)) {
            sizes.forEach { size ->
                val isSelected = size == selectedSize

                val backgroundColor = if (isSelected && style != null) {
                    parseRgba(style.selectedStyle!!.backgroundColor)
                } else {
                    Color.White // Default background for unselected
                }

                val borderColor = if (isSelected && style != null) {
                    Color(android.graphics.Color.parseColor(style.selectedStyle!!.borderColor))
                } else {
                    Color.LightGray // Default border for unselected
                }

                val textColor = if (isSelected && style != null) {
                    Color(android.graphics.Color.parseColor(style.selectedStyle!!.textColor))
                } else {
                    Color.Black // Default text color for unselected
                }

                Surface(
                    color = backgroundColor,
                    shape = MaterialTheme.shapes.small,
                    border = BorderStroke(
                        1.dp,
                        if (isSelected) borderColor else Color.LightGray
                    ), // Add border, make it subtle for unselected
                    modifier = Modifier
                        .clickable { selectedSize = size }
                ) {
                    Text(
                        size.label,
                        color = textColor,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ) // Padding directly on Text for content padding
                    )
                }
            }
        }
    }
}

@Composable
fun OfferTimer(component: UIComponent) {
    val props = component.props
    val formatter = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    val endTimeCalendar = remember(props.endTime) {
        Calendar.getInstance().apply {
            try {
                val parsedTime = formatter.parse(props.endTime!!)
                if (parsedTime != null) {
                    val tempCalendar = Calendar.getInstance().apply { time = parsedTime }

                    // Set only the time components (hour, minute, second)
                    set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY))
                    set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE))
                    set(Calendar.SECOND, tempCalendar.get(Calendar.SECOND))
                    set(Calendar.MILLISECOND, 0) // Ensure milliseconds are zeroed out

                    val nowForComparison = Calendar.getInstance()

                    // If the target time (e.g., 01:00:00) is in the past for today,
                    // assume it means 01:00:00 tomorrow.
                    if (this.before(nowForComparison)) {
                        add(Calendar.DAY_OF_MONTH, 1)
                    }
                } else {
                    Log.e("OfferTimer", "Error parsing endTime: parsedTime is null for ${props.endTime}")
                }
            } catch (e: ParseException) {
                Log.e("OfferTimer", "Error parsing endTime: ${props.endTime} - ${e.message}")
            } catch (e: Exception) {
                Log.e("OfferTimer", "Unexpected error parsing endTime: ${props.endTime} - ${e.message}")
            }
        }
    }

    var remainingTimeMs by remember {
        // Initial calculation uses the absolute current time when the composable is first displayed
        mutableLongStateOf(
            max(0L, endTimeCalendar.timeInMillis - Calendar.getInstance().timeInMillis)
        )
    }
    var formattedTime by remember {
        mutableStateOf(String.format("%02d:%02d:%02d", 0, 0, 0))
    }

    LaunchedEffect(Unit) {
        while (remainingTimeMs > 0) {
            val now = Calendar.getInstance().timeInMillis
            remainingTimeMs = max(0L, endTimeCalendar.timeInMillis - now)
            if (remainingTimeMs > 0) { // Only delay if time is still remaining
                delay(1000) // Delay for 1 second
                val hours = (remainingTimeMs / (1000 * 60 * 60)).toInt()
                val minutes = ((remainingTimeMs / (1000 * 60)) % 60).toInt()
                val seconds = ((remainingTimeMs / 1000) % 60).toInt()
                formattedTime =String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }
        }
    }




    // Remember parsed style properties to avoid recalculation on every recomposition
    val backgroundColorBrush = remember(props.style.background) { parseLinearGradient(props.style.background!!) }
    val textColor = remember(props.style.textColor) { props.style.textColor?.parseHexColor1() }
    val timerBackgroundColor = remember(props.style.timerBackground) { props.style.timerBackground?.parseHexColor1() }
    val timerTextColor = remember(props.style.timerTextColor) { props.style.timerTextColor?.parseHexColor1() }
    val cornerRadius = remember(props.style.cornerRadius) { props.style.cornerRadius?.toFloat()?.dp }
    val contentPadding = remember(props.style.padding) { props.style.padding?.toFloat()?.dp }

    val icon: ImageVector = when (props.icon) {
        "zap" -> Icons.Default.FlashOn
        else -> Icons.Default.FlashOn // Default icon
    }

    // Outer Box with outer padding
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Outer padding as per image
        contentAlignment = Alignment.Center
    ) {
        // Inner Row with background, shape, and inner padding
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = backgroundColorBrush,
                    shape = RoundedCornerShape(cornerRadius!!)
                )
                .padding(contentPadding!!), // Inner padding from style prop
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Distribute space between items
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Offer Icon",
                    tint = textColor!!,
                    modifier = Modifier.size(20.dp) // Adjust icon size
                )
                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                Text(
                    text = props.title!!,
                    color = textColor,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge // Adjust text style
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Ends in: ",
                    color = textColor!!,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge // Adjust text style
                )
                Spacer(modifier = Modifier.width(4.dp))
                Surface(
                    shape = RoundedCornerShape(cornerRadius), // Use same corner radius as outer shape
                    color = timerBackgroundColor!!
                ) {
                    Text(
                        text = formattedTime,
                        color = timerTextColor!!,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge, // Adjust text style
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ) // Padding for timer text
                    )
                }
            }
        }
    }
}


