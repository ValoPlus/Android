package com.valoplus.android.activity.basic

import android.app.AlertDialog
import android.support.v7.app.ActionBarActivity

/**
 * Created by tom on 22.02.16.
 */
abstract class AbstractActivity : ActionBarActivity() {

    protected abstract fun initUiElements()

    protected open fun showError(e: String) {
        AlertDialog.Builder(this).setTitle("Error")
                .setNeutralButton("OK", { dialog, id -> dialog.cancel() })
                .setMessage(e)
                .create()
                .show()
    }
}
