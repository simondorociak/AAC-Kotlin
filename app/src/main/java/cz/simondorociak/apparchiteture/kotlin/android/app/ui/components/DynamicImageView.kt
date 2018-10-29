package cz.simondorociak.apparchiteture.kotlin.android.app.ui.components

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import jp.wasabeef.picasso.transformations.CropCircleTransformation

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
class DynamicImageView(context: Context?, attributeSet: AttributeSet?) : AppCompatImageView(context, attributeSet) {

    fun loadURL(url: String?, transformation: Transformation = CropCircleTransformation()) {
        Picasso.get()
            .load(url)
            .transform(transformation)
            .fit()
            .centerCrop()
            .into(this)
    }
}