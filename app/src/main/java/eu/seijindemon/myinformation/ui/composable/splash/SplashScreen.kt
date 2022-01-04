package eu.seijindemon.myinformation.ui.composable.splash

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.seijindemon.myinformation.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    SplashContent(
        navController = navController
    )
}

@Composable
fun SplashContent(
    navController: NavController
) {
    val scale = remember { Animatable(0f) }
    
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
    }

    LaunchedEffect(key1 = true) {
        delay(4000L)
        navController.navigate("home")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash Image",
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale.value)
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(
        navController = navController
    )
}