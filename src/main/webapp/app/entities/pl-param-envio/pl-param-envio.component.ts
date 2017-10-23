import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlParamEnvio } from './pl-param-envio.model';
import { PlParamEnvioService } from './pl-param-envio.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-param-envio',
    templateUrl: './pl-param-envio.component.html'
})
export class PlParamEnvioComponent implements OnInit, OnDestroy {
plParamEnvios: PlParamEnvio[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plParamEnvioService: PlParamEnvioService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plParamEnvioService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plParamEnvios = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlParamEnvios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlParamEnvio) {
        return item.id;
    }
    registerChangeInPlParamEnvios() {
        this.eventSubscriber = this.eventManager.subscribe('plParamEnvioListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
