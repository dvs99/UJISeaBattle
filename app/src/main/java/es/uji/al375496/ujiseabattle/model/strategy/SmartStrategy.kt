package es.uji.al375496.ujiseabattle.model.strategy

import android.util.Log
import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship

class SmartStrategy(override val playerBoard: Board, private val possibleShips: MutableList<Ship>) : IStrategy {
    private enum class CellState{ UNKNOWN, HIT, SUNK, WATER}
    private val cells = Array(playerBoard.width) { _ -> Array(playerBoard.height) { _ -> CellState.UNKNOWN }}
    private var probabilityMatrix = Array(playerBoard.width) { _ -> Array(playerBoard.height) { _ -> 0 }}

    var lastPosX = 0
    var lastPosY = 0

    override fun getAIGuess(): Position {
        //reset probabilities
        for (line in probabilityMatrix)
            for (i in line.indices)
                line[i] = 0

        //attack or find
        var attackMode = false
        for (line in cells)
            for (cellState in line)
                if (cellState == CellState.HIT)
                    attackMode = true

        if (!attackMode){
            //find mode: try to place every ship in every possible position (probability+1 for all the cells it covers if it can be done)
            for(ship in possibleShips)
                for (x in probabilityMatrix.indices)
                    for (y in probabilityMatrix[0].indices){
                        couldBePlacedAt(ship.length, x,y)
                    }
        }
        else {
            //attack mode: try to place every within the places that have been hit (probability+1 for all the cells it covers if it can be done except for the hit one)
            for(ship in possibleShips)
                for (x in probabilityMatrix.indices)
                    for (y in probabilityMatrix[0].indices){
                        couldBeHitAt(ship.length, x,y)
                    }
        }

        val bestPos = mutableListOf<Position>()
        var bestProbability = 0

        //store all equal probability positions and the pick random to make it less deterministic
        for (x in probabilityMatrix.indices)
            for (y in probabilityMatrix[0].indices)
                if (probabilityMatrix[x][y] > bestProbability){
                    bestProbability = probabilityMatrix[x][y]
                    bestPos.clear()
                    bestPos.add(Position(x + playerBoard.position.x, y + playerBoard.position.y))
                }
                else if ((probabilityMatrix[x][y] == bestProbability))
                    bestPos.add(Position(x + playerBoard.position.x, y + playerBoard.position.y))

        val selectedPos = bestPos[(0 until bestPos.size).random()]
        lastPosX = selectedPos.x.toInt()
        lastPosY = selectedPos.y.toInt()
        return selectedPos
    }

    private fun couldBeHitAt(length: Int, x: Int, y: Int) {
        var couldBePlacedHorizontally = true
        var couldBePlacedVertically = true
        var couldBeHitHorizontally = false
        var couldBeHitVertically = false

        for (i: Int in 0 until length){
            if (x+i >= cells.size || cells[x+i][y] == CellState.WATER || cells[x+i][y] == CellState.SUNK)
                couldBePlacedHorizontally = false
            if (y+i >= cells[0].size || cells[x][y+i] == CellState.WATER || cells[x][y+i] == CellState.SUNK)
                couldBePlacedVertically = false
        }

        for (i: Int in 0 until length){
            if (x+i < cells.size && cells[x+i][y] == CellState.HIT)
                couldBeHitHorizontally = true
            if (y+i < cells[0].size && cells[x][y+i] == CellState.HIT)
                couldBeHitVertically = true
        }

        for (i: Int in 0 until length){
            if (couldBePlacedHorizontally && couldBeHitHorizontally && cells[x+i][y] == CellState.UNKNOWN)
                probabilityMatrix[x+i][y] += 1
            if (couldBePlacedVertically && couldBeHitVertically && cells[x][y+i] == CellState.UNKNOWN)
                probabilityMatrix[x][y+i] += 1
        }
    }

    private fun couldBePlacedAt(length: Int, x: Int, y: Int) {
        var couldBePlacedHorizontally = true
        var couldBePlacedVertically = true
        for (i: Int in 0 until length){
            if (x+i >= cells.size || cells[x+i][y] == CellState.WATER || cells[x+i][y] == CellState.HIT || cells[x+i][y] == CellState.SUNK)
                couldBePlacedHorizontally = false
            if (y+i >= cells[0].size || cells[x][y+i] == CellState.WATER || cells[x][y+i] == CellState.HIT || cells[x][y+i] == CellState.SUNK)
                couldBePlacedVertically = false
        }

        for (i: Int in 0 until length){
            if (couldBePlacedHorizontally && cells[x+i][y] == CellState.UNKNOWN)
                probabilityMatrix[x+i][y] += 1
            if (couldBePlacedVertically && cells[x][y+i] == CellState.UNKNOWN)
                probabilityMatrix[x][y+i] += 1
        }
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
                        for(ship in possibleShips)
                            if (ship.length == sunkShip.length){
                                possibleShips.remove(ship)
                                break
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