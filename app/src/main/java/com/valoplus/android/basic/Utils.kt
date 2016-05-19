package com.valoplus.android.basic

import android.widget.EditText

/**
 * Created by tom on 21.02.16.
 */
object Utils {

    fun getInput(editText: EditText): String {
        return editText.text.toString()
    }

    fun getInputAsInt(editText: EditText): Int {
        return Integer.parseInt(editText.text.toString())
    }
}
