package com.valoplus.android.exception

/**
 * Created by tom on 30.03.16.
 */
class ChannelNotFreeException : RuntimeException {

    constructor(channel: Int): super("The channel $channel is not free or does not exist!") {

    }

    constructor(name: String): super("The channel $name does already exist!") {

    }
}
