package id.idprogramming.simplemovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.idprogramming.simplemovie.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var bind: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.layoutToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bind.layoutToolbar.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
