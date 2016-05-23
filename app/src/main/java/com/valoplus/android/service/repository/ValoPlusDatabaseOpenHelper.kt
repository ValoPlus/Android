package com.valoplus.android.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.valoplus.android.exception.RepositoryException
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

/**
 * Created by tom on 31.03.16.
 */
class ValoPlusDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {

    companion object {
        private var instance: ValoPlusDatabaseOpenHelper? = null

        fun getInstance(ctx: Context): ValoPlusDatabaseOpenHelper {
            synchronized(this) {
                if (instance == null) {
                    instance = ValoPlusDatabaseOpenHelper(ctx.applicationContext)
                }
                return instance!!
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(ControllerRepo.Names.TBL.rep, true,
                ControllerRepo.Names.IP.rep to TEXT + PRIMARY_KEY + UNIQUE,
                ControllerRepo.Names.ALIAS.rep to TEXT + NOT_NULL + UNIQUE,
                "key" to TEXT + NOT_NULL,
                "available_channel" to INTEGER + NOT_NULL,
                "type" to TEXT + NOT_NULL)

        db.createTable(LastControllerRepo.Names.TBL.rep, true,
                LastControllerRepo.Names.IP.rep to TEXT + PRIMARY_KEY + UNIQUE,
                LastControllerRepo.Names.KEY.rep to TEXT + PRIMARY_KEY + UNIQUE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
    }
}

// Access property for Context
val Context.database: ValoPlusDatabaseOpenHelper
    get() = ValoPlusDatabaseOpenHelper.getInstance(applicationContext)

inline fun <reified T : Any> genericParser(): RowParser<T> {
    val clazz = T::class.java
    val constructors = clazz.declaredConstructors.filter {
        val types = it.parameterTypes
        !it.isVarArgs && Modifier.isPublic(it.modifiers) &&
                types != null && types.size > 0
    }
    if (constructors.isEmpty())
        throw AnkoException(
                "Can't initialize object parser for ${clazz.canonicalName}, no acceptable constructors found")

    val c = constructors[0]

    for (type in c.parameterTypes) {
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
        val valid = when (type) {
            Long::class.java, java.lang.Long::class.java -> true
            Double::class.java, java.lang.Double::class.java -> true
            String::class.java, ByteArray::class.java -> true
            else -> false
        }
        if (!valid)
            throw AnkoException(
                    "Invalid argument type ${type.canonicalName} in ${clazz.canonicalName} constructor." +
                            "Supported types are: Long, Double, String, Array<Byte>.")
    }

    return object : RowParser<T> {
        override fun parseRow(columns: Array<Any>): T {
            return c.newInstance(columns) as T
        }
    }
}

public fun <K, V, R : Any> Map<K, V>.forKey(key: K, type: KClass<R>): R {
    val result = get(key)
    if (result != null)
        return result as R
    throw RepositoryException("No value for NOT_NULL key $key")
}