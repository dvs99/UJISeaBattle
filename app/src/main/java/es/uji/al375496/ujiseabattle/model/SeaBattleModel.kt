package es.uji.al375496.ujiseabattle.model

import es.uji.al375496.ujiseabattle.model.data.Board

class SeaBattleModel(private val playerBoard: Board, private val AIBoard: Board) {
    var state = SeaBattleState.PLACE_SHIPS
        private set
}
