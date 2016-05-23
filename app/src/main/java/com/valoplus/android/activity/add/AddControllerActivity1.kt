package com.valoplus.android.activity.add

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.valoplus.android.R
import com.valoplus.android.activity.basic.AbstractActivity
import com.valoplus.android.basic.Utils
import com.valoplus.android.service.add.ControllerConfigurationProcessFassade
import com.valoplus.android.service.repository.LastControllerRepo
import kotlinx.android.synthetic.main.activity_add_controller1.*

class AddControllerActivity1 : AbstractActivity() {
    val lastController = LastControllerRepo(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_controller1)
        setTitle(R.string.title_add_controller_1)

        initDummyData()

        button_cancel.setOnClickListener { this.finish() }

        button_next.setOnClickListener { v ->
            button_next.isEnabled = false
            val clientId = Settings.Secure.getString(this.contentResolver,
                    Settings.Secure.ANDROID_ID)

            lastController.save(Utils.getInput(input_controller_ip), Utils.getInput(input_controller_key))

            ControllerConfigurationProcessFassade
                    .step1({ this.doIntend() }, { this.showError(it) })
                    .setUrl(Utils.getInput(input_controller_ip))
                    .setName(Utils.getInput(input_controller_name))
                    .setKey(Utils.getInput(input_controller_key))
                    .setClientId(clientId)
                    .validate()
        }
    }

    override fun initUiElements() {
    }

    private fun initDummyData() {
        val last = lastController.get();

        input_controller_ip.setText(last.first)
        input_controller_key.setText(last.second)

        input_controller_name.setText("controller1")
    }

    private fun doIntend() {
        val intent = Intent(this, AddControllerActivity2::class.java)
        button_next.isEnabled = true
        startActivity(intent)
    }

    override fun showError(e: String) {
        super.showError(e)
        button_next.isEnabled = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
