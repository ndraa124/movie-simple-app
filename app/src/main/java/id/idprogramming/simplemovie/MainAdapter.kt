package id.idprogramming.simplemovie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.idprogramming.simplemovie.databinding.ItemMovieBinding
import id.idprogramming.simplemovie.model.Movie

class MainAdapter(private val data: List<Movie>) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bind = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ListViewHolder(private val bind: ItemMovieBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Movie) {
            val rating = data.voteAverage / 2
            val ratingDigit = String.format("%.1f", rating).toDouble()

            ViewCompat.setTransitionName(bind.ivCover, data.posterPath)
            Glide.with(itemView.context)
                .load(data.posterPath)
                .into(bind.ivCover)

            bind.tvTitle.text = data.title
            bind.ratingBar.rating = rating.toFloat()
            bind.tvRating.text = "$ratingDigit"
            bind.tvVote.text = "${data.voteCount}"
            bind.tvDescription.text = data.overview

            itemView.setOnClickListener {
                onItemClickListener.onItemClicked(data, bind.ivCover)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(data: Movie, ivCover: ImageView)
    }
}
