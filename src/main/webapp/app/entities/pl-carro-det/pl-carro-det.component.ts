import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlCarroDet } from './pl-carro-det.model';
import { PlCarroDetService } from './pl-carro-det.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-carro-det',
    templateUrl: './pl-carro-det.component.html'
})
export class PlCarroDetComponent implements OnInit, OnDestroy {
plCarroDets: PlCarroDet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plCarroDetService: PlCarroDetService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plCarroDetService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plCarroDets = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlCarroDets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlCarroDet) {
        return item.id;
    }
    registerChangeInPlCarroDets() {
        this.eventSubscriber = this.eventManager.subscribe('plCarroDetListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
