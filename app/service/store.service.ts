import {Device} from "../domain/Controller";
import {Injectable} from "@angular/core";
import {DatastoreService} from "./datastore/datastore.service";
/**
 * Created by tom on 23.05.16.
 */
@Injectable()
export class StoreService {
    private controller:Array<Device>;

    constructor(private datastore:DatastoreService) {
        this.controller = [];
    }
    
    add(device:Device) {
        // TODO Fall behandeln, dass Controller bereits existiert
        this.datastore.saveDevice(device);
        this.controller.push(device);
    }

    remove(index:number) {
        const deletedDevice = this.controller.splice(index, 1);
        this.datastore.removeDevice(deletedDevice[0]._id, deletedDevice[0]._rev);
    }

    loadControllerFromDb() {
        this.datastore.getAll(device => this.controller.push(device));
    }
}