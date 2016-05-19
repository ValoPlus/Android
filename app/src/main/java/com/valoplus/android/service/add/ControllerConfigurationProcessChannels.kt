package com.valoplus.android.service.add


import com.valoplus.android.service.ClientId
import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.channel.Channel
import de.valoplus.controller.Controller
import de.valoplus.dto.RequestWrapper
import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom on 14.02.16.
 */
class ControllerConfigurationProcessChannels internal constructor(private val controller: Controller) : ControllerConfigurationProcess, AnkoLogger {

    fun addChannel(channel: Channel) {
        controller.channel.add(channel)

        val client = RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!)
        client.addChannel(RequestWrapper(ClientId.get(), channel)).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                debug("channel added")
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t!!.printStackTrace()
                throw t
            }
        })
    }

    @Throws(ControllerConfigurationProcessException::class)
    override fun validate() {

    }

}
