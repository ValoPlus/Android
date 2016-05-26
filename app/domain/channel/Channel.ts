import {ChannelTypes} from "./ChannelTypes";
/**
 * Created by tom on 23.05.16.
 */

export class Channel {
    constructor(public name:String = "", 
                public type:string = ChannelTypes[ChannelTypes.WS2812],
                public typedef:any = {}) {
    }
}
