package uk.co.joeshuff.immichframe.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import uk.co.joeshuff.immichframe.ui.ImmichFrameTextStyling.TextSize.LargeLineHeight
import uk.co.joeshuff.immichframe.ui.ImmichFrameTextStyling.TextSize.StandardLineHeight

object ImmichFrameTextStyling {

    object TextSize {
        val StandardLineHeight = 20.sp
        val LargeLineHeight = 25.sp
        val SmallerLineHeight = 12.sp
    }

    val Heading =
        TextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 20.sp,
            color = Color.Black,
            lineHeight = LargeLineHeight)
    val Large =
        TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = StandardLineHeight)
    val Bold =
        TextStyle(
            fontWeight = FontWeight.W700,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = StandardLineHeight)
    val Body =
        TextStyle(
            fontWeight = FontWeight.W300,
            fontSize = 14.sp,
            color = Color.Black,
            lineHeight = StandardLineHeight)
}