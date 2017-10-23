import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { PlParamRespuesta } from './pl-param-respuesta.model';
import { PlParamRespuestaService } from './pl-param-respuesta.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-pl-param-respuesta',
    templateUrl: './pl-param-respuesta.component.html'
})
export class PlParamRespuestaComponent implements OnInit, OnDestroy {
plParamRespuestas: PlParamRespuesta[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private plParamRespuestaService: PlParamRespuestaService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.plParamRespuestaService.query().subscribe(
            (res: ResponseWrapper) => {
                this.plParamRespuestas = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlParamRespuestas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlParamRespuesta) {
        return item.id;
    }
    registerChangeInPlParamRespuestas() {
        this.eventSubscriber = this.eventManager.subscribe('plParamRespuestaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
