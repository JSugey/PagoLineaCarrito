import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlParamEnvio } from './pl-param-envio.model';
import { PlParamEnvioPopupService } from './pl-param-envio-popup.service';
import { PlParamEnvioService } from './pl-param-envio.service';
import { PlParamBanco, PlParamBancoService } from '../pl-param-banco';
import { PlIntentoPago, PlIntentoPagoService } from '../pl-intento-pago';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-param-envio-dialog',
    templateUrl: './pl-param-envio-dialog.component.html'
})
export class PlParamEnvioDialogComponent implements OnInit {

    plParamEnvio: PlParamEnvio;
    isSaving: boolean;

    plparambancos: PlParamBanco[];

    intentopagos: PlIntentoPago[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plParamEnvioService: PlParamEnvioService,
        private plParamBancoService: PlParamBancoService,
        private plIntentoPagoService: PlIntentoPagoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plParamBancoService.query()
            .subscribe((res: ResponseWrapper) => { this.plparambancos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.plIntentoPagoService
            .query({filter: 'plparamenvio-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.plParamEnvio.intentopago || !this.plParamEnvio.intentopago.id) {
                    this.intentopagos = res.json;
                } else {
                    this.plIntentoPagoService
                        .find(this.plParamEnvio.intentopago.id)
                        .subscribe((subRes: PlIntentoPago) => {
                            this.intentopagos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plParamEnvio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plParamEnvioService.update(this.plParamEnvio));
        } else {
            this.subscribeToSaveResponse(
                this.plParamEnvioService.create(this.plParamEnvio));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlParamEnvio>) {
        result.subscribe((res: PlParamEnvio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlParamEnvio) {
        this.eventManager.broadcast({ name: 'plParamEnvioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackPlParamBancoById(index: number, item: PlParamBanco) {
        return item.id;
    }

    trackPlIntentoPagoById(index: number, item: PlIntentoPago) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-param-envio-popup',
    template: ''
})
export class PlParamEnvioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamEnvioPopupService: PlParamEnvioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plParamEnvioPopupService
                    .open(PlParamEnvioDialogComponent as Component, params['id']);
            } else {
                this.plParamEnvioPopupService
                    .open(PlParamEnvioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
