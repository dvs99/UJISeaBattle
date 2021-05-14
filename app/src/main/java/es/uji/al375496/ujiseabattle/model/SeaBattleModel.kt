package es.uji.al375496.ujiseabattle.model

import android.util.Log
import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship
import kotlin.math.abs

class SeaBattleModel(private val playerBoard: Board, private val AIBoard: Board, private val unplacedShips: MutableList<Ship>) {
    private companion object Constants{
        const val MIN_DRAG = 0.2f
    }

    var state = SeaBattleState.PLACE_SHIPS
        private set
    var touchOrigin = Position(0f,0f)
    private var shipStartingPos = Position(0f,0f)
    private var shipIndex = 0
    private var dragIsTap = true

    fun startDrag() {
        if (state == SeaBattleState.PLACE_SHIPS) {
            Log.d("DIEGODEBUG", "try drag")
            //test if the drag starts in an unplaced ship
            for (i in 0 until unplacedShips.size)
                if (unplacedShips[i].isTouched(touchOrigin)) {
                    state = SeaBattleState.DRAG_INTO_BOARD
                    shipStartingPos = Position(unplacedShips[i].position.x, unplacedShips[i].position.y)
                    shipIndex = i
                    dragIsTap=true
                    Log.d("DIEGODEBUG", "dragging from unplaced")
                }

            //test if the drag starts in a placed ship
            val ship: Ship? = playerBoard.getShipAt(touchOrigin)
            if (ship != null){
                state = SeaBattleState.DRAG_INSIDE_BOARD
                shipStartingPos = Position(ship.position.x, ship.position.y)
                dragIsTap=true
                Log.d("DIEGODEBUG", "dragging from placed")
            }
        }
    }

    fun drag(pos: Position) {
        if (state == SeaBattleState.DRAG_INTO_BOARD) {
            //move ship
            unplacedShips[shipIndex].position.x = pos.x + shipStartingPos.x - touchOrigin.x
            unplacedShips[shipIndex].position.y = pos.y + shipStartingPos.y - touchOrigin.y
            //if drag is minimal consider it a tap
            if (dragIsTap && abs(unplacedShips[shipIndex].position.x - shipStartingPos.x) > MIN_DRAG || abs(unplacedShips[shipIndex].position.y - shipStartingPos.y) > MIN_DRAG)
                dragIsTap = false
        }
        else if (state == SeaBattleState.DRAG_INSIDE_BOARD) {
            //move ship
            val ship: Ship? = playerBoard.getShipAt(shipStartingPos)
            if (ship != null){
                ship.position.x = pos.x + shipStartingPos.x - touchOrigin.x
                ship.position.y = pos.y + shipStartingPos.y - touchOrigin.y

                //if drag is minimal consider it a tap
                if (dragIsTap && abs(ship.position.x - shipStartingPos.x) > MIN_DRAG || abs(ship.position.y - shipStartingPos.y) > MIN_DRAG)
                    dragIsTap = false
            }
            else
                state = SeaBattleState.PLACE_SHIPS
        }
    }

    fun endDrag(pos: Position) {
        if (state == SeaBattleState.DRAG_INTO_BOARD) {
            drag(pos)
            //if drag is a tap cancel it and call tap
            if (dragIsTap){
                unplacedShips[shipIndex].position = shipStartingPos
                state = SeaBattleState.PLACE_SHIPS
                tap(shipStartingPos)
            }
            else {
                //try to place ship in the board
                val cellPosition = playerBoard.getRoundedCellPosition(unplacedShips[shipIndex].position)
                if (cellPosition != null && playerBoard.getShipAt(cellPosition) == null) {
                    unplacedShips[shipIndex].position = cellPosition
                    if (playerBoard.addShip(unplacedShips[shipIndex], cellPosition))
                        unplacedShips.removeAt(shipIndex)
                    else //reset ship if it couldn't be placed
                        unplacedShips[shipIndex].position = shipStartingPos
                }
                else //reset ship if it couldn't be placed
                    unplacedShips[shipIndex].position = shipStartingPos
                state = SeaBattleState.PLACE_SHIPS
            }
        }

        if (state == SeaBattleState.DRAG_INSIDE_BOARD) {
            drag(pos)
            val ship: Ship? = playerBoard.getShipAt(shipStartingPos)
            if (ship != null){
                //if drag is a tap cancel it and call tap
                if (dragIsTap){
                    ship.position = shipStartingPos
                    state = SeaBattleState.PLACE_SHIPS
                    tap(shipStartingPos)
                }
                else {
                    val cellPosition = playerBoard.getRoundedCellPosition(ship.position)
                    if (cellPosition == null || !playerBoard.moveShip(shipStartingPos, cellPosition)) {
                        //reset ship if it couldn't be placed
                        ship.position = shipStartingPos
                    }
                }
            }
            state = SeaBattleState.PLACE_SHIPS
        }
    }

    fun tap(pos: Position) {
        playerBoard.rotateShipAt(pos)
    }
}

