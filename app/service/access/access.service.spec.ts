import {AccessService} from "./access.service";
import {beforeEachProviders, expect, inject, it, describe, async} from "@angular/core/testing";
import {Device} from "../../domain/Controller";
import {StoreService} from "../store.service";
import {Channel} from "../../domain/channel/Channel";
import {ChannelTypes} from "../../domain/channel/ChannelTypes";
import {ConnectionService} from "../connection/connection.service";
import {BaseRequestOptions, Http, Response} from "@angular/http";
import {DeviceService} from "../device/device.service";
import {DatastoreService} from "../datastore/datastore.service";
import {MockBackend} from "@angular/http/testing";

describe('Access Service Test', () => {
    let accessService:AccessService, storageService:StoreService, dbService:DatastoreService, mockBackend:MockBackend;

    beforeEachProviders(() => [AccessService, StoreService, ConnectionService, DeviceService, DatastoreService, MockBackend, BaseRequestOptions,
        {
            provide: Http, useFactory: (backend, options) => {
            return new Http(backend, options);
        }, deps: [MockBackend, BaseRequestOptions]
        }
    ]);

    beforeEach(inject([AccessService, StoreService, DatastoreService, MockBackend], (access:AccessService, storage:StoreService, db:DatastoreService, _mockBackend:MockBackend) => {
        accessService = access;
        storageService = storage;
        dbService = db;
        mockBackend = _mockBackend;
        // Reset DB
        dbService.reset();
    }));

    it('Testing save channel', (done) => {
        const device:Device = new Device("Test Device", "", []);
        const channel:Channel = new Channel();
        channel.name = 'test';
        channel.type = ChannelTypes[ChannelTypes.WS2812];
        dbService.initDB();
        storageService.loadControllerFromDb();
        storageService.add(device);

        mockBackend.connections.subscribe(connection => {
            connection.mockRespond(new Response({body: ''}));
        });
        accessService.saveChannelInDevice(channel, device).subscribe(
            () => {
                storageService.loadControllerFromDb(() => {
                    expect(storageService.controller.length).toEqual(1);
                    expect(storageService.controller[0].channel.length).toEqual(1);
                    expect(storageService.controller[0].channel[0].name).toEqual('test');
                    done();
                });
            },
            () => fail()
        );
    });

    it('Testing delete channel', (done) => {
        const device:Device = new Device("Test Device", "", []);
        const channel:Channel = new Channel();
        channel.name = 'test';
        channel.type = ChannelTypes[ChannelTypes.WS2812];

        device.channel.push(channel);

        dbService.initDB();
        storageService.loadControllerFromDb();
        storageService.add(device);

        mockBackend.connections.subscribe(connection => {
            connection.mockRespond(new Response({body: ''}));
        });

        accessService.deleteChannelInDevice(channel, device).subscribe(() => {
            storageService.loadControllerFromDb(() => {
                expect(storageService.controller[0].channel.length).toEqual(0);
                done();
            });
        })
    });
});
