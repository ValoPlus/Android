package com.valoplus.android.activity.add

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.View
import android.widget.BaseAdapter
import android.widget.ListView
import com.valoplus.android.R
import com.valoplus.android.activity.widget.ChannelAdapter
import com.valoplus.android.service.add.ControllerConfigurationProcessFassade
import kotlinx.android.synthetic.main.activity_add_controller2.*

class AddControllerActivity2 : ActionBarActivity() {

    private var listView: ListView? = null
    private var adapter: BaseAdapter? = null

    private val channels = ControllerConfigurationProcessFassade.controller.channel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_controller2)
        setTitle(R.string.title_add_controller_2)

        button_add.setOnClickListener { this.addButtonClicked(it) }
        button_next.setOnClickListener { this.nextButtonClicked(it) }

        controller_description.text = "Connected to a ${ControllerConfigurationProcessFassade.controller.type}-controller with ${ControllerConfigurationProcessFassade.controller.availableChannel} channels."

        adapter = ChannelAdapter(this, channels);
        list_view_channels.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }

    private fun addButtonClicked(v: View) {
        val intend = Intent(this, AddControllerActivityChannelDetail::class.java)
        startActivity(intend)
    }

    private fun nextButtonClicked(v: View) {
        // TODO ist Kanal eingegeben?
        val intend = Intent(this, AddControllerActivity3::class.java)
        startActivity(intend)
    }
}
