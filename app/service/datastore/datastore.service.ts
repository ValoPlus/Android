import {Injectable} from "@angular/core";
import {Connection} from "../../domain/Connection";
//noinspection TypeScriptUnresolvedFunction
/**
 * Created by tom on 28.05.16.
 */

let PouchDB = require('pouchdb');

@Injectable()
export class DatastoreService {
    private _dbController;
    private _dbLastConnection;

    initDB() {
        this._dbController = new PouchDB('controller', {adapter: 'websql'});
        this._dbLastConnection = new PouchDB('lastConnection', {adapter: 'websql'});
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
}