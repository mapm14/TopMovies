package movies.manuelperera.com.topmovies.view.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.item_key_value.view.*
import movies.manuelperera.com.topmovies.R

class ItemKeyValue : FrameLayout {

    constructor(context: Context) : super(context) {
        inflate(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inflate(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context)
    }

    private fun inflate(context: android.content.Context) {
        inflate(context, R.layout.item_key_value, this)
    }

    fun config(key: String, value: String): ItemKeyValue = this.also {
        itemKeyTextView.text = key
        itemValueTextView.text = value
    }

}