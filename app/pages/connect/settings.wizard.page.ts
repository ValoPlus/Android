import {ConnectionService} from "../../service/connection/connection.service";
import {Page, NavParams, NavController, Toast} from "ionic-angular/index";
import {StartPage} from "../start/start";
import {Wlan} from "../../domain/Wlan";
import {Device} from "../../domain/Controller";
import {StoreService} from "../../service/store.service";
import {Component} from "@angular/core";

@Component({
    templateUrl: 'build/pages/connect/settings.wizard.page.html',
    providers: [ConnectionService]
})
export class SettingsWizardPage {
    private wlan:Wlan = new Wlan();
    private device:Device;

    // TODO detect wlan change!

    constructor(public nav:NavController,
                private connectionService:ConnectionService,
                private store:StoreService,
                params:NavParams) {
        this.device = params.data.device;
        this.connectionService.getWlan(this.device.ip).subscribe(
            wlan => this.wlan = wlan,
            this.error
        )
    }

    save() {
        this.connectionService.saveAlias(this.device.name, this.device.ip).subscribe(
            () => {
                this.connectionService.saveWlan(this.wlan, this.device.ip).subscribe(() => {
                    this.store.update(this.device);
                    this.nav.setRoot(StartPage);
                }, this.error);
            },
            this.error
        );
    }

    private error(error:string) {
        this.nav.present(Toast.create({message: error, duration: 3000}));
    }

    close() {
        this.nav.setRoot(StartPage);
    }
}