package com.pimuseum.indexpicker.tools

import android.view.View

fun View.noFastClick(delay : Long? = null) {
    isEnabled = false
    postDelayed({isEnabled = true},delay?:1000)
}