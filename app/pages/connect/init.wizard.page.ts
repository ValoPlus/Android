import {ViewController, NavController, Toast, Modal, NavParams} from "ionic-angular/index";
import {ConnectionService} from "../../service/connection/connection.service";
import {RegistrationRequest, RegistrationResponse} from "../../domain/Registration";
import {StoreService} from "../../service/store.service";
import {Device} from "../../domain/Controller";
import {SettingsWizardPage} from "./settings.wizard.page";
import {DatastoreService} from "../../service/datastore/datastore.service";
import {Connection} from "../../domain/Connection";
import {ErrorHandler} from "../abstract/ErrorHandler";
import {Component} from "@angular/core";
import {DeviceService} from "../../service/device/device.service";
/**
 * Created by tom on 23.05.16.
 */

@Component({
    templateUrl: 'build/pages/connect/init.wizard.page.html',
    providers: [ConnectionService]
})
export class InitWizzardPage extends ErrorHandler {
    public registrationRequest:RegistrationRequest;
    public registrationResponse:RegistrationResponse;
    public ip:string = 'test.valoplus.de';

    private device:DeviceService;

    constructor(private viewCtrl:ViewController,
                private connectionService:ConnectionService,
                nav:NavController,
                private store:StoreService,
                private datastoreService:DatastoreService,
                device:DeviceService) {
        super(nav);
        this.device = device;
        this.registrationRequest = new RegistrationRequest();
        datastoreService.getLastConnection(last => {
            if (last != null) {
                this.ip = last.ip;
                this.registrationRequest.key = last.key;
            }
        });
    }

    clickConnect() {
        this.registrationRequest.clientId = this.device.getDeviceId();
        this.connectionService.requestRegistration(this.registrationRequest, this.ip).subscribe(
            result => this.registrationResponse = result,
            error => super.handleErrorString
        );
        this.datastoreService.saveLastConnection(new Connection(this.registrationRequest.key, this.ip));
    }

    clickAdd() {
        this.connectionService.getChannel(this.ip).subscribe(
            result => {
                try {
                    this.store.add(new Device(this.registrationResponse.controllerAlias, this.ip, result));
                    this.close();
                } catch (error) {
                    super.handleError(error);
                }
            }, super.handleErrorString);
    }

    clickConfigure() {
        this.connectionService.getChannel(this.ip).subscribe(
            result => {
                const device = new Device(this.registrationResponse.controllerAlias, this.ip, result);
                this.store.add(device);

                const modal = Modal.create(SettingsWizardPage, {device: device});
                this.nav.present(modal, {animate: false});
            },
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        );
    }

    close() {
        this.viewCtrl.dismiss();
    }
}