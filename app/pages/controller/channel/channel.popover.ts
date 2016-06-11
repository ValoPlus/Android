/**
 * Created by tom on 08.06.16.
 */

import {Component, OnInit} from '@angular/core';
import {ViewController, NavParams} from "ionic-angular/index";
import {PopoverCallback} from "../../../util/PopoverCallback";

@Component({
    template: `
    <ion-list>
      <button ion-item (click)="onEdit()">Edit</button>
      <button ion-item (click)="onDelete()">Delete</button>
    </ion-list>
  `
})
export class DevicePopoverComponent {
    private callback:PopoverCallback;

    constructor(private viewCtrl:ViewController, params:NavParams) {
        this.callback = params.data.callback;
    }

    onEdit() {
        this.callback.onClickEdit();
    }

    onDelete() {
        this.callback.onClickDelete();
    }

    close() {
        this.viewCtrl.dismiss();
    }
}
