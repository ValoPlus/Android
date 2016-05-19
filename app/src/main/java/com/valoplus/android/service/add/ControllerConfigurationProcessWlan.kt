package com.valoplus.android.service.add

import com.valoplus.android.service.ControllerStore
import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.controller.Controller
import de.valoplus.dto.ControllerRequestDTO
import de.valoplus.wlan.Wlan
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom on 14.02.16.
 */
class ControllerConfigurationProcessWlan internal constructor(private val onSuccess: (Controller) -> Unit, private val onError: (String) -> Unit, private val controller: Controller) : ControllerConfigurationProcess {

    private var clientId: String? = null

    init {
        controller.wlan = Wlan()
    }

    fun key(key: String): ControllerConfigurationProcessWlan {
        controller.wlan!!.pass = key
        return this
    }

    fun ssid(ssid: String): ControllerConfigurationProcessWlan {
        controller.wlan!!.ssid = ssid
        return this
    }

    fun clientId(clientId: String): ControllerConfigurationProcessWlan {
        this.clientId = clientId
        return this
    }

    @Throws(ControllerConfigurationProcessException::class)
    override fun validate() {
        val client = RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!)

        val dto = ControllerRequestDTO(controller.wlan!!, controller.controllerAlias!!, clientId!!)

        client.postSettings(dto).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                onError(t?.message!!)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                ControllerStore.controller.add(controller)
                onSuccess(controller)
            }
        })
    }
}
