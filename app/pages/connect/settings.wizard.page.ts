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

    private oldName:string;

    private nav:NavController;
    private connectionService:ConnectionService;
    private store:StoreService;

    // TODO detect wlan change!


    constructor(nav:NavController, connectionService:ConnectionService, store:StoreService, params:NavParams) {
        this.nav = nav;
        this.connectionService = connectionService;
        this.store = store;

        this.device = params.data.device;
        this.connectionService.getWlan(this.device.ip).subscribe(
            wlan => this.wlan = wlan,
            this.error
        )
        this.oldName = this.device.name;
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

    public close() {
        this.device.name = this.oldName;
        this.nav.setRoot(StartPage);
    }

    private error(error:string) {
        this.nav.present(Toast.create({message: error, duration: 3000}));
    }
}