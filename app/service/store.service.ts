import {Device} from "../domain/Controller";
import {Injectable} from "@angular/core";
import {DatastoreService} from "./datastore/datastore.service";
import {State} from "../domain/channel/State";
import {Doc} from "../domain/Doc";
/**
 * Service to store all devices and the last connection.
 * Should be used for every DB access!
 */
@Injectable()
export class StoreService {
    public controller:Array<Device>;

    constructor(private datastore:DatastoreService) {
        this.controller = [];
    }

    /**
     * Saves a new Device. The Name must be unique.
     * @param device
     * @throws Error if a Device with the same Name exists.
     */
    add(device:Device) {
        const exist = this.controller.find(actual => actual.name == device.name);
        if(exist != null) {
            throw new Error(`A Device with the name ${exist.name} already exists.`);
        }
        device.channel.forEach(channel => {
            channel.state = new State();
        });
        
        this.datastore.saveDevice(device);
        this.controller.push(device);
    }

    /**
     * Updates the Device. A Device with the same Name should already exist.
     * @param device
     */
    update(device:Device) {
        this.datastore.updateDevice(device);
    }

    remove(index:number) {
        const deletedDevice = this.controller.splice(index, 1);
        this.datastore.removeDevice(deletedDevice[0]._id, deletedDevice[0]._rev);
    }

    removeByDoc(doc:Doc) {
        this.controller = this.controller.filter(actual => actual._id != doc._id);
        this.datastore.removeDevice(doc._id, doc._rev);
    }

    loadControllerFromDb() {
        this.datastore.getAll(device => this.controller.push(device));
        this.controller.forEach(device => {
            device.channel.forEach(channel => {
                channel.state = new State();
            });
        });
    }
}
