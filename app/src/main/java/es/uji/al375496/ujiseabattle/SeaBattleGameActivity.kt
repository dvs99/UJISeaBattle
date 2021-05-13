package es.uji.al375496.ujiseabattle

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.View
import es.uji.al375496.ujiseabattle.controller.SeaBattleGameController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController

class SeaBattleGameActivity : GameActivity(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
    private lateinit var controller: SeaBattleGameController
    private var sound : Boolean = false
    private var smart : Boolean = false

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        //TODO: are the bools being set before this is called?
        sound = intent.getBooleanExtra(SOUND, false)
        sound = intent.getBooleanExtra(SOUND, false)
        controller = SeaBattleGameController(
            displayMetrics.widthPixels,
            displayMetrics.heightPixels,
            applicationContext,
            sound,
            smart
        )
        return controller
    }
}