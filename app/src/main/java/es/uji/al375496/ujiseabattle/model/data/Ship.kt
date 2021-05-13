package es.uji.al375496.ujiseabattle.model.data

import android.graphics.Bitmap
import es.uji.al375496.ujiseabattle.Assets

class Ship (var position: Position, val length: Int, var isHorizontal: Boolean) {
    var sunk = false
    var verticalImg : Bitmap? = when (length){
        1 -> Assets.verticalShip1
        2 -> Assets.verticalShip2
        3 -> Assets.verticalShip3
        4 -> Assets.verticalShip4
        else-> null
    }
    var horizontalImg: Bitmap? = when (length){
        1 -> Assets.horizontalShip1
        2 -> Assets.horizontalShip2
        3 -> Assets.horizontalShip3
        4 -> Assets.horizontalShip4
        else-> null
    }

    fun sink(){
        sunk = true
        //TODO: sunk sprites
    }
}