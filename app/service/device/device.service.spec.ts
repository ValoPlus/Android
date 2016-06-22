import { describe, beforeEachProviders, it, expect, inject } from '@angular/core/testing';
import { DeviceService } from './device.service';
import {ConnectionService} from "../connection/connection.service";

describe('Device Service Test', () => {
    beforeEachProviders(() => [DeviceService]);

    it('Testing get UUID', inject([DeviceService], (deviceService) => {
        expect(deviceService.getDeviceId()).toEqual('devuuid');
    }));
});
