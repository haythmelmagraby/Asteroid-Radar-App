package com.udacity.asteroidradar


import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.main.MainRecyclerViewAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context
            .getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context
            .getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context
            .getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context
            .getString(R.string.not_hazardous_asteroid_image)
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

// using bind adapter to set list to recycler view data from viewmodel
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data:List<Asteroid>?){
    data?.let {
        val adapter = recyclerView.adapter as MainRecyclerViewAdapter
        adapter.submitList(data)
    }
}
// binding adapter for asteroid status
@BindingAdapter("asteroidStatus")
fun bindStatus(progressBar: ProgressBar,status: AsteroidApiStatus?){
    when(status){
        AsteroidApiStatus.LOADING->{
            progressBar.visibility = View.VISIBLE
        }
        AsteroidApiStatus.DONE->{
            progressBar.visibility = View.GONE
        }
    }
}


@BindingAdapter("imageUrl")
fun bindPictureOfDay(imageView: ImageView,pictureOfDay: PictureOfDay?){
    pictureOfDay?.let {
        val imageUri = it.url.toUri().buildUpon().scheme("https").build()
        Picasso.with(imageView.context).load(imageUri).centerCrop().fit().into(imageView)
        imageView.contentDescription = it.title
    }
}

@BindingAdapter("closeApproachDateAdapter")
fun bindCloseApproachDate(textView: TextView,closeApproachDate:String?){
    closeApproachDate?.let {
        textView.text = closeApproachDate
    }
}

@BindingAdapter("codenameAdapter")
fun bindCodename(textView: TextView,codename:String?){
    codename?.let {
        textView.text = codename
    }
}