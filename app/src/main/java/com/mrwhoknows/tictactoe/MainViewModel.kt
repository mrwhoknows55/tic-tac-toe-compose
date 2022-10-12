package com.mrwhoknows.tictactoe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mrwhoknows.tictactoe.model.CellValue
import com.mrwhoknows.tictactoe.model.GameResultType
import com.mrwhoknows.tictactoe.model.GameState
import com.mrwhoknows.tictactoe.model.UserEvent

class MainViewModel : ViewModel() {
    var state by mutableStateOf(GameState())
        private set

    val boardItems: MutableMap<Int, CellValue> = initBoardItems()

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.BoardCellClicked -> {
                insertValueInCell(event.cellNo)
            }
            is UserEvent.PlayAgainClicked -> {
                resetGame()
            }
        }
    }

    private fun insertValueInCell(cellNo: Int) {
        if (boardItems[cellNo] != CellValue.NONE) {
            return
        }
        when (state.currentPlayerTurn) {
            CellValue.CIRCLE -> {
                boardItems[cellNo] = CellValue.CIRCLE
                state = if (checkForGameResult(CellValue.CIRCLE)) {
                    state.copy(
                        circleCount = state.circleCount + 1,
                        currentPlayerTurn = CellValue.NONE,
                        hasWon = true
                    )
                } else if (isBoardFull()) {
                    state.copy(
                        cellDrawCount = state.cellDrawCount + 1,
                    )
                } else {
                    state.copy(
                        currentPlayerTurn = CellValue.CROSS
                    )
                }
            }
            CellValue.CROSS -> {
                boardItems[cellNo] = CellValue.CROSS
                state = if (checkForGameResult(CellValue.CROSS)) {
                    state.copy(
                        crossCount = state.crossCount + 1,
                        currentPlayerTurn = CellValue.NONE,
                        hasWon = true
                    )
                } else if (isBoardFull()) {
                    state.copy(
                        cellDrawCount = state.cellDrawCount + 1,
                    )
                } else {
                    state.copy(
                        currentPlayerTurn = CellValue.CIRCLE
                    )
                }
            }
            else -> {
                return
            }
        }
    }

    private fun isBoardFull(): Boolean {
        if (boardItems.containsValue(CellValue.NONE)) return false
        return true
    }

    private fun checkForGameResult(cellValue: CellValue): Boolean {
        when {
            boardItems[1] == cellValue && boardItems[2] == cellValue && boardItems[3] == cellValue -> {
                state = state.copy(gameResult = GameResultType.ROW_1)
                return true
            }
            boardItems[4] == cellValue && boardItems[5] == cellValue && boardItems[6] == cellValue -> {
                state = state.copy(gameResult = GameResultType.ROW_2)
                return true
            }
            boardItems[7] == cellValue && boardItems[8] == cellValue && boardItems[9] == cellValue -> {
                state = state.copy(gameResult = GameResultType.ROW_3)
                return true
            }
            boardItems[1] == cellValue && boardItems[4] == cellValue && boardItems[7] == cellValue -> {
                state = state.copy(gameResult = GameResultType.COLUMN_1)
                return true
            }
            boardItems[2] == cellValue && boardItems[5] == cellValue && boardItems[8] == cellValue -> {
                state = state.copy(gameResult = GameResultType.COLUMN_2)
                return true
            }
            boardItems[3] == cellValue && boardItems[6] == cellValue && boardItems[9] == cellValue -> {
                state = state.copy(gameResult = GameResultType.COLUMN_3)
                return true
            }
            boardItems[1] == cellValue && boardItems[5] == cellValue && boardItems[9] == cellValue -> {
                state = state.copy(gameResult = GameResultType.DIAGONAL_BACKWARD_SLASH)
                return true
            }
            boardItems[3] == cellValue && boardItems[5] == cellValue && boardItems[7] == cellValue -> {
                state = state.copy(gameResult = GameResultType.DIAGONAL_FORWARD_SLASH)
                return true
            }
            else -> return false
        }
    }

    private fun resetGame() {
        boardItems.forEach { (index, _) ->
            boardItems[index] = CellValue.NONE
        }
        state = state.copy(
            currentPlayerTurn = CellValue.CROSS,
            gameResult = GameResultType.NONE,
            hasWon = false
        )
    }

    private fun initBoardItems(): MutableMap<Int, CellValue> = mutableMapOf(
        1 to CellValue.NONE,
        2 to CellValue.NONE,
        3 to CellValue.NONE,
        4 to CellValue.NONE,
        5 to CellValue.NONE,
        6 to CellValue.NONE,
        7 to CellValue.NONE,
        8 to CellValue.NONE,
        9 to CellValue.NONE
    )
}