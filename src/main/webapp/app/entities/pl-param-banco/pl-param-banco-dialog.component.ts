import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlParamBanco } from './pl-param-banco.model';
import { PlParamBancoPopupService } from './pl-param-banco-popup.service';
import { PlParamBancoService } from './pl-param-banco.service';
import { PlParamEnvio, PlParamEnvioService } from '../pl-param-envio';
import { PlParamRespuesta, PlParamRespuestaService } from '../pl-param-respuesta';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-param-banco-dialog',
    templateUrl: './pl-param-banco-dialog.component.html'
})
export class PlParamBancoDialogComponent implements OnInit {

    plParamBanco: PlParamBanco;
    isSaving: boolean;

    plparamenvios: PlParamEnvio[];

    plparamrespuestas: PlParamRespuesta[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plParamBancoService: PlParamBancoService,
        private plParamEnvioService: PlParamEnvioService,
        private plParamRespuestaService: PlParamRespuestaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plParamEnvioService.query()
            .subscribe((res: ResponseWrapper) => { this.plparamenvios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.plParamRespuestaService.query()
            .subscribe((res: ResponseWrapper) => { this.plparamrespuestas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plParamBanco.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plParamBancoService.update(this.plParamBanco));
        } else {
            this.subscribeToSaveResponse(
                this.plParamBancoService.create(this.plParamBanco));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlParamBanco>) {
        result.subscribe((res: PlParamBanco) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlParamBanco) {
        this.eventManager.broadcast({ name: 'plParamBancoListModification', content: 'OK'});
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

    trackPlParamEnvioById(index: number, item: PlParamEnvio) {
        return item.id;
    }

    trackPlParamRespuestaById(index: number, item: PlParamRespuesta) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-param-banco-popup',
    template: ''
})
export class PlParamBancoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamBancoPopupService: PlParamBancoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plParamBancoPopupService
                    .open(PlParamBancoDialogComponent as Component, params['id']);
            } else {
                this.plParamBancoPopupService
                    .open(PlParamBancoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
