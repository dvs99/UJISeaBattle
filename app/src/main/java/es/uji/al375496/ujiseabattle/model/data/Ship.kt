package es.uji.al375496.ujiseabattle.model.data

import android.graphics.Bitmap
import es.uji.al375496.ujiseabattle.Assets

data class Ship (var position: Position, val length: Int, private var isHorizontal: Boolean) {
    var hits = mutableListOf<Position>()

    var sunk = false
        private set

    private var verticalImg : Bitmap? = when (length){
        1 -> Assets.verticalShip1
        2 -> Assets.verticalShip2
        3 -> Assets.verticalShip3
        4 -> Assets.verticalShip4
        else-> null
    }
    private var horizontalImg: Bitmap? = when (length){
        1 -> Assets.horizontalShip1
        2 -> Assets.horizontalShip2
        3 -> Assets.horizontalShip3
        4 -> Assets.horizontalShip4
        else-> null
    }

    var currentImg = if (isHorizontal) horizontalImg else verticalImg
        private set

    private fun sink(){
        sunk = true
        //TODO: sunk sprites
    }

    fun isTouched(pos: Position): Boolean {
        for (i: Int in 0 until length)
            if (isHorizontal) {
                if (pos.isSameCell(Position(position.x + i, position.y)))
                    return true
            }
            else{
                if (pos.isSameCell(Position(position.x, position.y + i)))
                    return true
            }

        return false
    }

    fun rotate() {
        isHorizontal = !isHorizontal
        currentImg = if (isHorizontal) horizontalImg else verticalImg
        //TODO: lists
    }

    fun isHorizontal() : Boolean{
        return isHorizontal
    }

    //Assumes it has been checked to be actually a hit that hasn't been hit before and is valid for this ship
    fun hit(pos: Position){
        hits.add(pos.copy())
        if (hits.size == length)
            sink()
    }
}