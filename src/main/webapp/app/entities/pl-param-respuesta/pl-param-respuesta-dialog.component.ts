import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlParamRespuesta } from './pl-param-respuesta.model';
import { PlParamRespuestaPopupService } from './pl-param-respuesta-popup.service';
import { PlParamRespuestaService } from './pl-param-respuesta.service';
import { PlRespuestaBanco, PlRespuestaBancoService } from '../pl-respuesta-banco';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-param-respuesta-dialog',
    templateUrl: './pl-param-respuesta-dialog.component.html'
})
export class PlParamRespuestaDialogComponent implements OnInit {

    plParamRespuesta: PlParamRespuesta;
    isSaving: boolean;

    plrespuestabancos: PlRespuestaBanco[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plParamRespuestaService: PlParamRespuestaService,
        private plRespuestaBancoService: PlRespuestaBancoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plRespuestaBancoService.query()
            .subscribe((res: ResponseWrapper) => { this.plrespuestabancos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plParamRespuesta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plParamRespuestaService.update(this.plParamRespuesta));
        } else {
            this.subscribeToSaveResponse(
                this.plParamRespuestaService.create(this.plParamRespuesta));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlParamRespuesta>) {
        result.subscribe((res: PlParamRespuesta) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlParamRespuesta) {
        this.eventManager.broadcast({ name: 'plParamRespuestaListModification', content: 'OK'});
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

    trackPlRespuestaBancoById(index: number, item: PlRespuestaBanco) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-param-respuesta-popup',
    template: ''
})
export class PlParamRespuestaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamRespuestaPopupService: PlParamRespuestaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plParamRespuestaPopupService
                    .open(PlParamRespuestaDialogComponent as Component, params['id']);
            } else {
                this.plParamRespuestaPopupService
                    .open(PlParamRespuestaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
