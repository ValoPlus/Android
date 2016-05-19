package com.valoplus.android.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.View
import com.larswerkman.holocolorpicker.*
import com.valoplus.android.R
import com.valoplus.android.service.ChannelService
import com.valoplus.android.service.ControllerStore
import com.valoplus.android.service.ClientId
import com.valoplus.android.service.rest.RetrofitClient
import de.valoplus.channel.Channel
import de.valoplus.color.ColorStateWS2812
import de.valoplus.color.DummyState
import de.valoplus.color.StateVisitor
import de.valoplus.controller.Status
import de.valoplus.dto.RequestWrapper
import kotlinx.android.synthetic.main.channel_detail.*
import okhttp3.ResponseBody
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textResource
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for the ChannelDetail Screen to see / edit the [Status].
 * Gets the selected controller and channel id. Uses the status cached at start of the app.
 */
class ChannelDetails : ActionBarActivity() {
    lateinit var status: Status;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.channel_detail)

        val controller = ControllerStore.controller[intent.extras["id"] as Int]
        val channel = controller.channel[intent.extras["channel"] as Int]
        status = controller.status.find { it.channel == channel.name }!!

        title = "${controller.controllerAlias} - ${channel.name}"

        button_save.visibility = View.GONE

        picker.addOpacityBar(opacitybar)

        picker.setOnColorChangedListener {
            button_save.visibility = View.VISIBLE
        }

        //To get the color
        picker.color

        //to turn of showing the old color
        picker.showOldCenterColor = false;

        status.state.accept(object : StateVisitor<Unit> {
            override fun visit(state: ColorStateWS2812) {
                picker.color = Color.rgb(state.red, state.green, state.blue)
                opacitybar.opacity = state.brightness
            }

            override fun visit(state: DummyState) {
                throw UnsupportedOperationException()
            }
        })

        if(status.active) {
            button_switch_state.textResource = R.string.button_switch_state_on
        } else {
            button_switch_state.textResource = R.string.button_switch_state_off
        }


        button_switch_state.onClick {
            if (resources.getString(R.string.button_switch_state_on) == button_switch_state.text) {
                status.active = false
            } else {
                status.active = true
            }
            RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!).postStatus(RequestWrapper(ClientId.get(), status)).enqueue(callback)
        }

        button_save.onClick {
            status.state.brightness = opacitybar.opacity
            status.state.accept(object : StateVisitor<Unit> {
                override fun visit(state: ColorStateWS2812) {
                    state.red = Color.red(picker.color);
                    state.green = Color.green(picker.color);
                    state.blue = Color.blue(picker.color);
                }

                override fun visit(state: DummyState) {
                    throw UnsupportedOperationException()
                }
            })
            RetrofitClient.getValoPlusClientForUrl(controller.controllerIp!!).postStatus(RequestWrapper(ClientId.get(), status)).enqueue(callback)
        }
    }

    val callback = object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
            if(status.active) {
                button_switch_state.textResource = R.string.button_switch_state_on
            } else {
                button_switch_state.textResource = R.string.button_switch_state_off
            }
        }

        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
            toast("Cannot connect to Controller :/")
        }
    }
}
