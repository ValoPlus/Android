import { Injectable } from '@angular/core';
import { Channel } from '../../domain/channel/Channel';
import {ConnectionService} from "../connection/connection.service";
import {Device} from "../../domain/Controller";
import {DatastoreService} from "../datastore/datastore.service";
import {Observable} from "rxjs/Rx";

/**
 * Service to access domain Objects. This service should help to get the local storage synced with the database and the device.
 * Created by tom on 19.06.16.
 */
@Injectable()
export class AccessService {
    private con:ConnectionService;
    private store:DatastoreService;

    constructor(con:ConnectionService, store:DatastoreService) {
        this.con = con;
        this.store = store;
    }

    /**
     * Saves the new Channel to the given device.
     * @param channel
     * @param device
     */
    saveChannelInDevice(channel:Channel, device:Device):Observable<void> {
        return this.con.saveChannel(device.ip, channel).map(
            res => device.channel.push(channel)
        ).map(
            res => this.store.updateDevice(device)
        )
    }

    /**
     * This method should called after the channel is changed.
     * @param channel
     * @param device
     */
    updateChannelInDevice(channel:Channel, device:Device) {

    }

    /**
     * Deletes the given channel in the device.
     * @param channel
     * @param device
     * @returns {Observable<R>} The processable Observable.
     */
    deleteChannelInDevice(channel:Channel, device:Device):Observable<void> {
        return this.con.deleteChannel(device.ip, channel.name).map(
            () => device.channel.splice(device.channel.indexOf(channel) ,1)
        ).map(
            () => this.store.updateDevice(device)
        )
    }
}
