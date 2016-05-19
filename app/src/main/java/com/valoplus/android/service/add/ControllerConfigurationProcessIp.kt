package com.valoplus.android.service.add

import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.channel.Channel
import de.valoplus.controller.Controller
import de.valoplus.converter.JsonConverter
import de.valoplus.dto.ChannelResponseDTO
import de.valoplus.dto.ControllerResponseDTO
import de.valoplus.dto.InitRequestDTO
import de.valoplus.dto.InitResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom on 14.02.16.
 */
class ControllerConfigurationProcessIp internal constructor(val onSuccess: (Controller) -> Unit, private val onError: (String) -> Unit, private val controller: Controller) : ControllerConfigurationProcess {

    private var clientId: String? = null

    fun setUrl(url: String): ControllerConfigurationProcessIp {
        this.controller.controllerIp = url
        return this
    }

    fun setKey(key: String): ControllerConfigurationProcessIp {
        this.controller.controllerKey = key
        return this
    }

    fun setName(name: String): ControllerConfigurationProcessIp {
        this.controller.controllerAlias = name
        return this
    }

    fun setClientId(clientId: String): ControllerConfigurationProcessIp {
        this.clientId = clientId
        return this
    }

    @Throws(ControllerConfigurationProcessException::class)
    override fun validate() {
        val client = RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!)

        val request = InitRequestDTO()
        request.clientId = clientId
        request.key = controller.controllerKey
        request.name = controller.controllerAlias

        client.postInit(request).enqueue(initCallback)

        // TODO Search if data exists!!!
    }

    private val initCallback = object : Callback<InitResponseDTO> {
        override fun onFailure(call: Call<InitResponseDTO>?, t: Throwable?) {
            onError(t?.message!!)
        }

        override fun onResponse(call: Call<InitResponseDTO>?, response: Response<InitResponseDTO>?) {
            val res = response!!.body()

            controller.availableChannel = res.availableChannel!!
            print(res.controllerType)


            val client = RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!)
            client.getSettings(clientId!!).enqueue(getSettingsCallback)
        }
    }

    private val getSettingsCallback = object : Callback<ControllerResponseDTO> {
        override fun onFailure(call: Call<ControllerResponseDTO>?, t: Throwable?) {
            onError(t?.message!!)
        }

        override fun onResponse(call: Call<ControllerResponseDTO>?, response: Response<ControllerResponseDTO>?) {
            val result = response?.body()!!
            controller.wlan = result.wlan

            val client = RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!)
            client.getChannel(clientId!!).enqueue(getChannelCallback)
        }
    }

    private val getChannelCallback = object : Callback<MutableList<ChannelResponseDTO>> {
        override fun onFailure(call: Call<MutableList<ChannelResponseDTO>>?, t: Throwable?) {
            onError(t?.message!!)
        }

        override fun onResponse(call: Call<MutableList<ChannelResponseDTO>>?, response: Response<MutableList<ChannelResponseDTO>>?) {
            val result = response?.body()!!
            for (actual in result) {
                val channel = JsonConverter.parseChannelType(actual)
                controller.channel.add(channel)
            }
            onSuccess(controller)
        }
    }
}
