package com.valoplus.android.activity.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.valoplus.android.R
import com.valoplus.android.activity.MainActivity
import com.valoplus.android.activity.basic.AbstractActivity
import com.valoplus.android.basic.Utils
import com.valoplus.android.service.ClientId
import com.valoplus.android.service.add.ControllerConfigurationProcessFassade
import com.valoplus.android.service.repository.ControllerRepo
import kotlinx.android.synthetic.main.activity_add_controller3.*

class AddControllerActivity3 : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_controller3)

        button_cancel.setOnClickListener { this.backToStart(it) }
        button_save.setOnClickListener { this.save(it) }
    }

    override fun initUiElements() {
        throw UnsupportedOperationException()
    }

    private fun backToStart(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun showError(e: String) {
        super.showError(e)
        button_save.isEnabled = true
        button_cancel.isEnabled = true
    }

    private fun save(v: View) {
        button_save.isEnabled = false
        button_cancel.isEnabled = false

        val clientId = ClientId.get(this.contentResolver)

        ControllerConfigurationProcessFassade
                .step3({
                    ControllerRepo(this).save(it)
                    backToStart(v)
                }, { showError(it) })
                .key(Utils.getInput(input_wlan_key))
                .ssid(Utils.getInput(input_wlan_ssid))
                .clientId(clientId)
                .validate();
    }
}
