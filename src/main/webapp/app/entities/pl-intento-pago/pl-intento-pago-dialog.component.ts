import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlIntentoPago } from './pl-intento-pago.model';
import { PlIntentoPagoPopupService } from './pl-intento-pago-popup.service';
import { PlIntentoPagoService } from './pl-intento-pago.service';
import { PlCarroHist, PlCarroHistService } from '../pl-carro-hist';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-intento-pago-dialog',
    templateUrl: './pl-intento-pago-dialog.component.html'
})
export class PlIntentoPagoDialogComponent implements OnInit {

    plIntentoPago: PlIntentoPago;
    isSaving: boolean;

    plcarrohists: PlCarroHist[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plIntentoPagoService: PlIntentoPagoService,
        private plCarroHistService: PlCarroHistService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plCarroHistService.query()
            .subscribe((res: ResponseWrapper) => { this.plcarrohists = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plIntentoPago.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plIntentoPagoService.update(this.plIntentoPago));
        } else {
            this.subscribeToSaveResponse(
                this.plIntentoPagoService.create(this.plIntentoPago));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlIntentoPago>) {
        result.subscribe((res: PlIntentoPago) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlIntentoPago) {
        this.eventManager.broadcast({ name: 'plIntentoPagoListModification', content: 'OK'});
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

    trackPlCarroHistById(index: number, item: PlCarroHist) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-intento-pago-popup',
    template: ''
})
export class PlIntentoPagoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plIntentoPagoPopupService: PlIntentoPagoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plIntentoPagoPopupService
                    .open(PlIntentoPagoDialogComponent as Component, params['id']);
            } else {
                this.plIntentoPagoPopupService
                    .open(PlIntentoPagoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
