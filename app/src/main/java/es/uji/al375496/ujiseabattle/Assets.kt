package es.uji.al375496.ujiseabattle

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.Graphics
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val SHIP1_LENGTH = 1
    private const val SHIP2_LENGTH = 2
    private const val SHIP3_LENGTH = 3
    private const val SHIP4_LENGTH = 4

    //TODO
    private const val SPLASH_ROWS = 0
    private const val SPLASH_COLUMNS = 0
    private const val SPLASH_WIDTH = 0
    private const val SPLASH_HEIGHT = 0

    private const val EXPLOSION_ROWS = 0
    private const val EXPLOSION_COLUMNS = 0
    private const val EXPLOSION_WIDTH = 0
    private const val EXPLOSION_HEIGHT = 0
    //end todo

    var horizontalShip1 : Bitmap? = null
    var horizontalShip2 : Bitmap? = null
    var horizontalShip3 : Bitmap? = null
    var horizontalShip4 : Bitmap? = null
    var verticalShip1 : Bitmap? = null
    var verticalShip2 : Bitmap? = null
    var verticalShip3 : Bitmap? = null
    var verticalShip4 : Bitmap? = null

    private var splash : SpriteSheet? = null
    var splashAnim : AnimatedBitmap? = null
    private var explosion : SpriteSheet? = null
    var explosionAnim : AnimatedBitmap? = null

    fun loadDrawableAssets(context: Context) {
        val resources : Resources = context.resources


        //TODO
        /*
        if (splash == null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.splash)
            splash = SpriteSheet(sheet, SPLASH_HEIGHT, SPLASH_WIDTH)
        }
        [...]
         */
    }

    fun createResizedAssets(context: Context, cellSize : Int) {
        val resources : Resources = context.resources
        horizontalShip1?.recycle()
        horizontalShip1 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship1),
            cellSize * SHIP1_LENGTH, cellSize, true)
        horizontalShip2?.recycle()
        horizontalShip2 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship2),
            cellSize * SHIP2_LENGTH, cellSize, true)
        horizontalShip3?.recycle()
        horizontalShip3 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship3),
            cellSize * SHIP3_LENGTH, cellSize, true)
        horizontalShip4?.recycle()
        horizontalShip4 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship4),
            cellSize * SHIP4_LENGTH, cellSize, true)
        verticalShip1?.recycle()
        verticalShip1 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship1v),
            cellSize, cellSize * SHIP1_LENGTH, true)
        verticalShip2?.recycle()
        verticalShip2 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship2v),
            cellSize, cellSize * SHIP2_LENGTH, true)
        verticalShip3?.recycle()
        verticalShip3 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship3v),
            cellSize, cellSize * SHIP3_LENGTH, true)
        verticalShip4?.recycle()
        verticalShip4 = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.ship4v),
            cellSize, cellSize * SHIP4_LENGTH, false)

        //TODO
        /*val frames = ArrayList<Bitmap>()
        waterSplash?.recycle()
        for (row in 0 until SPLASH_ROWS)
            splash?.let { frames.addAll(it.getScaledRow(row, SPLASH_COLUMNS, cellSize,
                cellSize)) }
        waterSplash = AnimatedBitmap(2.0f, false, *frames.toTypedArray())
        [...]*/
    }
}