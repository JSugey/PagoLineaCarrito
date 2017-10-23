import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlRespuestaBanco } from './pl-respuesta-banco.model';
import { PlRespuestaBancoPopupService } from './pl-respuesta-banco-popup.service';
import { PlRespuestaBancoService } from './pl-respuesta-banco.service';
import { PlIntentoPago, PlIntentoPagoService } from '../pl-intento-pago';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-respuesta-banco-dialog',
    templateUrl: './pl-respuesta-banco-dialog.component.html'
})
export class PlRespuestaBancoDialogComponent implements OnInit {

    plRespuestaBanco: PlRespuestaBanco;
    isSaving: boolean;

    intentopagos: PlIntentoPago[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plRespuestaBancoService: PlRespuestaBancoService,
        private plIntentoPagoService: PlIntentoPagoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plIntentoPagoService
            .query({filter: 'plrespuestabanco-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.plRespuestaBanco.intentopago || !this.plRespuestaBanco.intentopago.id) {
                    this.intentopagos = res.json;
                } else {
                    this.plIntentoPagoService
                        .find(this.plRespuestaBanco.intentopago.id)
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
        if (this.plRespuestaBanco.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plRespuestaBancoService.update(this.plRespuestaBanco));
        } else {
            this.subscribeToSaveResponse(
                this.plRespuestaBancoService.create(this.plRespuestaBanco));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlRespuestaBanco>) {
        result.subscribe((res: PlRespuestaBanco) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlRespuestaBanco) {
        this.eventManager.broadcast({ name: 'plRespuestaBancoListModification', content: 'OK'});
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

    trackPlIntentoPagoById(index: number, item: PlIntentoPago) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-respuesta-banco-popup',
    template: ''
})
export class PlRespuestaBancoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plRespuestaBancoPopupService: PlRespuestaBancoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plRespuestaBancoPopupService
                    .open(PlRespuestaBancoDialogComponent as Component, params['id']);
            } else {
                this.plRespuestaBancoPopupService
                    .open(PlRespuestaBancoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
