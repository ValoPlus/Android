import {Injectable} from "@angular/core";
import {RequestOptions, Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {RegistrationRequest, RegistrationResponse} from "../../domain/Registration";
import {Device} from "ionic-native/dist/index";
import {Channel} from "../../domain/channel/Channel";
import {Wlan} from "../../domain/Wlan";
/**
 * Created by tom on 23.05.16.
 */

@Injectable()
export class ConnectionService {
    private pre = 'http://';

    private init = '/api/init';
    private alias = '/api/settings/alias';
    private wlan = '/api/settings/wlan';
    private channel = '/api/channel';

    private options:RequestOptions;

    constructor(private http:Http) {
        let headers = new Headers({
            'Authorization': 'test',
            'Content-Type': 'application/json'
        });
        this.options = new RequestOptions({headers: headers});
    }

    requestRegistration(reqObject:RegistrationRequest, ip:String):Observable<RegistrationResponse> {
        return this.http.post(this.pre + ip + this.init, JSON.stringify(reqObject), this.options)
            .map(this.extractData)
            .catch(this.handleError)
    }

    saveAlias(alias:String, ip:String):Observable<String> {
        return this.http.post(this.pre + ip + this.alias, alias, this.options)
            .map(this.extractDataString)
            .catch(this.handleError)
    }

    getWlan(ip:String):Observable<Wlan> {
        return this.http.get(this.url(ip, this.wlan), this.options)
            .map(this.extractData)
            .catch(this.handleError)
    }

    saveWlan(wlan:Wlan, ip:String):Observable<String> {
        return this.http.post(this.url(ip, this.wlan), JSON.stringify(wlan), this.options)
            .map(this.extractDataString)
            .catch(this.handleError)
    }

    getChannel(ip:String):Observable<Channel[]> {
        return this.http.get(this.url(ip, this.channel) + '?clientId=test', this.options)
            .map(this.extractData)
            .catch(this.handleError)
    }

    saveChannel(ip:String, channel:Channel):Observable<Channel[]> {
        return this.http.post(this.url(ip, this.channel), JSON.stringify(channel), this.options)
            .map(this.extractDataString)
            .catch(this.handleError)
    }

    deleteChannel(ip:String, name:String):Observable<String> {
        return this.http.delete(this.url(ip, this.channel)+ '?channelName = ' + name, this.options)
            .map(this.extractDataString)
            .catch(this.handleError)
    }

    // requestWlan(reqObject:Wlan, ip:String):Observable<String> {
    //     return this.http.post(ip + this.wlan, JSON.stringify(reqObject), this.options)
    //         .map(this.extractDataString)
    //         .catch(this.handleError)
    // }

    private url(ip:String, path:String):String {
        return this.pre + ip + path;
    }

    private extractDataString(res:Response) {
        if (res.status < 200) {
            throw new Error('Response status: ' + res.status);
        }
        let body = res.text();
        return body || '';
    }

    private extractData(res:Response) {
        if (res.status < 200) {
            throw new Error('Response status: ' + res.status);
        }
        let body = res.json();
        return body || {};
    }

    private handleError(error:any) {
        // In a real world app, we might use a remote logging infrastructure
        let errMsg = error.json().message || 'Could not connect to server!';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}