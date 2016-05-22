package com.valoplus.android.service

import android.content.ContentResolver
import android.provider.Settings

/**
 * Created by tom on 24.03.16.
 */
object ClientId {
    var contentResolver: ContentResolver? = null

    fun get(contentResolver: ContentResolver? = null): String {
        if(contentResolver != null) {
               this.contentResolver = contentResolver
        }
        return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
    }
}