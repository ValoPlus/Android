/**
 * Service to access domain Objects. This service should help to get the local storage synced with the Database and the controller.
 * Created by tom on 19.06.16.
 */

import { Injectable } from '@angular/core';
import { Channel } from '../../domain/channel/Channel';
import { Device } from 'ionic-native/dist/index';

@Injectable()
export class AccessService {

    /**
     * Saves the new Channel to the given device.
     * @param channel
     * @param device
     */
    saveChannelInDevice(channel:Channel, device:Device) {

    }
}
