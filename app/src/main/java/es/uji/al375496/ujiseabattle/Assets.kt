package es.uji.al375496.ujiseabattle

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import es.uji.vj1229.framework.AnimatedBitmap
import es.uji.vj1229.framework.SpriteSheet

object Assets {
    private const val SHIP1_LENGTH = 1
    private const val SHIP2_LENGTH = 2
    private const val SHIP3_LENGTH = 3
    private const val SHIP4_LENGTH = 4

    private const val TEXT_HEIGHT = 1.5f
    private const val TEXT_WIDTH = 8

    private const val BUTTON_HEIGHT = 4
    private const val BUTTON_WIDTH = 4

    private const val RESTART_HEIGHT = 1.5f
    private const val RESTART_WIDTH = 3

    private const val SPLASH_COLUMNS = 6
    private const val SPLASH_PIXELS_WIDE = 300
    private const val SPLASH_PIXELS_HIGH = 250

    private const val EXPLOSION_COLUMNS = 8
    private const val EXPLOSION_PIXELS_WIDE = 96
    private const val EXPLOSION_PIXELS_HIGH = 96

    private const val SMOKE_COLUMNS = 6
    private const val SMOKE_PIXELS_WIDE = 32
    private const val SMOKE_PIXELS_HIGH = 32

    var horizontalShip1 : Bitmap? = null
        private set
    var horizontalShip2 : Bitmap? = null
        private set
    var horizontalShip3 : Bitmap? = null
        private set
    var horizontalShip4 : Bitmap? = null
        private set

    var verticalShip1 : Bitmap? = null
        private set
    var verticalShip2 : Bitmap? = null
        private set
    var verticalShip3 : Bitmap? = null
        private set
    var verticalShip4 : Bitmap? = null
        private set

    var horizontalShip1Sunk : Bitmap? = null
        private set
    var horizontalShip2Sunk : Bitmap? = null
        private set
    var horizontalShip3Sunk : Bitmap? = null
        private set
    var horizontalShip4Sunk : Bitmap? = null
        private set

    var verticalShip1Sunk : Bitmap? = null
        private set
    var verticalShip2Sunk : Bitmap? = null
        private set
    var verticalShip3Sunk : Bitmap? = null
        private set
    var verticalShip4Sunk : Bitmap? = null
        private set

    var aiTurnText : Bitmap? = null
        private set
    var playerTurnText : Bitmap? = null
        private set
    var dragText : Bitmap? = null
        private set
    var loseText : Bitmap? = null
        private set
    var winText : Bitmap? = null
        private set

    var battleButton : Bitmap? = null
        private set
    var restartButton: Bitmap? = null
        private set

    private var splash : SpriteSheet? = null
    var splashAnim : AnimatedBitmap? = null
        private set

    private var explosion : SpriteSheet? = null
    var explosionAnim : AnimatedBitmap? = null
        private set

    private var smoke : SpriteSheet? = null
    var smokeAnim : AnimatedBitmap? = null
        private set


    fun createAndResizeAssets(context: Context, cellSize : Int) {
        val resources : Resources = context.resources

        //animations
        if (splash == null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.splash)
            splash = SpriteSheet(sheet, SPLASH_PIXELS_HIGH, SPLASH_PIXELS_WIDE)
            val frames = ArrayList<Bitmap>()
            splashAnim?.recycle()
            frames.addAll(splash!!.getScaledRow(0, SPLASH_COLUMNS, cellSize, cellSize))
            splashAnim = AnimatedBitmap(0.6f, false, *frames.toTypedArray())
        }

        if (explosion == null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.explosion)
            explosion = SpriteSheet(sheet, EXPLOSION_PIXELS_HIGH, EXPLOSION_PIXELS_WIDE)
            val frames = ArrayList<Bitmap>()
            explosionAnim?.recycle()
            frames.addAll(explosion!!.getScaledRow(0, EXPLOSION_COLUMNS, cellSize, cellSize))
            explosionAnim = AnimatedBitmap(0.8f, false, *frames.toTypedArray())
        }

        if (smoke== null) {
            val sheet = BitmapFactory.decodeResource(resources, R.drawable.smoke)
            smoke = SpriteSheet(sheet, SMOKE_PIXELS_HIGH, SMOKE_PIXELS_WIDE)
            val frames = ArrayList<Bitmap>()
            smokeAnim?.recycle()
            frames.addAll(smoke!!.getScaledRow(0, SMOKE_COLUMNS, cellSize, cellSize))
            smokeAnim = AnimatedBitmap(0.6f, false, *frames.toTypedArray())
        }

        //not animated sprites
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
            cellSize, cellSize * SHIP4_LENGTH, true)

        horizontalShip1Sunk?.recycle()
        horizontalShip1Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship1s),
                cellSize * SHIP1_LENGTH, cellSize, true)
        horizontalShip2Sunk?.recycle()
        horizontalShip2Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship2s),
                cellSize * SHIP2_LENGTH, cellSize, true)
        horizontalShip3Sunk?.recycle()
        horizontalShip3Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship3s),
                cellSize * SHIP3_LENGTH, cellSize, true)
        horizontalShip4Sunk?.recycle()
        horizontalShip4Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship4s),
                cellSize * SHIP4_LENGTH, cellSize, true)

        verticalShip1Sunk?.recycle()
        verticalShip1Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship1vs),
                cellSize, cellSize * SHIP1_LENGTH, true)
        verticalShip2Sunk?.recycle()
        verticalShip2Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship2vs),
                cellSize, cellSize * SHIP2_LENGTH, true)
        verticalShip3Sunk?.recycle()
        verticalShip3Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship3vs),
                cellSize, cellSize * SHIP3_LENGTH, true)
        verticalShip4Sunk?.recycle()
        verticalShip4Sunk = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ship4vs),
                cellSize, cellSize * SHIP4_LENGTH, true)

        aiTurnText?.recycle()
        aiTurnText = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.ai_turn),
                cellSize * TEXT_WIDTH, (cellSize * TEXT_HEIGHT).toInt(), true)
        playerTurnText?.recycle()
        playerTurnText = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.player_turn),
                cellSize * TEXT_WIDTH, (cellSize * TEXT_HEIGHT).toInt(), true)
        dragText?.recycle()
        dragText = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.drag),
                cellSize * TEXT_WIDTH, (cellSize * TEXT_HEIGHT).toInt(), true)
        loseText?.recycle()
        loseText = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.lose),
                cellSize * TEXT_WIDTH, (cellSize * TEXT_HEIGHT).toInt(), true)
        winText?.recycle()
        winText = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.win),
                cellSize * TEXT_WIDTH, (cellSize * TEXT_HEIGHT).toInt(), true)


        battleButton?.recycle()
        battleButton = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.battle),
                cellSize * BUTTON_WIDTH, cellSize * BUTTON_HEIGHT, true)

        restartButton?.recycle()
        restartButton = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.restart),
                cellSize * RESTART_WIDTH, (cellSize * RESTART_HEIGHT).toInt(), true)
    }
}