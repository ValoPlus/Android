import {Page, NavParams, NavController, Toast} from "ionic-angular/index";
import {Device} from "../../domain/Controller";
import {AddChannelPage} from "./channel/add.channel.page";
import {Channel} from "../../domain/channel/Channel";
/**
 * Created by tom on 23.05.16.
 */

@Page({
    templateUrl: 'build/pages/controller/controller.page.html'
})
export class ControllerPage {
    private device:Device;

    constructor(params: NavParams, private nav:NavController) {
        this.device = params.data.device;
    }

    clickAddChannel() {
        this.nav.push(AddChannelPage, {device: this.device});
    }

    clickDelete(channel:Channel) {
        this.nav.present(Toast.create({message: 'TODO', duration: 3000}))
    }
}
