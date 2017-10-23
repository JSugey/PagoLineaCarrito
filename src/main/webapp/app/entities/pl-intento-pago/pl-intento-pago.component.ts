import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlIntentoPago } from './pl-intento-pago.model';
import { PlIntentoPagoService } from './pl-intento-pago.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-intento-pago',
    templateUrl: './pl-intento-pago.component.html'
})
export class PlIntentoPagoComponent implements OnInit, OnDestroy {
plIntentoPagos: PlIntentoPago[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plIntentoPagoService: PlIntentoPagoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plIntentoPagoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plIntentoPagos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlIntentoPagos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlIntentoPago) {
        return item.id;
    }
    registerChangeInPlIntentoPagos() {
        this.eventSubscriber = this.eventManager.subscribe('plIntentoPagoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
