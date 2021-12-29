package eu.seijindemon.myinformation.ui.composable.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.seijindemon.myinformation.data.model.KeyValue
import eu.seijindemon.myinformation.data.model.User
import eu.seijindemon.myinformation.ui.composable.general.AutoSizeText
import eu.seijindemon.myinformation.ui.theme.MyInformationTheme

@Composable
fun ProfileCard(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 5.dp,
                end = 5.dp,
                bottom = 5.dp
            )
            .clip(RoundedCornerShape(8.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AutoSizeText(
            modifier = Modifier
                .padding(all = 10.dp),
            text = "${user.firstName} ${user.lastName}",
            maxFontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Divider(
            thickness = 5.dp
        )
        if (!user.keysValues.isNullOrEmpty()) {
            for (item: KeyValue in user.keysValues!!) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AutoSizeText(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1f),
                            text = item.key,
                            maxFontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                        AutoSizeText(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .weight(1f),
                            text = item.value,
                            maxFontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
                Divider(
                    thickness = 2.dp
                )
            }
        }

    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ProfileCardPreview() {
    MyInformationTheme {
        val user = User(1, "George", "Karanikolas", listOf(
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890"),
            KeyValue("AMKA","1234567890")
        ))
        ProfileCard(
            user = user
        )
    }
}