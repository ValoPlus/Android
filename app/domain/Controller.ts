import {Channel} from "./Channel";
/**
 * Created by tom on 23.05.16.
 */

export class Device {
    constructor(public name:String,
                public channel:Channel[]) {
    }
}