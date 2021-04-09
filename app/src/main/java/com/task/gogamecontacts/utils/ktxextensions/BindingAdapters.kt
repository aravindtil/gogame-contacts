package com.task.gogamecontacts.utils.ktxextensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory


@BindingAdapter(
    value = ["imageUrl", "cropping", "corners", "placeHolder", "errorDrawable"],
    requireAll = false
)
fun ImageView.bindImageFromUrl(
    imageUrl: String?,
    cropping: Int? = null,
    corners: Int? = null,
    placeHolder: Drawable? = null,
    errorDrawable: Drawable? = null,
) {
    if (!imageUrl.isNullOrEmpty()) {
        try {
            var builder = Glide.with(this.context).load(imageUrl)

            var opts = RequestOptions()

            placeHolder?.let { p ->
                opts = opts.placeholder(p)
            }
            errorDrawable?.let { e ->
                opts = opts.error(e)
            }
            corners?.let {
                if (corners != 0)
                    opts = opts.apply(RequestOptions.bitmapTransform(RoundedCorners(corners)))
            }

            if (placeHolder == null) {
                //use a blurred low-res version of the requested image as a thumbnail
                //which effectively becomes a placeholder
                builder = builder.thumbnail(corners?.let { RoundedCorners(it) }?.let {
                    RequestOptions.bitmapTransform(
                        it
                    )
                }?.let { Glide.with(this).load(imageUrl).apply(it) })
            }

            when (cropping) {
                1 -> builder.centerCrop()
            }

            builder.apply(opts)
                .transition(
                    DrawableTransitionOptions.withCrossFade(
                        DrawableCrossFadeFactory.Builder(500)
                            .setCrossFadeEnabled(true)
                            .build()
                    )
                )
                .circleCrop()
                .into(this)
        } catch (t: Throwable) {
            /* no-op */
        }
    } else if (placeHolder != null) {
        Glide.with(this.context).load(placeHolder).into(this)
    }
}

@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}