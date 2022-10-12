package com.mrwhoknows.tictactoe.model

data class GameState(
    val cellDrawCount: Int = 0,
    val circleCount: Int = 0,
    val crossCount: Int = 0,
    val currentPlayerTurn: CellValue = CellValue.CROSS,
    val gameResult: GameResultType = GameResultType.NONE,
    val hasWon: Boolean = false
) {
    fun currentPlayerTurnText() = "$currentPlayerTurn's turn"
}
