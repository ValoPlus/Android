import {Page, NavParams} from "ionic-angular/index";
import {Device} from "../../domain/Controller";
/**
 * Created by tom on 23.05.16.
 */

@Page({
    templateUrl: 'build/pages/controller/controller.page.html'
})
export class ControllerPage {
    private device:Device;

    constructor(params: NavParams) {
        this.device = params.data.device;
    }
}
