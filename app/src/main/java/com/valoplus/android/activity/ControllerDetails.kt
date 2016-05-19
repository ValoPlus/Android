package com.valoplus.android.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.valoplus.android.R
import com.valoplus.android.activity.add.AddControllerActivity1
import com.valoplus.android.activity.widget.ChannelAdapter
import com.valoplus.android.service.ControllerStore
import com.valoplus.android.service.repository.ControllerRepo
import de.valoplus.controller.Controller
import org.jetbrains.anko.*

/**
 * Created by tom on 26.03.16.
 */
class ControllerDetails : ActionBarActivity() {
    val ID_LIST_VIEW = 1
    lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controller = ControllerStore.controller[intent.extras.get("id") as Int]
        title = controller.controllerAlias

        verticalLayout {
            listView {
                id = ID_LIST_VIEW
            }
        }

        val listView = find<ListView>(ID_LIST_VIEW)
        listView.adapter = ChannelAdapter(this, controller.channel)

        listView.onItemClick { adapterView, view, i, l ->
            startActivity<ChannelDetails>("channel" to i, "id" to intent.extras.get("id"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_controller, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_delete) {
            ControllerRepo(this).delete(controller.controllerAlias!!)
            startActivity(intentFor<MainActivity>().clearTask().clearTop())
            return true
        }

        if (id == R.id.action_add) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}