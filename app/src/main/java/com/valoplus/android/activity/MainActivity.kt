package com.valoplus.android.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.valoplus.android.R
import com.valoplus.android.activity.add.AddControllerActivity1
import com.valoplus.android.service.ControllerStore
import com.valoplus.android.service.ClientId
import com.valoplus.android.service.repository.ControllerRepo
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*


class MainActivity : ActionBarActivity() {

    private var adapter: BaseAdapter? = null
    private val controller = ControllerStore.controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ClientId.get(this.contentResolver)
        list_view_controller.visibility = View.GONE

        controller.clear()
        controller.addAll(ControllerRepo(this).load {
            if (adapter != null) {
                adapter!!.notifyDataSetChanged()
                list_view_controller.visibility = View.VISIBLE
                toast("All data loaded!")
            }
        })

        adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return controller.size
            }

            override fun getItem(position: Int): Any {
                return controller.get(position)
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var line: View? = convertView

                if (convertView == null)
                    line = layoutInflater.inflate(R.layout.controller_line, parent, false)

                val controller = controller[position]

                val color = line?.findViewById(R.id.controller_line_color) as TextView
                val alias = line?.findViewById(R.id.controller_line_alias) as TextView
                val details = line?.findViewById(R.id.controller_line_details) as TextView

                val active = controller.activeChannels()

                alias.text = controller.controllerAlias
                if(controller.availableChannel == -1) {
                    details.text = "Can not connect"
                    details.textColor = Color.RED
                } else if(controller.availableChannel == -2) {
                    details.text = "Device not authorized"
                    details.textColor = Color.RED
                } else {
                    details.text = "${controller.channel.size} channels, $active active"
                    details.textColor = Color.DKGRAY
                }

                if (active == 0) {
                    color.backgroundColor = resources.getColor(android.R.color.holo_red_light)
                } else if (active == controller.channel.size) {
                    color.backgroundColor = resources.getColor(android.R.color.holo_green_light)
                } else {
                    color.backgroundColor = resources.getColor(android.R.color.holo_orange_light)
                }

                return line!!
            }
        }
        list_view_controller.adapter = adapter

        list_view_controller.onItemClick { adapterView, view, i, l ->
            startActivity<ControllerDetails>("id" to i)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        if (id == R.id.action_add) {
            startActivity<AddControllerActivity1>()
        }

        return super.onOptionsItemSelected(item)
    }
}
