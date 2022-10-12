package com.mrwhoknows.tictactoe.model

sealed interface UserEvent {
    object PlayAgainClicked : UserEvent
    data class BoardCellClicked(val cellNo: Int) : UserEvent
}

