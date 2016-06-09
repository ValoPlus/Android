/**
 * Created by tom on 28.05.16.
 */
export class Connection {
    private _id:String;
    constructor(public key:string,
                public ip:string) {
        this._id = 'last';
    }
}
