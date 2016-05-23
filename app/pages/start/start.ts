import {Page, NavController, Modal} from "ionic-angular/index";
import {InitWizzardPage} from "../connect/init.wizard.page";
import {StoreService} from "../../service/store.service";
import {ControllerPage} from "../controller/controller.page";
/**
 * Created by tom on 22.05.16.
 */

@Page({
    templateUrl: 'build/pages/start/start.html',
})
export class StartPage {
    constructor(private nav:NavController, private store:StoreService) {

    }

    clickAddDevice() {
        let modal = Modal.create(InitWizzardPage);
        this.nav.present(modal);
    }

    clickDevice(device) {
        this.nav.push(ControllerPage, {device: device});
    }

    clickDelete(index:number) {
        this.store.controller.splice(index, 1);
    }
}