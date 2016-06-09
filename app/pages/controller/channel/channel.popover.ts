/**
 * Created by tom on 08.06.16.
 */

import {Component, OnInit} from '@angular/core';
import {ViewController} from "ionic-angular/index";

@Component({
    template: `
    <ion-list>
      <button ion-item (click)="close()">Edit</button>
      <button ion-item (click)="close()">Delete</button>
    </ion-list>
  `
})
export class DevicePopoverComponent {
    constructor(private viewCtrl:ViewController) {
    }

    close() {
        this.viewCtrl.dismiss();
    }
}
