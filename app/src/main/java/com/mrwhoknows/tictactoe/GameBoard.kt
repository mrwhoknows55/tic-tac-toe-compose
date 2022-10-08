package com.mrwhoknows.tictactoe

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun GameBoard() {
    Canvas(
        modifier = Modifier
            .size(400.dp)
            .padding(16.dp)
            .background(Color.DarkGray)
            .aspectRatio(1f)
    ) {
        (1..2).forEach {
            drawVerticalLine(size.width * it / 3)
            drawHorizontalLine(size.height * it / 3)
        }
        drawDiagonalLineRightToLeft()
        drawDiagonalLineLeftToRight()
    }
}

fun DrawScope.drawVerticalLine(
    x: Float,
    color: Color = Color.White,
    strokeWidth: Float = 8f
) {
    drawLine(
        color = color, start = Offset(x, 0f), end = Offset(x, size.width),
        cap = StrokeCap.Butt, strokeWidth = strokeWidth
    )
}

fun DrawScope.drawHorizontalLine(
    y: Float,
    color: Color = Color.White,
    strokeWidth: Float = 8f
) {
    drawLine(
        color = color,
        start = Offset(0f, y),
        end = Offset(size.height, y),
        cap = StrokeCap.Butt, strokeWidth = strokeWidth
    )
}

fun DrawScope.drawDiagonalLineLeftToRight(
    color: Color = Color.White,
    strokeWidth: Float = 8f
) {
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.height, size.width),
        cap = StrokeCap.Round,
        strokeWidth = strokeWidth
    )
}

fun DrawScope.drawDiagonalLineRightToLeft(
    color: Color = Color.White, strokeWidth: Float = 8f
) {
    drawLine(
        color = color,
        start = Offset(size.width, 0f),
        end = Offset(0f, size.width),
        cap = StrokeCap.Round,
        strokeWidth = strokeWidth
    )
}
