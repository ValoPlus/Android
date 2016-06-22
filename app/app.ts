import { ionicBootstrap, Platform } from 'ionic-angular';
import { StatusBar } from 'ionic-native';
import { Http } from '@angular/http';
import { TRANSLATE_PROVIDERS, TranslateService, TranslateStaticLoader, TranslateLoader } from 'ng2-translate/ng2-translate';
import { StoreService } from './service/store.service';
import { DatastoreService } from './service/datastore/datastore.service';
import { StartPage } from './pages/start/start';
import { provide, Component } from '@angular/core';
import { VALO_PLUS_PROVIDERS } from './service/services';

@Component({
    template: '<ion-nav [root]="rootPage"></ion-nav>',
    providers: [
        provide(TranslateLoader, {
            useFactory: (http:Http) => new TranslateStaticLoader(http, 'assets/i18n', '.json'),
            deps: [Http]
        }),
        TranslateService
    ]
})
export class MyApp {
    rootPage:any = StartPage;

    constructor(platform:Platform, datastoreService:DatastoreService, store:StoreService, translate:TranslateService) {
        platform.ready().then(() => {
            // Okay, so the platform is ready and our plugins are available.
            // Here you can do any higher level native things you might need.
            StatusBar.styleDefault();
            datastoreService.initDB();
            store.loadControllerFromDb();

            this.initTranslateProvider(translate);
        });
    }

    private initTranslateProvider(translate:TranslateService) {
        let userLang = navigator.language.split('-')[0]; // use navigator lang if available
        userLang = /(de|en)/gi.test(userLang) ? userLang : 'en';

        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('en');

        // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use(userLang);
    }
}

ionicBootstrap(MyApp, [VALO_PLUS_PROVIDERS, TRANSLATE_PROVIDERS], {
    platforms: {
        ios: {
            statusbarPadding: true
        }
    }
});
