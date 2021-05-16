package es.uji.al375496.ujiseabattle.model.data

import kotlin.math.floor
import kotlin.math.round

data class Board (var position: Position, val width: Int, val height: Int){
    val ships = mutableListOf<Ship>()
    val cells = Array(width) { x-> Array(height) { y -> Cell(x, y)}}

    // returns the position to place something in a cell when given a position inside that cell
    fun getCellPosition(pos: Position) : Position?{
        return if (pos.x >= position.x && pos.x < position.x + width && pos.y >= position.y && pos.y < position.y + height)
            Position(floor(pos.x), floor(pos.y))
        else
            null
    }

    // returns the numbers corresponding to the indexes of a cell when given a position inside that cell
    fun getCellIndexesAtPosition(pos: Position) : Position?{
        val cellPos = getCellPosition(pos)
        if (cellPos != null)
            return Position(cellPos.x - position.x, cellPos.y - position.y)
        return null
    }

    // returns the position to place something in the closest cell
    fun getRoundedCellPosition(pos: Position) : Position?{
        return if (pos.x > position.x - 1 && pos.x < position.x + width && pos.y > position.y - 1 && pos.y < position.y + height){
            Position(maxOf(minOf(round(pos.x), position.x + width - 1f), position.x), maxOf(minOf(round(pos.y), position.y + height - 1f), position.y))
        }
        else
            null
    }

    //tries to add a ship at the given position, returns true if successful
    fun addShip(ship: Ship, pos: Position): Boolean{
        if (canAddShip(ship, pos))
        {
            val cellIndexes = getCellIndexesAtPosition(pos)
            if (cellIndexes != null){
                val x = cellIndexes.x.toInt()
                val y = cellIndexes.y.toInt()

                //place the ship
                ships.add(ship)
                val cellPos = getCellPosition(pos)
                if (cellPos != null){
                    ship.position = cellPos
                    for (i: Int in 0 until ship.length)
                        if (ship.isHorizontal())
                            cells[x+i][y].ship = ship
                        else
                            cells[x][y+i].ship = ship
                    return true
                }
            }
        }
        return false
    }

    fun canAddShip(ship: Ship, pos: Position): Boolean{
        val cellIndexes = getCellIndexesAtPosition(pos)
        if (cellIndexes != null){
            val x = cellIndexes.x.toInt()
            val y = cellIndexes.y.toInt()

            //check that the cells are available
            for (i: Int in 0 until ship.length)
                if (ship.isHorizontal() && (x+i >= cells.size || cells[x+i][y].ship != null) || !ship.isHorizontal() && (y+i >= cells[0].size || cells[x][y+i].ship != null))
                    return false

            //can place the ship
            return true
        }
        return false
    }

    //tries to get a ship from the board in a given position, returns null if not found
    fun getShipAt(pos: Position): Ship? {
        val cellIndexes= getCellIndexesAtPosition(pos)

        if (cellIndexes != null){
            val x = cellIndexes.x.toInt()
            val y = cellIndexes.y.toInt()
            return cells[x][y].ship
        }
        return null
    }

    //tries to rotate a ship in a given position if it exists and has space to be rotated, returns true is successful
    fun rotateShipAt(pos: Position): Boolean{
        //try to get the ship
        val ship: Ship = getShipAt(pos) ?: return false

        val cellIndexes= getCellIndexesAtPosition(ship.position)
        if (cellIndexes != null){
            val x = cellIndexes.x.toInt()
            val y = cellIndexes.y.toInt()


            //check that the cells are available
            for (i: Int in 1 until ship.length)
                if (!ship.isHorizontal() && (x+i >= cells.size || cells[x+i][y].ship != null) || ship.isHorizontal() && (y+i >= cells[0].size || cells[x][y+i].ship != null))
                    return false

            //actually rotate the ship
            for (i: Int in 1 until ship.length)
                if (ship.isHorizontal())
                    cells[x+i][y].ship = null
                else
                    cells[x][y+i].ship = null
            ship.rotate()
            for (i: Int in 1 until ship.length)
                if (ship.isHorizontal())
                    cells[x+i][y].ship = ship
                else
                    cells[x][y+i].ship = ship
            return true
        }
        return false
    }

    //tries to move a ship in a given position to another given position if it exists and has space to be rotated, returns true is successful
    fun moveShip(from: Position, to: Position): Boolean{
        //try to get the ship
        val ship: Ship = getShipAt(from) ?: return false

        val cellIndexes= getCellIndexesAtPosition(from)
        if (cellIndexes != null){
            val fromX = cellIndexes.x.toInt()
            val fromY = cellIndexes.y.toInt()

            val toCellIndexes= getCellIndexesAtPosition(to)
            if (toCellIndexes != null){
                val toX = toCellIndexes.x.toInt()
                val toY = toCellIndexes.y.toInt()

                //check that the cells are available
                for (i: Int in 0 until ship.length)
                    if (ship.isHorizontal() && (toX+i >= cells.size || cells[toX+i][toY].ship != null && cells[toX+i][toY].ship != ship)|| !ship.isHorizontal() && (toY+i >= cells[0].size || cells[toX][toY+i].ship != null && cells[toX][toY+i].ship != ship)){
                        return false
                    }

                //actually move the ship
                for (i: Int in 0 until ship.length){
                    if (ship.isHorizontal())
                        cells[fromX+i][fromY].ship = null
                    else
                        cells[fromX][fromY+i].ship = null
                }

                val toCellPos = getCellPosition(to)
                if (toCellPos != null){
                    ship.position = toCellPos

                    for (i: Int in 0 until ship.length)
                        if (ship.isHorizontal())
                            cells[toX+i][toY].ship = ship
                        else
                            cells[toX][toY+i].ship = ship
                    return true
                }
            }
        }
        return false
    }

    //returns true if it hits a ship, false if hits water and null if it hits outside the board
    fun tryHitAt(pos: Position) : Boolean? {
        val cellIndexes = getCellIndexesAtPosition(pos)
        if (cellIndexes != null){
            val x = cellIndexes.x.toInt()
            val y = cellIndexes.y.toInt()
            if (!cells[x][y].hit){
                val cellPos = getCellPosition(pos)
                if (cellPos != null){
                    cells[x][y].hit = true
                    return if (cells[x][y].ship == null)
                        false
                    else{
                        cells[x][y].ship?.hit(cellPos)
                        true
                    }
                }
            }
        }
    return null
    }
}