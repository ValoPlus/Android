package com.valoplus.android.service.repository

import android.content.Context
import de.valoplus.controller.Controller
import org.jetbrains.anko.db.*

/**
 * Created by tom on 22.05.16.
 */
class LastControllerRepo(val context: Context) {
    fun save(ip: String, key: String) {
        context.database.use {
            delete(Names.TBL.rep, null, null)

            insert(Names.TBL.rep,
                    Names.IP.rep to ip,
                    Names.KEY.rep to key)
        }
    }

    fun get(): Pair<String, String> {
        var result = Pair("", "");
        context.database.use {
            select(Names.TBL.rep).exec {
                result = parseList(object : MapRowParser<Pair<String, String>> {
                    override fun parseRow(columns: Map<String, Any>): Pair<String, String> {
                        return Pair(columns.forKey(Names.IP.rep, String::class), columns.forKey(Names.KEY.rep, String::class))
                    }
                }).getOrElse(0, { Pair("", "") })
            }
        }
        return result;
    }

    enum class Names(val rep: String) {
        TBL("lastcontroller"),

        IP("ip"),
        KEY("key"),
    }
}
