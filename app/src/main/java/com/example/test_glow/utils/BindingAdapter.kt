package com.example.test_glow.utils

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("attachImage")
fun ImageView.setPreview(uriString : String?){
    if(uriString != null) {
        Glide.with(this)
            .load(uriString!!.toUri())
            .thumbnail(0.33f)
            .centerCrop()
            .into(this)
    }
}