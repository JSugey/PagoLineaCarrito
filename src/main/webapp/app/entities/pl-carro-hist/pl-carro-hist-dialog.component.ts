import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlCarroHist } from './pl-carro-hist.model';
import { PlCarroHistPopupService } from './pl-carro-hist-popup.service';
import { PlCarroHistService } from './pl-carro-hist.service';
import { PlCarro, PlCarroService } from '../pl-carro';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-carro-hist-dialog',
    templateUrl: './pl-carro-hist-dialog.component.html'
})
export class PlCarroHistDialogComponent implements OnInit {

    plCarroHist: PlCarroHist;
    isSaving: boolean;

    plcarros: PlCarro[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plCarroHistService: PlCarroHistService,
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
        if (this.plCarroHist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plCarroHistService.update(this.plCarroHist));
        } else {
            this.subscribeToSaveResponse(
                this.plCarroHistService.create(this.plCarroHist));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlCarroHist>) {
        result.subscribe((res: PlCarroHist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlCarroHist) {
        this.eventManager.broadcast({ name: 'plCarroHistListModification', content: 'OK'});
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
    selector: 'jhi-pl-carro-hist-popup',
    template: ''
})
export class PlCarroHistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroHistPopupService: PlCarroHistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plCarroHistPopupService
                    .open(PlCarroHistDialogComponent as Component, params['id']);
            } else {
                this.plCarroHistPopupService
                    .open(PlCarroHistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
