package ca.uqac.tp_informatique_mobile_8inf257.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.uqac.tp_informatique_mobile_8inf257.presentation.CheatSheetVM
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun CheatSheetItem(cheatSheet: CheatSheetVM, onClick: () -> Unit = {}) {
    val painter = rememberAsyncImagePainter(model = cheatSheet.document)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = cheatSheet.title,
                style = TextStyle(fontSize = 20.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = cheatSheet.description,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
