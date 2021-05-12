package es.uji.al375496.ujiseabattle

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch

class MainActivity : AppCompatActivity()
{
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var soundSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var smartSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundSwitch = findViewById(R.id.soundSwitch)
        smartSwitch = findViewById(R.id.smartSwitch)
    }

    fun onPlay(view: View) {
        val intent = Intent(this, SeaBattleGameActivity::class.java).apply {
            putExtra(SMART, smartSwitch.isChecked)
            putExtra(SOUND, soundSwitch.isChecked)
        }
        startActivity(intent)
    }
}