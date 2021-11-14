package com.oguzhan.moviecommentsapp.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES

abstract class ThemeChecker {

    companion object {

        fun Context.isDarkThemeOn(): Boolean {
            return resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
        }

    }

}