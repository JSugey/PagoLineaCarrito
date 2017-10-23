import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { PlCarroDetHistService } from './pl-carro-det-hist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-carro-det-hist',
    templateUrl: './pl-carro-det-hist.component.html'
})
export class PlCarroDetHistComponent implements OnInit, OnDestroy {
plCarroDetHists: PlCarroDetHist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plCarroDetHistService: PlCarroDetHistService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plCarroDetHistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plCarroDetHists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlCarroDetHists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlCarroDetHist) {
        return item.id;
    }
    registerChangeInPlCarroDetHists() {
        this.eventSubscriber = this.eventManager.subscribe('plCarroDetHistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
