import {Device} from "ionic-native/dist/index";
/**
 * Created by tom on 23.05.16.
 */
    
export class RegistrationRequest {
    constructor(public key:String = "123456789abc",
                public clientId:String = '') {
    }
}

export class RegistrationResponse {
    constructor(public controllerType:String = "",
                public availableChannel:String = "",
                public controllerAlias: String = "",
                public configured: Boolean = false) {
    }
}