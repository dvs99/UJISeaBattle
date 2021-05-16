package es.uji.al375496.ujiseabattle.model.strategy

import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship

class RandomStrategy(override val playerBoard: Board) : IStrategy {

    //play randomly and expect the model to keep asking for shots until one is valid
    override fun getAIGuess(): Position {
        val x = (playerBoard.position.x.toInt() until playerBoard.position.x.toInt() + playerBoard.width).random()
        val y = (playerBoard.position.y.toInt() until playerBoard.position.y.toInt() + playerBoard.height).random()
        return Position(x.toFloat(), y.toFloat())
    }

    //no need to store any information
    override fun shotResults(hitShip: Boolean, sunkShip: Ship?) {}
}