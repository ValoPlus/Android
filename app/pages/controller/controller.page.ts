import {Page, NavParams, NavController, Toast, Popover, Modal} from "ionic-angular/index";
import {Device} from "../../domain/Controller";
import {AddChannelPage} from "./channel/add.channel.page";
import {Channel} from "../../domain/channel/Channel";
import {DetailChannelPage} from "./channel/detail.channel.page";
import {Component} from "@angular/core";
import {DevicePopoverComponent} from "./channel/channel.popover";
import {PopoverCallback} from "../../util/PopoverCallback";
import {SettingsWizardPage} from "../connect/settings.wizard.page";
import {StoreService} from "../../service/store.service";
import {StartPage} from "../start/start";
import {VpColorpickerComponent} from "./channel/colorpicker.component";
import { VpTranslateComponent } from '../global/translate.component';
/**
 * Created by tom on 23.05.16.
 */

@Component({
    templateUrl: 'build/pages/controller/controller.page.html',
    directives: [VpColorpickerComponent]
})
export class ControllerPage {
    private device:Device;

    constructor(params:NavParams, private nav:NavController, private store:StoreService) {
        this.device = params.data.device;
    }

    toggleCp(event, cp:VpColorpickerComponent) {
        if (event.target.className != 'button-inner')
            cp.toggle();
    }

    clickAddChannel() {
        this.nav.push(AddChannelPage, {device: this.device});
    }

    clickChannel(channel:Channel) {
        this.nav.push(DetailChannelPage, {device: this.device, channel: channel})
    }

    clickDelete(channel:Channel) {
        this.nav.present(Toast.create({message: 'TODO', duration: 3000}))
    }

    toggle(channel:Channel) {
        channel.state.active = !channel.state.active;
    }

    presentPopover(myEvent) {
        let popover = Popover.create(DevicePopoverComponent, {
            callback: <PopoverCallback> {
                onClickDelete: () => {
                    this.store.removeByDoc(this.device);
                    this.nav.setRoot(StartPage);
                },
                onClickEdit: () => {
                    let modal = Modal.create(SettingsWizardPage, {device: this.device});
                    this.nav.present(modal);
                }
            }
        });
        this.nav.present(popover, {
            ev: myEvent
        });
    }
}
