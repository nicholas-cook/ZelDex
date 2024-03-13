package com.nickcook.zeldex.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ZelDexPrimary,
    onPrimary = ZelDexOnPrimary,
    primaryContainer = ZelDexPrimaryContainer,
    onPrimaryContainer = ZelDexOnPrimaryContainer,
    secondary = ZelDexSecondary,
    onSecondary = ZelDexOnSecondary,
    secondaryContainer = ZelDexSecondaryContainer,
    onSecondaryContainer = ZelDexOnSecondaryContainer,
    tertiary = ZelDexTertiary,
    onTertiary = ZelDexOnTertiary,
    tertiaryContainer = ZelDexOnTertiaryContainer,
    onTertiaryContainer = ZelDexOnTertiaryContainer,
    error = ZelDexError,
    errorContainer = ZelDexErrorContainer,
    onError = ZelDexOnError,
    onErrorContainer = ZelDexOnErrorContainer,
    background = ZelDexBackground,
    onBackground = ZelDexOnBackground,
    surface = ZelDexSurface,
    onSurface = ZelDexOnSurface,
    surfaceVariant = ZelDexSurfaceVariant,
    onSurfaceVariant = ZelDexOnSurfaceVariant,
    outline = ZelDexOutline,
    inverseOnSurface = ZelDexInverseOnSurface,
    inverseSurface = ZelDexInverseSurface,
    inversePrimary = ZelDexInversePrimary,
    surfaceTint = ZelDexSurfaceTint,
    outlineVariant = ZelDexOutlineVariant,
    scrim = ZelDexScrim
)


private val DarkColorScheme = darkColorScheme(
    primary = ZelDexPrimaryDark,
    onPrimary = ZelDexOnPrimaryDark,
    primaryContainer = ZelDexPrimaryContainerDark,
    onPrimaryContainer = ZelDexOnPrimaryContainerDark,
    secondary = ZelDexSecondaryDark,
    onSecondary = ZelDexOnSecondaryDark,
    secondaryContainer = ZelDexSecondaryContainerDark,
    onSecondaryContainer = ZelDexOnSecondaryContainerDark,
    tertiary = ZelDexTertiaryDark,
    onTertiary = ZelDexOnTertiaryDark,
    tertiaryContainer = ZelDexOnTertiaryContainerDark,
    onTertiaryContainer = ZelDexOnTertiaryContainerDark,
    error = ZelDexErrorDark,
    errorContainer = ZelDexErrorContainerDark,
    onError = ZelDexOnErrorDark,
    onErrorContainer = ZelDexOnErrorContainerDark,
    background = ZelDexBackgroundDark,
    onBackground = ZelDexOnBackgroundDark,
    surface = ZelDexSurfaceDark,
    onSurface = ZelDexOnSurfaceDark,
    surfaceVariant = ZelDexSurfaceVariantDark,
    onSurfaceVariant = ZelDexOnSurfaceVariantDark,
    outline = ZelDexOutlineDark,
    inverseOnSurface = ZelDexInverseOnSurfaceDark,
    inverseSurface = ZelDexInverseSurfaceDark,
    inversePrimary = ZelDexInversePrimaryDark,
    surfaceTint = ZelDexSurfaceTintDark,
    outlineVariant = ZelDexOutlineVariantDark,
    scrim = ZelDexScrimDark
)

@Composable
fun ZelDexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}