import {Device} from "ionic-native/dist/index";
/**
 * Created by tom on 23.05.16.
 */
    
export class RegistrationRequest {
    constructor(public key:string = "123456789abc",
                public clientId:string = '') {
    }
}

export class RegistrationResponse {
    public controllerType:string;
    public availableChannel:string;
    public controllerAlias: string;
    public configured: Boolean;

    constructor(controllerType:string, availableChannel:string, controllerAlias:string, configured:Boolean) {
        this.controllerType = controllerType;
        this.availableChannel = availableChannel;
        this.controllerAlias = controllerAlias;
        this.configured = configured;
    }
}