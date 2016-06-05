import {App, Platform} from 'ionic-angular';
import {StatusBar} from 'ionic-native';
import {StartPage} from "./pages/start/start";
import {StoreService} from "./service/store.service";
import {DatastoreService} from "./service/datastore/datastore.service";
import {ColorPickerService} from "./pages/colorpicker/color-picker.service";


@App({
  template: '<ion-nav [root]="rootPage"></ion-nav>',
  config: {}, // http://ionicframework.com/docs/v2/api/config/Config/,
  providers: [StoreService, DatastoreService, ColorPickerService]
})
export class MyApp {
  rootPage: any = StartPage;

  constructor(platform: Platform, datastoreService: DatastoreService, store:StoreService) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      StatusBar.styleDefault();
      datastoreService.initDB();
      store.loadControllerFromDb();
    });
  }
}
