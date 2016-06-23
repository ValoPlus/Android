import { Page, NavController, Modal } from "ionic-angular/index";
import { InitWizzardPage } from "../connect/init.wizard.page";
import { StoreService } from "../../service/store.service";
import { ControllerPage } from "../controller/controller.page";
import { Component } from "@angular/core";
import { Device } from "../../domain/Controller";
import { TranslatePipe } from 'ng2-translate/ng2-translate';
import { VpTranslateComponent } from '../global/translate.component';
/**
 * Created by tom on 22.05.16.
 */

@Component({
    templateUrl: 'build/pages/start/start.html',
})
export class StartPage {
    private nav:NavController;
    private store:StoreService;

    constructor(nav:NavController, store:StoreService) {
        this.nav = nav;
        this.store = store;
    }

    clickAddDevice():void {
        let modal = Modal.create(InitWizzardPage);
        this.nav.present(modal);
    }

    clickDevice(device:Device) {
        this.nav.push(ControllerPage, { device: device });
    }

    getActive(device:Device):number {
        return device.channel.map(actual => actual.state.active).filter(actual => actual).length;
    }

    clickDelete(index:number) {
        this.store.remove(index);
    }
}
