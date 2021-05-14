package es.uji.al375496.ujiseabattle.model.data

import kotlin.math.floor

data class Position(var x: Float, var y: Float){
    fun isSameCell(otherPosition: Position): Boolean{
        return floor(x) == floor(otherPosition.x) &&  floor(y) == floor(otherPosition.y)
    }
}