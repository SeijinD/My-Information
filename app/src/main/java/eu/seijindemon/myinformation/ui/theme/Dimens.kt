package eu.seijindemon.myinformation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    // Default Margin Values
    val margin_octuple_128dp: Dp = 128.dp,
    val margin_quadruple_64dp: Dp = 64.dp,
    val margin_custom_48dp: Dp = 48.dp,
    val margin_double_32dp: Dp = 32.dp,
    val margin_double_half_24dp: Dp = 24.dp,
    val margin_default_16dp: Dp = 16.dp,
    val margin_half_double_12dp: Dp = 12.dp,
    val margin_half_8dp: Dp = 8.dp,
    val margin_quarter_4dp: Dp = 4.dp,
    val margin_eighth_2dp: Dp = 2.dp,
    // Default Text Sizes
    val text_tiny_4sp: Dp = 4.dp,
    val text_micro_8sp: Dp = 8.dp,
    val text_mini_10sp: Dp = 10.dp,
    val text_small_12sp: Dp = 12.dp,
    val text_default_14sp: Dp = 14.dp,
    val text_normal_16sp: Dp = 16.dp,
    val text_medium_18sp: Dp = 18.dp,
    val text_large_22sp: Dp = 22.dp,
    val text_xlarge_26sp: Dp = 26.dp,
    val text_xxlarge_32sp: Dp = 32.dp,
    // Corners
    val radius_quadruple_40dp: Dp = 40.dp,
    val radius_double_20dp: Dp = 20.dp,
    val radius_default_10dp: Dp = 10.dp,
    val radius_half_5dp: Dp = 5.dp,
    val radius_custom_button_8dp: Dp = 8.dp,
    val radius_custom_button_2dp: Dp = 2.dp,
    // Vector Sizes
    val vector_small: Dp = 24.dp,
    val vector_medium: Dp = 32.dp,
    val vector_normal: Dp = 48.dp,
    val vector_large: Dp = 64.dp,
    // Elevation
    val elevation_4dp: Dp = 4.dp,
    val elevation_default_10dp: Dp = 10.dp,
)

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current