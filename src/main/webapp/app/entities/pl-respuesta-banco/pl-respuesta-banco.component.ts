import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlRespuestaBanco } from './pl-respuesta-banco.model';
import { PlRespuestaBancoService } from './pl-respuesta-banco.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-respuesta-banco',
    templateUrl: './pl-respuesta-banco.component.html'
})
export class PlRespuestaBancoComponent implements OnInit, OnDestroy {
plRespuestaBancos: PlRespuestaBanco[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plRespuestaBancoService: PlRespuestaBancoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plRespuestaBancoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plRespuestaBancos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlRespuestaBancos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlRespuestaBanco) {
        return item.id;
    }
    registerChangeInPlRespuestaBancos() {
        this.eventSubscriber = this.eventManager.subscribe('plRespuestaBancoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
