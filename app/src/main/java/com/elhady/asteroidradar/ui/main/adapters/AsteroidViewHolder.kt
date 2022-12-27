package com.elhady.asteroidradar.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elhady.asteroidradar.databinding.AsteroidItemBinding
import com.elhady.asteroidradar.model.Asteroid

class AsteroidViewHolder(private val binding: AsteroidItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Asteroid) {
        binding.asteroid = item
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