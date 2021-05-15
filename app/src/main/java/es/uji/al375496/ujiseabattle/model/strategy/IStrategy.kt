package es.uji.al375496.ujiseabattle.model.strategy

import android.icu.text.Transliterator
import es.uji.al375496.ujiseabattle.model.data.Board
import es.uji.al375496.ujiseabattle.model.data.Position
import es.uji.al375496.ujiseabattle.model.data.Ship

interface IStrategy {
    val playerBoard : Board
    fun getAIGuess(): Position
    fun shotResults(hitShip: Boolean, sunkShip: Ship?)
}