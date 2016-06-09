import {Injectable} from "@angular/core";
import {Connection} from "../../domain/Connection";
import {Device} from "../../domain/Controller";
//noinspection TypeScriptUnresolvedFunction
/**
 * Created by tom on 28.05.16.
 */

let PouchDB = require('pouchdb');

@Injectable()
export class DatastoreService {
    private _dbDevice;
    private _dbLastConnection;

    initDB() {
        this._dbDevice = new PouchDB('device', {adapter: 'websql'});
        this._dbLastConnection = new PouchDB('lastConnection', {adapter: 'websql'});
    }
    
    saveDevice(device:Device) {
        this._dbDevice.post(device);
    }
    
    removeDevice(id:string, rev:String) {
        this._dbDevice.remove(id, rev);
    }

    getLastConnection(onLoad:(Connection) => void) {
        this._dbLastConnection.get('last').then(function (doc) {
            onLoad(doc);
        }).catch();
    }

    saveLastConnection(connection:Connection) {
        this._dbLastConnection.get('last').then(doc => {
            doc.ip = connection.ip;
            doc.key = connection.key;
            return this._dbLastConnection.put(doc);
        }).catch(error => {
            this._dbLastConnection.put(connection);
        });
    }

    getAll(forEachCallback:(Device) => void) {
        return this._dbDevice.allDocs({include_docs: true}).then(result => {
            result.rows.forEach(device => {
                forEachCallback(device.doc);
            })
        });
    }
}