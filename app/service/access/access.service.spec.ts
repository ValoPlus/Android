import { AccessService } from './access.service';
import { beforeEachProviders, expect, inject, it, describe } from '@angular/core/testing';

describe('Access Service Test', () => {
    beforeEachProviders(() => [AccessService]);

    it('Testing get UUID', inject([AccessService], (accessService:AccessService) => {
        expect(accessService.saveChannelInDevice(null, null)).toEqual('devuuid');
    }));
});