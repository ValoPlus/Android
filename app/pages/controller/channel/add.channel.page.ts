import {NavParams, ViewController, NavController, Toast} from "ionic-angular/index";
import {Device} from "../../../domain/Controller";
import {ConnectionService} from "../../../service/connection/connection.service";
import {Channel} from "../../../domain/channel/Channel";
import {ChannelTypes} from "../../../domain/channel/ChannelTypes";
import {Component} from "@angular/core";
import {AccessService} from "../../../service/access/access.service";

@Component({
    templateUrl: 'build/pages/controller/channel/add.channel.page.html',
})
export class AddChannelPage {
    private device:Device;
    private channel:Channel;
    private channelTypes:any = ChannelTypes;

    private access:AccessService;

    constructor(params:NavParams, private viewCtrl:ViewController, private nav:NavController, access:AccessService) {
        this.device = params.data.device;
        this.channel = new Channel();
        this.access = access;
    }

    save() {
        this.channel.type = this.channelTypes[this.channel.type];
        this.access.saveChannelInDevice(this.channel, this.device).subscribe(
            () => this.viewCtrl.dismiss(),
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        );
    }
}