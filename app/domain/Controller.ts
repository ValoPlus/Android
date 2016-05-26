import {Channel} from "./channel/Channel";
/**
 * Created by tom on 23.05.16.
 */

export class Device {
    constructor(public name:String,
                public ip:String,
                public channel:Channel[]) {
    }
}