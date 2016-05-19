package com.valoplus.android.service.add

import de.valoplus.controller.Controller

/**
 * Created by tom on 19.02.16.
 */
object  ControllerConfigurationProcessFassade {

    var controller: Controller = Controller(0, "", "", "", "")

    fun step1(onSuccess: (Controller) -> Unit, onError: (String) -> Unit): ControllerConfigurationProcessIp {
        controller = Controller(0, "", "", "", "")
        val ccpi = ControllerConfigurationProcessIp(onSuccess, onError, controller)
        return ccpi
    }

    fun step2(): ControllerConfigurationProcessChannels {
        val ccpc = ControllerConfigurationProcessChannels(controller)
        return ccpc
    }

    fun step3(onSuccess: (Controller) -> Unit, onError: (String) -> Unit): ControllerConfigurationProcessWlan {
        val ccpw = ControllerConfigurationProcessWlan(onSuccess, onError, controller)
        return ccpw
    }
}
