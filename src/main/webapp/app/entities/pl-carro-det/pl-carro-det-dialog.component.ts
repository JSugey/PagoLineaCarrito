import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlCarroDet } from './pl-carro-det.model';
import { PlCarroDetPopupService } from './pl-carro-det-popup.service';
import { PlCarroDetService } from './pl-carro-det.service';
import { PlCarro, PlCarroService } from '../pl-carro';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-carro-det-dialog',
    templateUrl: './pl-carro-det-dialog.component.html'
})
export class PlCarroDetDialogComponent implements OnInit {

    plCarroDet: PlCarroDet;
    isSaving: boolean;

    plcarros: PlCarro[];
    fechaVigenciaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plCarroDetService: PlCarroDetService,
        private plCarroService: PlCarroService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.plCarroService.query()
            .subscribe((res: ResponseWrapper) => { this.plcarros = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plCarroDet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plCarroDetService.update(this.plCarroDet));
        } else {
            this.subscribeToSaveResponse(
                this.plCarroDetService.create(this.plCarroDet));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlCarroDet>) {
        result.subscribe((res: PlCarroDet) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlCarroDet) {
        this.eventManager.broadcast({ name: 'plCarroDetListModification', content: 'OK'});
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

    trackPlCarroById(index: number, item: PlCarro) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pl-carro-det-popup',
    template: ''
})
export class PlCarroDetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroDetPopupService: PlCarroDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plCarroDetPopupService
                    .open(PlCarroDetDialogComponent as Component, params['id']);
            } else {
                this.plCarroDetPopupService
                    .open(PlCarroDetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
