import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlParamBanco } from './pl-param-banco.model';
import { PlParamBancoService } from './pl-param-banco.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-param-banco',
    templateUrl: './pl-param-banco.component.html'
})
export class PlParamBancoComponent implements OnInit, OnDestroy {
plParamBancos: PlParamBanco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plParamBancoService: PlParamBancoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plParamBancoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plParamBancos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlParamBancos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlParamBanco) {
        return item.id;
    }
    registerChangeInPlParamBancos() {
        this.eventSubscriber = this.eventManager.subscribe('plParamBancoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
