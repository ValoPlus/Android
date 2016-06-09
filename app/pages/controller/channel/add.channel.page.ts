import {Page, NavParams, ViewController, NavController, Toast} from "ionic-angular/index";
import {Device} from "../../../domain/Controller";
import {ConnectionService} from "../../../service/connection/connection.service";
import {Channel} from "../../../domain/channel/Channel";
import {ChannelTypes} from "../../../domain/channel/ChannelTypes";
import {Component} from "@angular/core";

@Component({
    templateUrl: 'build/pages/controller/channel/add.channel.page.html',
    providers: [ConnectionService]
})
export class AddChannelPage {
    private device:Device;
    private channel:Channel; 
    private channelTypes:any = ChannelTypes;
    
    constructor(params: NavParams, private viewCtrl:ViewController, private nav:NavController, private connectionService:ConnectionService) {
        this.device = params.data.device;
        this.channel = new Channel();
    }
    
    save() {
        this.channel.type = this.channelTypes[this.channel.type];
        this.connectionService.saveChannel(this.device.ip, this.channel).subscribe(
            result => {
                this.device.channel.push(this.channel);
                this.viewCtrl.dismiss();
            },
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        );
    }
}