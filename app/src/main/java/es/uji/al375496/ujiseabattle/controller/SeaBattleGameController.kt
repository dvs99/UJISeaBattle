package es.uji.al375496.ujiseabattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import es.uji.al375496.ujiseabattle.Assets
import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import java.lang.Float.min

class SeaBattleGameController (private val width: Int, private val height: Int, context: Context, useSound: Boolean, useSmartOpponent: Boolean) : IGameController {

    //TODO: set right colors
    private companion object Constants{
        const val TOTAL_CELLS_WIDTH = 24
        const val TOTAL_CELLS_HEIGHT = 14
        const val BACKGROUND_COLOR = -0xf
        const val BOARD_LINE_WIDTH = 0.1f
        const val BOARD_LINE_COLOR = -0x1000000
        const val BOARD_CELL_COLOR = -0xfc000
        const val BOARD_HEIGHT = 10
        const val BOARD_WIDTH = 10
        const val SHIP_HORIZONTAL_SPACING = 1
        const val SHIP1_AMOUNT = 4
        const val SHIP2_AMOUNT = 3
        const val SHIP3_AMOUNT = 2
        const val SHIP4_AMOUNT = 1
        const val SHIP1_LENGTH = 1
        const val SHIP2_LENGTH = 2
        const val SHIP3_LENGTH = 3
        const val SHIP4_LENGTH = 4
        val PLAYER_BOARD_POSITION = Position(1, 2)
        val SHIP1_POSITION = Position(14, 9)
        val SHIP2_POSITION = Position(14, 7)
        val SHIP3_POSITION = Position(14, 5)
        val SHIP4_POSITION = Position(14, 3)
    }

    private val graphics = Graphics(width, height)

    private val cellSide = min(width.toFloat() / TOTAL_CELLS_WIDTH,height.toFloat() / TOTAL_CELLS_HEIGHT)
    private val xOffset = (width - TOTAL_CELLS_WIDTH*cellSide) / 2.0f
    private val yOffset = (height - TOTAL_CELLS_HEIGHT*cellSide) / 2.0f

    private val boards = arrayOf(Board(PLAYER_BOARD_POSITION, BOARD_WIDTH, BOARD_HEIGHT))
    private val ships = mutableListOf<Ship>()

    init {
        Assets.createResizedAssets(context, cellSide.toInt())

        for(i : Int in 0 until SHIP1_AMOUNT)
            ships.add(Ship(Position(SHIP1_POSITION.x + ((SHIP1_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP1_POSITION.y), SHIP1_LENGTH, true))
        for(i : Int in 0 until SHIP2_AMOUNT)
            ships.add(Ship(Position(SHIP2_POSITION.x + ((SHIP2_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP2_POSITION.y), SHIP2_LENGTH, true))
        for(i : Int in 0 until SHIP3_AMOUNT)
            ships.add(Ship(Position(SHIP3_POSITION.x + ((SHIP3_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP3_POSITION.y), SHIP3_LENGTH, true))
        for(i : Int in 0 until SHIP4_AMOUNT)
            ships.add(Ship(Position(SHIP4_POSITION.x + ((SHIP4_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP4_POSITION.y), SHIP4_LENGTH, true))
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>?) {
        //TODO("Not yet implemented")
    }

    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        drawBoards()
        drawShips()
        return graphics.frameBuffer
    }

    private fun drawBoards() {
        val halfLineWidth = 0.5f * BOARD_LINE_WIDTH
        with(graphics) {
            for (board in boards) {
                //background
                drawRect(virtualXToRealX(board.position.x.toFloat()), virtualYToRealY(board.position.y.toFloat()), virtualToReal(board.width.toFloat()), virtualToReal(board.height.toFloat()), BOARD_CELL_COLOR)
                //lines
                for (x in board.position.x..(board.position.x + board.width)) {
                    drawLine(virtualXToRealX(x.toFloat()), virtualYToRealY(board.position.y - halfLineWidth),virtualXToRealX(x.toFloat()), virtualYToRealY(board.position.y + board.height + halfLineWidth), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)
                }
                for (y in board.position.y..(board.position.y + board.height)) {
                    drawLine(virtualXToRealX(board.position.x - halfLineWidth), virtualYToRealY(y.toFloat()),virtualXToRealX(board.position.x + board.width + halfLineWidth), virtualYToRealY(y.toFloat()), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)
                }
            }
        }
    }

    private fun drawShips() {
        for (ship in ships){
            if (ship.isHorizontal)
                 graphics.drawBitmap(ship.horizontalImg,virtualXToRealX(ship.position.x.toFloat()), virtualYToRealY(ship.position.y.toFloat()))
            else
                graphics.drawBitmap(ship.verticalImg,virtualXToRealX(ship.position.x.toFloat()), virtualYToRealY(ship.position.y.toFloat()))
            Log.d("DIEGODEBUG", ship.toString())
        }
    }

    private fun virtualXToRealX(x: Float) : Float{
        return x * cellSide + xOffset
    }

    private fun virtualYToRealY(y: Float) : Float{
        return y * cellSide + yOffset
    }

    private fun virtualToReal(y: Float) : Float{
        return y * cellSide
    }
}
