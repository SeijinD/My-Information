package eu.seijindemon.myinformation.ui.composable.error

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.seijindemon.myinformation.R
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText

@Composable
fun ErrorScreen(
    errorMessage: String?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AutoSizeText(
            text = errorMessage ?: stringResource(id = R.string.generic_error_message),
            modifier = Modifier.padding(16.dp),
            maxFontSize = 20.sp,
            style = MaterialTheme.typography.body1,
            color = Red
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(errorMessage = "Error Message!")
}