package com.valoplus.android.service

import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.controller.Status
import de.valoplus.converter.JsonConverter
import de.valoplus.dto.StatusResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by tom on 17.04.16.
 */
class ChannelService(
        val url: String
) {

    fun getStatus(name: String, onSuccess: (status: Status) -> Unit) {
        RetrofitClient.getValoPlusClientForUrl(url).getStatusByName(ClientId.get(), name).enqueue(object: Callback<StatusResponseDTO> {
            override fun onFailure(call: Call<StatusResponseDTO>?, t: Throwable?) {
                throw UnsupportedOperationException()
            }

            override fun onResponse(call: Call<StatusResponseDTO>?, response: Response<StatusResponseDTO>?) {
                onSuccess(JsonConverter.parseStateType(response!!.body()))
            }

        })
    }

}
