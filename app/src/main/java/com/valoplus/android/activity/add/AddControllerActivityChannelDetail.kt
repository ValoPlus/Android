package com.valoplus.android.activity.add

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.valoplus.android.R
import com.valoplus.android.activity.basic.AbstractActivity
import com.valoplus.android.basic.Utils.getInput
import com.valoplus.android.basic.Utils.getInputAsInt
import com.valoplus.android.exception.ChannelNotFreeException
import com.valoplus.android.service.add.ControllerConfigurationProcessFassade
import de.valoplus.channel.*
import kotlinx.android.synthetic.main.activity_add_controller_activity_channel_detail.*
import java.util.*

class AddControllerActivityChannelDetail : AbstractActivity() {
    override fun initUiElements() {
        throw UnsupportedOperationException()
    }

    private val channelTypes = ChannelTypes.values()
    private val layouts = HashMap<ChannelTypes, LinearLayout>()

    private val controller = ControllerConfigurationProcessFassade.controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_controller_activity_channel_detail)
        setTitle(R.string.title_add_controller_channel_detail)

        layouts.put(ChannelTypes.WS2812, layout_channel_ws2812)
        layouts.put(ChannelTypes.LED_STRIP, layout_channel_single_color)
        layouts.put(ChannelTypes.LED_STRIP_RGB, layout_channel_rgb)

        button_cancel.setOnClickListener { this.cancel(it) };
        button_save.setOnClickListener { this.save(it) };


        spinner_controller_type.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return channelTypes.size
            }

            override fun getItem(position: Int): Any {
                return channelTypes[position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var line: View? = convertView
                if (line == null) {
                    line = layoutInflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)
                }

                val name = line!!.findViewById(android.R.id.text1) as TextView
                name.text = channelTypes[position].rep

                return line
            }
        }

        spinner_controller_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                for (actual in layouts.values) {
                    actual.visibility = View.GONE
                }
                layouts[channelTypes[position]]!!.visibility = View.VISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun save(v: View) {
        error.visibility = View.GONE
        val type = spinner_controller_type.selectedItem as ChannelTypes
        var channelType: ChannelType? = null
        try {
            when (type) {
                ChannelTypes.WS2812 -> channelType = wS2812()
                ChannelTypes.LED_STRIP -> channelType = ledStrip()
                ChannelTypes.LED_STRIP_RGB -> channelType = ledStripRgb()
            }
            if (!controller.isNameFree(getInput(input_channel_name))) {
                throw ChannelNotFreeException(getInput(input_channel_name))
            }
            val channel = Channel(getInput(input_channel_name), type.name, channelType!!)
            ControllerConfigurationProcessFassade.step2().addChannel(channel)
            finish()
        } catch(ex: ChannelNotFreeException) {
            error.visibility = View.VISIBLE
            error.text = ex.message
        }
    }

    private fun cancel(v: View) {
        this.finish()
    }

    @Throws(ChannelNotFreeException::class)
    private fun wS2812(): ChannelType {
        val channelId = getInputAsInt(input_channel_ws1812_id)
        checkChannel(channelId)
        val ledCount = getInputAsInt(input_channel_ws1812_led_count)
        return ChannelTypeWS2812(channelId, ledCount)
    }

    @Throws(ChannelNotFreeException::class)
    private fun ledStrip(): ChannelType {
        val channelId = getInputAsInt(input_channel_single_color_id)
        checkChannel(channelId)
        return ChannelTypeSingleColor(channelId)
    }

    @Throws(ChannelNotFreeException::class)
    private fun ledStripRgb(): ChannelType {
        val r = getInputAsInt(input_channel_rgb_r)
        checkChannel(r)
        val g = getInputAsInt(input_channel_rgb_g)
        checkChannel(g)
        val b = getInputAsInt(input_channel_rgb_b)
        checkChannel(b)
        return ChannelTypeRGB(r, g, b)
    }

    @Throws(ChannelNotFreeException::class)
    private fun checkChannel(id: Int) {
        if (!controller.isChannelFree(id)) {
            throw ChannelNotFreeException(id)
        }
    }
}
