package id.idprogramming.simplemovie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.idprogramming.simplemovie.databinding.ActivityMainBinding
import id.idprogramming.simplemovie.model.Movie
import id.idprogramming.simplemovie.model.getListMovie

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setToolbarActionBar()
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.about_page) {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.layoutToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            bind.rvMovies.layoutManager = LinearLayoutManager(this)
        } else {
            bind.rvMovies.layoutManager = GridLayoutManager(this, 2)
        }
        val newsAdapter = MainAdapter(getListMovie())
        bind.rvMovies.adapter = newsAdapter

        newsAdapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClicked(data: Movie, ivCover: ImageView) {
                showSelectedItem(data, ivCover)
            }
        })
    }

    private fun showSelectedItem(data: Movie, ivCover: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("data", data)
        intent.putExtra("transitionName", ViewCompat.getTransitionName(ivCover))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            ivCover,
            ViewCompat.getTransitionName(ivCover)!!,
        )

        startActivity(intent, options.toBundle())
    }
}
