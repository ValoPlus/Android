/**
 * Created by tom on 28.05.16.
 */
export class Connection {
    private _id:String;
    constructor(public key:String,
                public ip:String) {
        this._id = 'last';
    }
}
