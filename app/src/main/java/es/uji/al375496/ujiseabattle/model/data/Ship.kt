package es.uji.al375496.ujiseabattle.model.data

import es.uji.al375496.ujiseabattle.Assets

data class Ship (var position: Position, val length: Int, private var isHorizontal: Boolean) {
    var hits = mutableListOf<Position>()

    var sunk = false
        private set

    private var verticalImg = arrayOf( when (length){
        1 -> Assets.verticalShip1
        2 -> Assets.verticalShip2
        3 -> Assets.verticalShip3
        4 -> Assets.verticalShip4
        else-> null
    }, when (length){
        1 -> Assets.verticalShip1Sunk
        2 -> Assets.verticalShip2Sunk
        3 -> Assets.verticalShip3Sunk
        4 -> Assets.verticalShip4Sunk
        else-> null
    })

    private var horizontalImg = arrayOf( when (length){
        1 -> Assets.horizontalShip1
        2 -> Assets.horizontalShip2
        3 -> Assets.horizontalShip3
        4 -> Assets.horizontalShip4
        else-> null
    }, when (length){
        1 -> Assets.horizontalShip1Sunk
        2 -> Assets.horizontalShip2Sunk
        3 -> Assets.horizontalShip3Sunk
        4 -> Assets.horizontalShip4Sunk
        else-> null
    })

    private var imageIndex = 0

    var currentImg = if (isHorizontal) horizontalImg[imageIndex] else verticalImg[imageIndex]
        private set

    private fun sink(){
        sunk = true
        imageIndex = 1
        currentImg = if (isHorizontal) horizontalImg[imageIndex] else verticalImg[imageIndex]
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
        currentImg = if (isHorizontal) horizontalImg[imageIndex] else verticalImg[imageIndex]
    }

    fun isHorizontal() : Boolean{
        return isHorizontal
    }

    //assumes it has been checked to be actually a hit that hasn't been hit before and is valid for this ship
    fun hit(pos: Position){
        hits.add(pos.copy())
        if (hits.size == length)
            sink()
    }
}