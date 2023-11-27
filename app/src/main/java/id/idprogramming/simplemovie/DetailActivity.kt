package id.idprogramming.simplemovie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import id.idprogramming.simplemovie.databinding.ActivityDetailBinding
import id.idprogramming.simplemovie.model.Movie
import id.idprogramming.simplemovie.model.getListGenre

class DetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDetailBinding
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setToolbarActionBar()
        initMovieData()
        setMovieData()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.layoutToolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        bind.layoutToolbar.toolbar.setNavigationOnClickListener {
            supportFinishAfterTransition()
        }
    }

    private fun initMovieData() {
        movie = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("data", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("data")
        }
    }

    private fun setMovieData() {
        if (movie != null) {
            val rating = movie!!.voteAverage / 2
            val ratingDigit = String.format("%.1f", rating).toDouble()

            setImageTransition()
            setImageCover()

            bind.tvTitle.text = movie!!.title
            bind.ratingBar.rating = rating.toFloat()
            bind.tvRating.text = "$ratingDigit"
            bind.tvVote.text = "${movie!!.voteCount}"
            bind.tvDescription.text = movie!!.overview
            bind.tvGenre.text = setGenres(movie!!.genreIds)

            bind.actionShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, movie!!.overview)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun setImageTransition() {
        val imageTransitionName: String? = intent.getStringExtra("transitionName")
        bind.ivCover.transitionName = imageTransitionName
    }

    private fun setImageCover() {
        Glide.with(this)
            .asBitmap()
            .load(movie?.posterPath)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?,
                ) {
                    supportStartPostponedEnterTransition()
                    bind.ivCover.setImageBitmap(resource)
                    bind.ivCover.buildLayer()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    supportStartPostponedEnterTransition()
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    supportStartPostponedEnterTransition()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    supportStartPostponedEnterTransition()
                }
            })
    }

    private fun setGenres(genres: List<Int>): String {
        var result = ""

        genres.forEach { id ->
            getListGenre().forEach {
                result += if (id == it.id) {
                    "${it.name}/"
                } else {
                    ""
                }
            }
        }

        return result.substring(0, result.length - 2)
    }
}
