package dev.chinh.itcanclick.ui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val BackgroundDark = Color(0xFF26282B) // Main background
val SurfaceDark = Color(0xFF191A1C)    // Panels/cards/topbars
val PrimaryDark = Color(0xFF3C3F41)    // Subtle elevated components
val SecondaryDark = Color(0xFF4E5254)
val OnBackgroundDark = Color(0xFFE8E8E8)
val OnSurfaceDark = Color(0xFFD6D6D6)
val OnPrimaryDark = Color(0xFFE8E8E8)
val OnSecondaryDark = Color(0xFFD6D6D6)
val OutlineDark = Color(0xFF5A5D5F)
val SurfaceVariantDark = Color(0xFF2F3133)


val BackgroundLight = Color(0xFFF5F5F5) // Main background
val SurfaceLight = Color(0xFFFFFFFF)    // Cards/sheets
val PrimaryLight = Color(0xFFE6E6E6)    // Toolbar/buttons
val SecondaryLight = Color(0xFFD0D0D0)
val OnBackgroundLight = Color(0xFF2B2B2B)
val OnSurfaceLight = Color(0xFF2B2B2B)
val OnPrimaryLight = Color(0xFF2B2B2B)
val OnSecondaryLight = Color(0xFF2B2B2B)
val OutlineLight = Color(0xFFC2C2C2)
val SurfaceVariantLight = Color(0xFFEAEAEA)


val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,

    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,

    background = BackgroundDark,
    onBackground = OnBackgroundDark,

    surface = SurfaceDark,
    onSurface = OnSurfaceDark,

    outline = OutlineDark,
    surfaceVariant = SurfaceVariantDark,
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,

    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,

    background = BackgroundLight,
    onBackground = OnBackgroundLight,

    surface = SurfaceLight,
    onSurface = OnSurfaceLight,

    outline = OutlineLight,
    surfaceVariant = SurfaceVariantLight,
)