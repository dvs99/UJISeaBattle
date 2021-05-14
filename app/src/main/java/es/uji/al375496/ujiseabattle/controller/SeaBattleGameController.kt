package es.uji.al375496.ujiseabattle.controller

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import es.uji.al375496.ujiseabattle.Assets
import es.uji.al375496.ujiseabattle.model.SeaBattleModel
import es.uji.al375496.ujiseabattle.model.SeaBattleState
import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.IGameController
import es.uji.vj1229.framework.TouchHandler
import java.lang.Float.min

class SeaBattleGameController (width: Int, height: Int, context: Context, private val useSound: Boolean, private val useSmartOpponent: Boolean) : IGameController {

    //TODO: set right colors
    private companion object Constants{
        const val TOTAL_CELLS_WIDTH = 24
        const val TOTAL_CELLS_HEIGHT = 14
        const val BACKGROUND_COLOR = -0xf
        const val HIT_COLOR = -0xfc000
        const val CROSS_PADDING = 0.2F
        const val BOARD_LINE_WIDTH = 0.1f
        const val BOARD_LINE_COLOR = -0x1000000
        const val BOARD_CELL_COLOR = -0xFF482B
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
        val PLAYER_BOARD_POSITION = Position(1f, 2f)
        val AI_BOARD_POSITION = Position(13f, 2f)
        val SHIP1_POSITION = Position(14f, 9f)
        val SHIP2_POSITION = Position(14f, 7f)
        val SHIP3_POSITION = Position(14f, 5f)
        val SHIP4_POSITION = Position(14f, 3f)
        val BUTTON_POS = Position(16f, 5f)
    }

    private val graphics = Graphics(width, height)

    private val cellSide = min(width.toFloat() / TOTAL_CELLS_WIDTH,height.toFloat() / TOTAL_CELLS_HEIGHT)
    private val xOffset = (width - TOTAL_CELLS_WIDTH*cellSide) / 2.0f
    private val yOffset = (height - TOTAL_CELLS_HEIGHT*cellSide) / 2.0f

    private val boards = arrayOf(Board(PLAYER_BOARD_POSITION, BOARD_WIDTH, BOARD_HEIGHT), Board(AI_BOARD_POSITION, BOARD_WIDTH, BOARD_HEIGHT))
    private val ships = mutableListOf<Ship>()

    private var model : SeaBattleModel
    var animation: AnimatedBitmap? = null
    var animationPos = mutableListOf<Position>()

    var showBattleButton = false
    var showAIBoard = false


    init {
        Assets.createAndResizeAssets(context, cellSide.toInt())
        for(i : Int in 0 until SHIP4_AMOUNT)
            ships.add(Ship(Position(SHIP4_POSITION.x + ((SHIP4_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP4_POSITION.y), SHIP4_LENGTH, true))
        for(i : Int in 0 until SHIP3_AMOUNT)
            ships.add(Ship(Position(SHIP3_POSITION.x + ((SHIP3_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP3_POSITION.y), SHIP3_LENGTH, true))
        for(i : Int in 0 until SHIP2_AMOUNT)
            ships.add(Ship(Position(SHIP2_POSITION.x + ((SHIP2_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP2_POSITION.y), SHIP2_LENGTH, true))
        for(i : Int in 0 until SHIP1_AMOUNT)
            ships.add(Ship(Position(SHIP1_POSITION.x + ((SHIP1_LENGTH + SHIP_HORIZONTAL_SPACING) * i), SHIP1_POSITION.y), SHIP1_LENGTH, true))

        model = SeaBattleModel(this, boards[0], boards[1], ships)
    }

    override fun onUpdate(deltaTime: Float, touchEvents: MutableList<TouchHandler.TouchEvent>) {
        if (model.state == SeaBattleState.WAITING){
            if(animation != null){
                animation!!.update(deltaTime)
                Log.d("DIEGODEBUG", "animation playing ${animation!!.currentFrame}")
                if (animation!!.isEnded)
                    model.onAnimationEnd()
            }
        }
        else{
            for (event in touchEvents){
                if (event.type == TouchHandler.TouchType.TOUCH_DOWN) {
                    if (model.state == SeaBattleState.PLACE_SHIPS)
                        model.touchOrigin = Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat()))
                }
                else if (event.type == TouchHandler.TouchType.TOUCH_DRAGGED) {
                    if(model.state == SeaBattleState.DRAG_INSIDE_BOARD || model.state == SeaBattleState.DRAG_INTO_BOARD)
                        model.drag(Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat())))
                    else if (model.state == SeaBattleState.PLACE_SHIPS){
                        model.startDrag()
                        model.drag(Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat())))
                    }
                }
                else if (event.type == TouchHandler.TouchType.TOUCH_UP){
                    if(model.state == SeaBattleState.DRAG_INSIDE_BOARD || model.state == SeaBattleState.DRAG_INTO_BOARD)
                        model.endDrag(Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat())))

                    else if (model.state == SeaBattleState.PLACE_SHIPS){
                        //check for button press
                        if (showBattleButton && Assets.battleButton!= null && (model.state == SeaBattleState.DRAG_INSIDE_BOARD || model.state == SeaBattleState.DRAG_INTO_BOARD
                                        || model.state == SeaBattleState.PLACE_SHIPS) && realXToVirtualX(event.x.toFloat()) > BUTTON_POS.x
                                        && realXToVirtualX(event.x.toFloat()) < BUTTON_POS.x + Assets.battleButton!!.width && realYToVirtualY(event.y.toFloat()) > BUTTON_POS.y
                                        && realYToVirtualY(event.y.toFloat()) < BUTTON_POS.y + Assets.battleButton!!.height)
                            model.startBattle()
                        else
                            model.tap(Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat())))
                    }
                    else if (model.state == SeaBattleState.PLAYER_TURN){
                        model.tap(Position(realXToVirtualX(event.x.toFloat()), realYToVirtualY(event.y.toFloat())))
                    }
                }
            }
        }
    }


    override fun onDrawingRequested(): Bitmap {
        graphics.clear(BACKGROUND_COLOR)
        drawBoards()
        drawShips()
        drawButton()
        if (animation != null)
            for (pos: Position in animationPos)
                graphics.drawBitmap(animation!!.currentFrame, virtualXToRealX(pos.x), virtualYToRealY(pos.y))
        return graphics.frameBuffer
    }

    private fun drawBoards() {
        val halfLineWidth = 0.5f * BOARD_LINE_WIDTH
        with(graphics) {
            for (board in boards) {
                if (!showAIBoard && board.position == AI_BOARD_POSITION)
                    continue
                //background
                drawRect(virtualXToRealX(board.position.x), virtualYToRealY(board.position.y), virtualToReal(board.width.toFloat()), virtualToReal(board.height.toFloat()), BOARD_CELL_COLOR)
                //lines
                for (x in board.position.x.toInt()..(board.position.x.toInt() + board.width))
                    drawLine(virtualXToRealX(x.toFloat()), virtualYToRealY(board.position.y - halfLineWidth),virtualXToRealX(x.toFloat()), virtualYToRealY(board.position.y + board.height + halfLineWidth), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)

                for (y in board.position.y.toInt()..(board.position.y.toInt() + board.height))
                    drawLine(virtualXToRealX(board.position.x - halfLineWidth), virtualYToRealY(y.toFloat()),virtualXToRealX(board.position.x + board.width + halfLineWidth), virtualYToRealY(y.toFloat()), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)

                //cells
                for (line in board.cells)
                    for (cell in line)
                        if (cell.hit && cell.ship == null) {
                            drawLine(virtualXToRealX(cell.x.toFloat() + board.position.x + CROSS_PADDING), virtualYToRealY(cell.y.toFloat() + board.position.y + CROSS_PADDING), virtualXToRealX(cell.x.toFloat() + 1f + board.position.x - CROSS_PADDING), virtualYToRealY(cell.y.toFloat() + 1f + board.position.y - CROSS_PADDING), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)
                            drawLine(virtualXToRealX(cell.x.toFloat() + 1f + board.position.x - CROSS_PADDING), virtualYToRealY(cell.y.toFloat() + board.position.y + CROSS_PADDING), virtualXToRealX(cell.x.toFloat() + board.position.x + CROSS_PADDING), virtualYToRealY(cell.y.toFloat() + 1f + board.position.y - CROSS_PADDING), virtualToReal(BOARD_LINE_WIDTH), BOARD_LINE_COLOR)
                        }
                //ships
                for(ship in board.ships) {
                    if (board.position != AI_BOARD_POSITION || ship.sunk){
                        graphics.drawBitmap(ship.currentImg, virtualXToRealX(ship.position.x), virtualYToRealY(ship.position.y))
                    }
                    if (board.position == AI_BOARD_POSITION && !ship.sunk){
                        for (hit in ship.hits){
                        drawLine(virtualXToRealX(hit.x + CROSS_PADDING), virtualYToRealY(hit.y + CROSS_PADDING), virtualXToRealX(hit.x+ 1f - CROSS_PADDING), virtualYToRealY(hit.y + 1f - CROSS_PADDING), virtualToReal(BOARD_LINE_WIDTH), HIT_COLOR)
                        drawLine(virtualXToRealX(hit.x + 1f - CROSS_PADDING), virtualYToRealY(hit.y + CROSS_PADDING), virtualXToRealX(hit.x + CROSS_PADDING), virtualYToRealY(hit.y + 1f - CROSS_PADDING), virtualToReal(BOARD_LINE_WIDTH), HIT_COLOR)
                        }
                    }
                }
            }
        }
    }

    private fun drawShips() {
        with (graphics){
            for (ship in ships){
                drawBitmap(ship.currentImg, virtualXToRealX(ship.position.x), virtualYToRealY(ship.position.y))
            }
        }
    }

    private fun drawButton() {
        if (showBattleButton)
            graphics.drawBitmap(Assets.battleButton, virtualXToRealX(BUTTON_POS.x), virtualYToRealY(BUTTON_POS.y))
    }


    private fun virtualXToRealX(x: Float) : Float{
        return x * cellSide + xOffset
    }

    private fun realXToVirtualX(x: Float) : Float{
        return (x - xOffset) / cellSide
    }

    private fun virtualYToRealY(y: Float) : Float{
        return y * cellSide + yOffset
    }

    private fun realYToVirtualY(y: Float) : Float{
        return (y - yOffset) / cellSide
    }

    private fun virtualToReal(n: Float) : Float{
        return n * cellSide
    }
}
