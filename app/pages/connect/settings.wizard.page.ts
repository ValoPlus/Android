import {ConnectionService} from "../../service/connection/connection.service";
import {Page, ViewController, NavParams, NavController} from "ionic-angular/index";
import {StartPage} from "../start/start";
import {Wlan} from "../../domain/Wlan";

@Page({
    templateUrl: 'build/pages/connect/settings.wizard.page.html',
    providers: [ConnectionService]
})
export class SettingsWizardPage {
    private alias:String = "";
    private wlan:Wlan = new Wlan();

    constructor(private nav:NavController,
                private connectionService:ConnectionService) {
    }

    save() {
        
    }

    close() {
        this.nav.setRoot(StartPage);
    }
}