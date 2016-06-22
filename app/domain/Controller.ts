import {Channel} from "./channel/Channel";
import {Doc} from "./Doc";
/**
 * Created by tom on 23.05.16.
 */

export class Device extends Doc{
    public name:string;
    public ip:string;
    public channel:Channel[];

    constructor(name:string, ip:string, channel:Channel[]) {
        super();
        this.name = name;
        this.ip = ip;
        this.channel = channel;
    }
}