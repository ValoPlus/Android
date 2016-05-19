package com.valoplus.android.service.add

/**
 * Created by tom on 15.02.16.
 */
internal interface ControllerConfigurationProcess {
    @Throws(ControllerConfigurationProcessException::class)
    fun validate()
}
