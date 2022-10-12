package com.mrwhoknows.tictactoe.model

enum class CellValue {
    NONE, CIRCLE, CROSS;

    override fun toString() = when (this) {
        CIRCLE -> "O"
        CROSS -> "X"
        else -> "None"
    }
}
