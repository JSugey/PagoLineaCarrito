import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlCarroHist } from './pl-carro-hist.model';
import { PlCarroHistService } from './pl-carro-hist.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-carro-hist',
    templateUrl: './pl-carro-hist.component.html'
})
export class PlCarroHistComponent implements OnInit, OnDestroy {
plCarroHists: PlCarroHist[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plCarroHistService: PlCarroHistService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plCarroHistService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plCarroHists = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlCarroHists();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlCarroHist) {
        return item.id;
    }
    registerChangeInPlCarroHists() {
        this.eventSubscriber = this.eventManager.subscribe('plCarroHistListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
