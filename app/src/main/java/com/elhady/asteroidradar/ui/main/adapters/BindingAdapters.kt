package com.elhady.asteroidradar.ui.main.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elhady.asteroidradar.R
import com.elhady.asteroidradar.model.Asteroid
import com.elhady.asteroidradar.model.PictureOfDay
import com.squareup.picasso.Picasso
import retrofit2.Response.error

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

/**
 * Binding adapter used to display images from URL using Picasso
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, picture: PictureOfDay?) {
    /**
     *  (mediaType) The image of the day could be an image or video, we are using only the image,
     *  to know what media type is you have to check the (mediaType) field, if this value is "image"
     *  you are going to download and use the image, if it's video you are going to ignore it.
     */
    if (picture?.mediaType ==  "image") {
        Picasso.get().load(picture.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .error(R.drawable.ic_help_circle)
            .into(imageView)
        imageView.contentDescription = imageView.context.getString(
            R.string.nasa_picture_of_day_content_description_format,
            picture.title
        )
    }else{
        imageView.contentDescription = imageView.context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
    }

}