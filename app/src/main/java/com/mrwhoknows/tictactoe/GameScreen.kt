package com.mrwhoknows.tictactoe

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.mrwhoknows.tictactoe.model.CellValue
import com.mrwhoknows.tictactoe.model.GameResultType
import com.mrwhoknows.tictactoe.model.GameState
import com.mrwhoknows.tictactoe.model.UserEvent
import com.mrwhoknows.tictactoe.ui.theme.CelestialBlue
import com.mrwhoknows.tictactoe.ui.theme.NorthernLightsBlue

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun GameScreen(
    state: GameState,
    boardItems: Map<Int, CellValue>,
    onEvent: (UserEvent) -> Unit,
    bgColor: Color = Color.White
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(1f)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        GameBoard()
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .align(Alignment.Center),
            cells = GridCells.Fixed(3),
        ) {

            boardItems.forEach { (cellNo, cellValue) ->
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable(
                                interactionSource = MutableInteractionSource(), indication = null
                            ) {
                                Log.d("TAG", "GameScreen:clickedOn:$cellNo")
                                onEvent.invoke(
                                    UserEvent.BoardCellClicked(cellNo)
                                )
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AnimatedVisibility(
                            visible = cellValue != CellValue.NONE, enter = scaleIn(tween(400))
                        ) {
                            if (cellValue == CellValue.CROSS) Cross()
                            else if (cellValue == CellValue.CIRCLE) Circle()
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = state.hasWon, enter = fadeIn(tween(2000))) {
                DrawVictoryLine(state = state)
            }
        }
    }
}

@Composable
fun DrawVictoryLine(state: GameState, strokeColor: Color = Color.Red, strokeWidth: Float = 15f) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        when (state.gameResult) {
            GameResultType.ROW_1 -> drawHorizontalLine(
                size.width * 1f / 6, strokeColor, strokeWidth
            )
            GameResultType.ROW_2 -> drawHorizontalLine(
                size.width * 3f / 6, strokeColor, strokeWidth
            )
            GameResultType.ROW_3 -> drawHorizontalLine(
                size.width * 5f / 6, strokeColor, strokeWidth
            )
            GameResultType.COLUMN_1 -> drawVerticalLine(
                size.height * 1f / 6, strokeColor, strokeWidth
            )
            GameResultType.COLUMN_2 -> drawVerticalLine(
                size.height * 3f / 6, strokeColor, strokeWidth
            )
            GameResultType.COLUMN_3 -> drawVerticalLine(
                size.height * 5f / 6, strokeColor, strokeWidth
            )
            GameResultType.DIAGONAL_BACKWARD_SLASH -> drawDiagonalLineLeftToRight(
                strokeColor, strokeWidth
            )
            GameResultType.DIAGONAL_FORWARD_SLASH -> drawDiagonalLineRightToLeft(
                strokeColor, strokeWidth
            )
            GameResultType.NONE -> {}
        }
    }
}

@Composable
fun Circle(color: Color = NorthernLightsBlue) = Canvas(
    modifier = Modifier
        .size(58.dp)
        .padding(4.dp)
) {
    drawCircle(
        color = color, style = Stroke(width = 20f)
    )
}

@Composable
fun Cross(color: Color = CelestialBlue) = Canvas(
    modifier = Modifier
        .size(58.dp)
        .padding(4.dp)
) {
    drawDiagonalLineLeftToRight(color, 20f)
    drawDiagonalLineRightToLeft(color, 20f)
}
