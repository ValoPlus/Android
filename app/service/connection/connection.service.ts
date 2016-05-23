import {Injectable} from "@angular/core";
import {RequestOptions, Http, Headers, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {RegistrationRequest, RegistrationResponse} from "../../domain/Registration";
import {Device} from "ionic-native/dist/index";
import {Channel} from "../../domain/Channel";
/**
 * Created by tom on 23.05.16.
 */
    
@Injectable()
export class ConnectionService {
    private init = '/api/init';

    private channel = '/api/channel';

    private options:RequestOptions;

    constructor(private http:Http) {
        let headers = new Headers({
            'Authorization': Device.device.uuid,
            'Content-Type': 'application/json' });
        this.options = new RequestOptions({ headers: headers });
    }

    requestRegistration(reqObject:RegistrationRequest, ip:String):Observable<RegistrationResponse> {
        return this.http.post('http://' + ip + this.init, JSON.stringify(reqObject), this.options)
            .map(this.extractData)
            .catch(this.handleError)
    }

    getChannel(ip:String):Observable<Channel[]> {
        return this.http.get('http://' + ip + this.channel + '?clientId=test' , this.options)
            .map(this.extractData)
            .catch(this.handleError)
    }

    // requestWlan(reqObject:Wlan, ip:String):Observable<String> {
    //     return this.http.post(ip + this.wlan, JSON.stringify(reqObject), this.options)
    //         .map(this.extractDataString)
    //         .catch(this.handleError)
    // }

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