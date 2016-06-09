import {Channel} from "./channel/Channel";
import {DBExtension} from "./DBExtension";
/**
 * Created by tom on 23.05.16.
 */

export class Device extends DBExtension{
    public name:string;
    public ip:string;
    public channel:Channel[];

    constructor(name?:string, ip?:string, channel?:Channel[]) {
        super();
        this.name = name;
        this.ip = ip;
        this.channel = channel;
    }
}