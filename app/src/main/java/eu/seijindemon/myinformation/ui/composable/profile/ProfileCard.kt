package eu.seijindemon.myinformation.ui.composable.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    user: User,
    selectedField: MutableState<Boolean>,
    selectedFieldKey: MutableState<String>,
    selectedFieldValue: MutableState<String>
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                contentPadding = PaddingValues(all = 5.dp)
            ) {
                items(user.keysValues!!) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                            .selectable(
                                selected = selectedFieldKey.value == item.key,
                                onClick = {
                                    selectedField.value = true
                                    selectedFieldKey.value = item.key
                                    selectedFieldValue.value = item.value
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val color = if (selectedField.value && item.key == selectedFieldKey.value) { Color.Red } else { Color.Unspecified }
                        AutoSizeText(
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1f),
                            text = item.key,
                            maxFontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1,
                            color = color
                        )
                        AutoSizeText(
                            modifier = Modifier
                                .padding(start = 5.dp)
                                .weight(1f),
                            text = item.value,
                            maxFontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            color = color
                        )
                    }
                    Divider(
                        thickness = 2.dp
                    )
                }
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
        val selectedField = remember { mutableStateOf(false) }
        val selectedFieldKey = remember { mutableStateOf("") }
        val selectedFieldValue = remember { mutableStateOf("") }
        ProfileCard(
            user = user,
            selectedField = selectedField,
            selectedFieldKey = selectedFieldKey,
            selectedFieldValue = selectedFieldValue
        )
    }
}