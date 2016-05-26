import {Page, ViewController, NavController, Toast, Modal, NavParams} from "ionic-angular/index";
import {ConnectionService} from "../../service/connection/connection.service";
import {RegistrationRequest, RegistrationResponse} from "../../domain/Registration";
import {StoreService} from "../../service/store.service";
import {Device} from "../../domain/Controller";
import {SettingsWizardPage} from "./settings.wizard.page";
/**
 * Created by tom on 23.05.16.
 */

@Page({
    templateUrl: 'build/pages/connect/init.wizard.page.html',
    providers: [ConnectionService]
})
export class InitWizzardPage {
    public registrationRequest:RegistrationRequest;
    public registrationResponse:RegistrationResponse;
    public ip:String = 'test.valoplus.de';

    constructor(private viewCtrl:ViewController,
                private connectionService:ConnectionService,
                private nav:NavController,
                private store:StoreService) {
        this.registrationRequest = new RegistrationRequest();
    }

    clickConnect() {
        this.registrationRequest.clientId = 'test';
        this.connectionService.requestRegistration(this.registrationRequest, this.ip).subscribe(
            result => this.registrationResponse = result,
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        )
    }

    clickAdd() {
        this.connectionService.getChannel(this.ip).subscribe(
            result => {
                this.store.controller.push(new Device(this.registrationResponse.controllerAlias, this.ip, result));
                this.close();
            },
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        )
    }

    clickConfigure() {
        let modal = Modal.create(SettingsWizardPage);
        this.nav.present(modal, {animate: false});
    }

    close() {
        this.viewCtrl.dismiss();
    }
}