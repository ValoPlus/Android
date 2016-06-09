import {ChannelTypes} from "./ChannelTypes";
import {State} from "./State";
/**
 * Created by tom on 23.05.16.
 */

export class Channel {
    public state:State = new State();

    constructor(public name:string = "", 
                public type:string = ChannelTypes[ChannelTypes.WS2812],
                public typedef:any = {}) {
    }
}
