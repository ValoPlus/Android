import {Component, OnInit} from '@angular/core';
import {NavParams, ViewController} from "ionic-angular/index";
import {Channel} from "../../../domain/channel/Channel";

@Component({
    templateUrl: 'build/pages/controller/channel/colorpicker.page.html'
})
export class ColorpickerPage {
    channel:Channel;

    constructor(params:NavParams, private viewCtrl:ViewController) {
        this.channel = params.data.channel;
    }

    save() {
        this.viewCtrl.dismiss();
    }

    getColor():String {
        return "#" + this.componentToHex(this.channel.state.color.r) + this.componentToHex(this.channel.state.color.g) + this.componentToHex(this.channel.state.color.b);
    }

    private componentToHex(c) {
        var hex = c.toString(16);
        return hex.length == 1 ? "0" + hex : hex;
    }
}