import {Page, ViewController, NavController, Toast, Modal, NavParams} from "ionic-angular/index";
import {ConnectionService} from "../../service/connection/connection.service";
import {RegistrationRequest, RegistrationResponse} from "../../domain/Registration";
import {StoreService} from "../../service/store.service";
import {Device} from "../../domain/Controller";
import {SettingsWizardPage} from "./settings.wizard.page";
import {DatastoreService} from "../../service/datastore/datastore.service";
import {Connection} from "../../domain/Connection";
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
    public ip:String = 'valoplus.de';

    constructor(private viewCtrl:ViewController,
                private connectionService:ConnectionService,
                private nav:NavController,
                private store:StoreService,
                private datastoreService: DatastoreService) {
        this.registrationRequest = new RegistrationRequest();
        datastoreService.getLastConnection(last => {
            if(last != null) {
                this.ip = last.ip;
                this.registrationRequest.key = last.key;
            }
        });
    }

    clickConnect() {
        this.registrationRequest.clientId = 'test';
        this.connectionService.requestRegistration(this.registrationRequest, this.ip).subscribe(
            result => this.registrationResponse = result,
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        );
        this.datastoreService.saveLastConnection(new Connection(this.registrationRequest.key, this.ip));
    }

    clickAdd() {
        this.connectionService.getChannel(this.ip).subscribe(
            result => {
                this.store.add(new Device(this.registrationResponse.controllerAlias, this.ip, result));
                this.close();
            },
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        )
    }

    clickConfigure() {
        this.connectionService.getChannel(this.ip).subscribe(
            result => {
                let modal = Modal.create(SettingsWizardPage, {device: new Device(this.registrationResponse.controllerAlias, this.ip, result)});
                this.nav.present(modal, {animate: false});
            },
            error => this.nav.present(Toast.create({message: error, duration: 3000}))
        );
    }

    close() {
        this.viewCtrl.dismiss();
    }
}