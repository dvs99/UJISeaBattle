package es.uji.al375496.ujiseabattle.model.strategy

import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship

class SimpleStrategy(override val playerBoard: Board) : IStrategy {
    private enum class CellState{ UNKNOWN, HIT, SUNK, WATER}
    private val cells = Array(playerBoard.width) { _ -> Array(playerBoard.height) { _ -> CellState.UNKNOWN }}

    var lastPosX = 0
    var lastPosY = 0

    override fun getAIGuess(): Position {

        var bestPos = Position (0f,0f)

        //look for hit ship
        var hitShip = false
        for (x in cells.indices)
            for (y in cells[0].indices)
                if (cells[x][y] == CellState.HIT){
                    hitShip = true
                     when (CellState.UNKNOWN) {
                        cells[x][y+1] -> bestPos = Position(x + playerBoard.position.x, y+1f + playerBoard.position.y)
                        cells[x][y-1] -> bestPos = Position(x + playerBoard.position.x, y-1f + playerBoard.position.y)
                        cells[x+1][y] -> bestPos = Position(x+1f + playerBoard.position.x, y + playerBoard.position.y)
                        cells[x-1][y] -> bestPos = Position(x-1f + playerBoard.position.x, y + playerBoard.position.y)
                    }
                }

        if (!hitShip)
            bestPos = Position((playerBoard.position.x.toInt() until playerBoard.position.x.toInt() + playerBoard.width).random().toFloat(), (playerBoard.position.y.toInt() until playerBoard.position.y.toInt() + playerBoard.height).random().toFloat())

        lastPosX = bestPos.x.toInt()
        lastPosY = bestPos.y.toInt()
        return bestPos
    }

    override fun shotResults(hitShip: Boolean, sunkShip: Ship?) {
        var indexes = playerBoard.getCellIndexesAtPosition(Position(lastPosX.toFloat(), lastPosY.toFloat()))
        if (indexes != null){
            var x = indexes.x.toInt()
            var y = indexes.y.toInt()

            if (hitShip){
                if (sunkShip != null){
                    indexes = playerBoard.getCellIndexesAtPosition(sunkShip.position)
                    if (indexes != null){
                        x = indexes.x.toInt()
                        y = indexes.y.toInt()
                        for (i: Int in 0 until sunkShip.length){
                            if (sunkShip.isHorizontal())
                                cells[x+i][y] = CellState.SUNK
                            else
                                cells[x][y+i] = CellState.SUNK
                        }
                    }
                }
                else
                    cells[x][y] = CellState.HIT
            }
            else
                cells[x][y] = CellState.WATER
        }
    }
}