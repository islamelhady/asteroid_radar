package com.elhady.asteroidradar.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elhady.asteroidradar.databinding.AsteroidItemBinding
import com.elhady.asteroidradar.model.Asteroid

class AsteroidViewHolder(private val binding: AsteroidItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: AsteroidClick, item: Asteroid) {
        binding.asteroid = item
        binding.asteroidClick = listener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)
            return AsteroidViewHolder(binding)
        }
    }

}

/**
 * Click listener for Groups. By giving the block a name it helps a reader understand what it does.
 */
class AsteroidClick(val block: (asteroid: Asteroid) -> Unit) {
    /**
     * Called when a Asteroid is clicked
     * @param asteroid the Asteroid Radar that was clicked
     */
    fun onClick(asteroid: Asteroid) = block(asteroid)
}