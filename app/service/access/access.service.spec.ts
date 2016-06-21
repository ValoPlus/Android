import { AccessService } from './access.service';
import { beforeEachProviders, expect, inject, it, describe } from '@angular/core/testing';
import {Device} from "../../domain/Controller";
import {StoreService} from "../store.service";
import {Channel} from "../../domain/channel/Channel";
import {ChannelTypes} from "../../domain/channel/ChannelTypes";

describe('Access Service Test', () => {
    beforeEachProviders(() => [AccessService, StoreService]);

    it('Testing get UUID', inject([AccessService, StoreService], (accessService:AccessService, storageService:StoreService) => {
        const device:Device = new Device();
        const channel:Channel = new Channel();

        device.name = 'Test Device';
        storageService.add(device);

        channel.name = 'test';
        channel.type = ChannelTypes[ChannelTypes.WS2812];

        accessService.saveChannelInDevice(channel, device).subscribe(
            ok => {
                storageService.loadControllerFromDb();
                expect(storageService.controller.length).toEqual(1);
            }
        );
    }));
});