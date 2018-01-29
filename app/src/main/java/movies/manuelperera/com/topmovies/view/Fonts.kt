package movies.manuelperera.com.topmovies.view

import android.content.Context
import android.graphics.Typeface

enum class Fonts(private val fontName: String) {

    REGULAR("fonts/Regular.otf"),
    SEMIBOLD("fonts/SemiBold.otf"),
    BOLD("fonts/Bold.otf"),
    BOLD_ITALIC("fonts/BoldItalic.otf"),
    ITALIC("fonts/Italic.otf"),
    LIGHT("fonts/Light.otf"),
    LIGHT_ITALIC("fonts/LightItalic.otf"),
    SEMIBOLD_ITALIC("fonts/SemiBoldItalic.otf");

    var typeface: Typeface? = null

    fun getTypeface(context: Context): Typeface? {
        typeface ?: run {
            typeface = Typeface.createFromAsset(context.assets, fontName)
        }
        return typeface
    }
}