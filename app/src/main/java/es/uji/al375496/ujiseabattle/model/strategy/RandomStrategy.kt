package es.uji.al375496.ujiseabattle.model.strategy

import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship

class RandomStrategy(override val playerBoard: Board) : IStrategy {
    override fun getAIGuess(): Position {
        val x = (playerBoard.position.x.toInt() until playerBoard.position.x.toInt() + playerBoard.width).random()
        val y = (playerBoard.position.y.toInt() until playerBoard.position.y.toInt() + playerBoard.height).random()
        return Position(x.toFloat(), y.toFloat())
    }

    override fun shotResults(hitShip: Boolean, sunkShip: Ship?) {
    }
}