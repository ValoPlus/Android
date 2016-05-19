package com.valoplus.android.service.repository

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.test.RenamingDelegatingContext
import android.test.suitebuilder.annotation.SmallTest
import de.valoplus.controller.Controller
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by tom on 01.04.16.
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class ControllerRepoTest {

    var repo: ControllerRepo? = null
    var context: Context? = null

    @Before
    fun init() {
        context = RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().targetContext, "test_")
        repo = ControllerRepo(context!!)
    }

    @Test
    fun testSave() {
        repo!!.save(Controller(8, "", "Controller 1", "secret", "localhost"))

        assertEquals(1, repo!!.load {}.size)
        assertEquals("Controller 1", repo!!.load {}[0].controllerAlias)
    }
}