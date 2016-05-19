package com.valoplus.android.activity.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.valoplus.android.R
import de.valoplus.channel.Channel

/**
 * Created by tom on 30.03.16.
 */
class ChannelAdapter(context: Context, channels: MutableList<Channel>) : ArrayAdapter<Channel>(context, 0, channels) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var line: View? = convertView

        if (convertView == null)
            line = LayoutInflater.from(context).inflate(R.layout.channel_line, parent, false)

        val channel = getItem(position)

        val alias = line?.findViewById(R.id.channel_line_alias) as TextView
        val type = line?.findViewById(R.id.channel_line_type) as TextView
        val details = line?.findViewById(R.id.channel_line_details) as TextView

        alias.text = channel.name
        type.text = "(${channel.type})"
        details.text = channel.typedef!!.toString()

        return line!!
    }
}