package es.uji.al375496.ujiseabattle

import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.DisplayMetrics
import es.uji.al375496.ujiseabattle.controller.IRestart
import es.uji.al375496.ujiseabattle.controller.SeaBattleGameController
import es.uji.vj1229.framework.GameActivity
import es.uji.vj1229.framework.IGameController


class SeaBattleGameActivity : GameActivity(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE), IRestart {
    private lateinit var controller: SeaBattleGameController
    private var sound : Boolean = false
    private var smart : Boolean = false

    override fun buildGameController(): IGameController {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        sound = intent.getBooleanExtra(SOUND, true)
        smart = intent.getBooleanExtra(SMART, true)
        controller = SeaBattleGameController(
                displayMetrics.widthPixels,
                displayMetrics.heightPixels,
                applicationContext,
                sound,
                smart,
                this
        )
        return controller
    }

    override fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}