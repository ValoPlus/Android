import {ionicBootstrap, Platform} from 'ionic-angular';
import {StatusBar} from 'ionic-native';
import {StartPage} from "./pages/start/start";
import {StoreService} from "./service/store.service";
import {DatastoreService} from "./service/datastore/datastore.service";
import {Component} from "@angular/core";
import {DeviceService} from './service/device/device.service';
import {AccessService} from "./service/access/access.service";
import {ConnectionService} from "./service/connection/connection.service";


@Component({
    template: '<ion-nav [root]="rootPage"></ion-nav>'
})
export class MyApp {
    rootPage:any = StartPage;

    constructor(platform:Platform, datastoreService:DatastoreService, store:StoreService) {
        platform.ready().then(() => {
            // Okay, so the platform is ready and our plugins are available.
            // Here you can do any higher level native things you might need.
            StatusBar.styleDefault();
            datastoreService.initDB();
            store.loadControllerFromDb();
        });
    }
}

ionicBootstrap(MyApp, [StoreService, DatastoreService, DeviceService, AccessService, ConnectionService], {
    platforms: {
        ios: {
            statusbarPadding: true
        }
    }
});
