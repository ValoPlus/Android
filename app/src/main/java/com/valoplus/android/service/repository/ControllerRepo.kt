package com.valoplus.android.service.repository

import android.content.Context
import android.util.Log
import com.valoplus.android.service.ClientId
import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.color.DummyState
import de.valoplus.controller.Controller
import de.valoplus.controller.Status
import de.valoplus.converter.JsonConverter
import de.valoplus.dto.ChannelResponseDTO
import de.valoplus.dto.StatusResponseDTO
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.*
import org.jetbrains.anko.error
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by tom on 31.03.16.
 */
class ControllerRepo(val context: Context) : AnkoLogger {

    fun save(controller: Controller) {
        context.database.use {
            insert(Names.TBL.rep,
                    Names.IP.rep to controller.controllerIp!!,
                    Names.ALIAS.rep to controller.controllerAlias!!,
                    Names.KEY.rep to controller.controllerKey!!,
                    Names.AVAILABLE_CHANNEL.rep to controller.availableChannel,
                    Names.TYPE.rep to controller.type)
        }
    }

    fun load(callback: () -> Unit): List<Controller> {
        var list: List<Controller> = ArrayList()

        context.database.use {
            select(Names.TBL.rep).exec {
                list = parseList(object : MapRowParser<Controller> {
                    override fun parseRow(columns: Map<String, Any>): Controller {
                        return Controller(columns.forKey(Names.AVAILABLE_CHANNEL.rep, Long::class),
                                columns.forKey(Names.TYPE.rep, String::class),
                                columns.forKey(Names.ALIAS.rep, String::class),
                                columns.forKey(Names.KEY.rep, String::class),
                                columns.forKey(Names.IP.rep, String::class))
                    }
                })

                var counter = list.size * 2

                list.forEach {
                    RetrofitClient.getValoPlusClientForUrl(it.controllerIp!!)
                            .getChannel(ClientId.get(context.contentResolver))
                            .enqueue(object : Callback<MutableList<ChannelResponseDTO>> {
                                override fun onFailure(call: Call<MutableList<ChannelResponseDTO>>?, t: Throwable?) {
                                    it.availableChannel = -1
                                    counter--
                                    if (counter == 0)
                                        callback.invoke()
                                }

                                override fun onResponse(call: Call<MutableList<ChannelResponseDTO>>?, response: Response<MutableList<ChannelResponseDTO>>?) {
                                    if(response?.code() == 401) {
                                        it.availableChannel = -2
                                    }
                                    val result = response?.body()!!
                                    for (actual in result) {
                                        val channel = JsonConverter.parseChannelType(actual)
                                        it.channel.add(channel)
                                    }
                                    counter--
                                    if (counter == 0)
                                        callback.invoke()
                                }
                            })

                    RetrofitClient.getValoPlusClientForUrl(it.controllerIp!!)
                            .getStatus(ClientId.get())
                            .enqueue(object : Callback<MutableList<StatusResponseDTO>> {
                                override fun onResponse(call: Call<MutableList<StatusResponseDTO>>?, response: Response<MutableList<StatusResponseDTO>>?) {
                                    error(response?.headers())
                                    error(response?.message())
                                    error(response?.body())
                                    error(response?.code())
                                    if(response?.code() == 401) {
                                        it.availableChannel = -2
                                    }
                                    response?.body()?.forEach { actual ->
                                        it.status.add(JsonConverter.parseStateType(actual))
                                    }
                                    counter--
                                    if (counter == 0)
                                        callback.invoke()
                                }

                                override fun onFailure(call: Call<MutableList<StatusResponseDTO>>?, t: Throwable?) {
                                    it.availableChannel = -1
                                    counter--
                                    if (counter == 0)
                                        callback.invoke()
                                }
                            })
                }
            }
        }

        return list
    }

    fun delete(name: String) {
        context.database.use {
            delete(Names.TBL.rep, "${Names.ALIAS.rep} = {name}", "name" to name)
        }
    }

    enum class Names(val rep: String) {
        TBL("controller"),

        IP("ip"),
        ALIAS("alias"),
        KEY("key"),
        AVAILABLE_CHANNEL("available_channel"),
        TYPE("type")
    }
}