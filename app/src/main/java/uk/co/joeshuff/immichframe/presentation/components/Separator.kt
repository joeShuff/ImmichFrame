package uk.co.joeshuff.immichframe.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Separator(modifier: Modifier = Modifier, color: Color = Color.Black) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(3.dp)) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSeparator() {
    Column(modifier = Modifier.height(60.dp)) {
        Spacer(Modifier.height(10.dp))
        Separator()
        Spacer(Modifier.height(10.dp))
    }
}