import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlCarro } from './pl-carro.model';
import { PlCarroService } from './pl-carro.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-carro',
    templateUrl: './pl-carro.component.html'
})
export class PlCarroComponent implements OnInit, OnDestroy {
plCarros: PlCarro[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plCarroService: PlCarroService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plCarroService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plCarros = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlCarros();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlCarro) {
        return item.id;
    }
    registerChangeInPlCarros() {
        this.eventSubscriber = this.eventManager.subscribe('plCarroListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
