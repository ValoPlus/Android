package com.valoplus.android.service.rest

import de.valoplus.channel.Channel
import de.valoplus.controller.Status
import de.valoplus.dto.*
import de.valoplus.wlan.Wlan
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * The definition of the access API of the controller.
 * More details can be found here: [API-Documentation](https://tc.valoplus.de/repository/download/LedHub_Testserver/.lastSuccessful/api-guide.html)
 */
interface ValoPlusRestClient {
    @GET("/api/settings")
    fun getSettings(@Query("clientId") clientId: String): Call<ControllerResponseDTO>

    @POST("/api/settings/wlan")
    fun postSettings(@Body body: Wlan): Call<ResponseBody>

    @POST("/api/init")
    fun postInit(@Body init: InitRequestDTO): Call<InitResponseDTO>


    @GET("/api/states")
    fun getStatus(@Query("clientId") clientId: String): Call<MutableList<StatusResponseDTO>>

    @GET("/api/state")
    fun getStatusByName(@Query("clientId") clientId: String, @Query("name") name: String): Call<StatusResponseDTO>

    @POST("/api/state")
    fun postStatus(@Body body: RequestWrapper<Status>): Call<ResponseBody>


    @GET("/api/channel")
    fun getChannel(@Query("clientId") clientId: String): Call<MutableList<ChannelResponseDTO>>

    @POST("/api/channel")
    fun addChannel(@Body body: RequestWrapper<Channel>): Call<ResponseBody>

    @DELETE("/api/channel")
    fun deleteChannel(@Query("channelName") channelName: String, @Query("clientId") clientId: String): Call<ResponseBody>
}
