/**
 * Created by tom on 02.06.16.
 */

import {Page, NavParams, NavController, Modal, Popover} from "ionic-angular/index";
import {Device} from "../../../domain/Controller";
import {Channel} from "../../../domain/channel/Channel";
import {Component} from "@angular/core";
import {ColorpickerPage} from "./colorpicker.page";
import {State} from "../../../domain/channel/State";
import {DevicePopoverComponent} from "./channel.popover";

@Component({
    templateUrl: 'build/pages/controller/channel/detail.channel.page.html',
})
export class DetailChannelPage {
    private device:Device;
    channel:Channel;

    constructor(params:NavParams, private nav:NavController) {
        this.device = params.data.device;
        this.channel = params.data.channel;
    }
    
    clickColor() {
        let modal = Modal.create(ColorpickerPage, {channel: this.channel});
        this.nav.present(modal);
    }

    getColor():String {
        return "#" + this.componentToHex(this.channel.state.color.r) + this.componentToHex(this.channel.state.color.g) + this.componentToHex(this.channel.state.color.b);
    }

    toggle() {
        this.channel.state.active = !this.channel.state.active;
    }
    
    private componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }

    presentPopover(myEvent) {
        let popover = Popover.create(DevicePopoverComponent);
        this.nav.present(popover, {
            ev: myEvent
        });
    }
}