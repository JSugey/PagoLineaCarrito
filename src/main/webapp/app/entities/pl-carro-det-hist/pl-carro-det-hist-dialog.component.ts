import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { PlCarroDetHistPopupService } from './pl-carro-det-hist-popup.service';
import { PlCarroDetHistService } from './pl-carro-det-hist.service';
import { PlCarroHist, PlCarroHistService } from '../pl-carro-hist';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pl-carro-det-hist-dialog',
    templateUrl: './pl-carro-det-hist-dialog.component.html'
})
export class PlCarroDetHistDialogComponent implements OnInit {

    plCarroDetHist: PlCarroDetHist;
    isSaving: boolean;

    plcarrohists: PlCarroHist[];
    fechaVigenciaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plCarroDetHistService: PlCarroDetHistService,
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
        if (this.plCarroDetHist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plCarroDetHistService.update(this.plCarroDetHist));
        } else {
            this.subscribeToSaveResponse(
                this.plCarroDetHistService.create(this.plCarroDetHist));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlCarroDetHist>) {
        result.subscribe((res: PlCarroDetHist) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlCarroDetHist) {
        this.eventManager.broadcast({ name: 'plCarroDetHistListModification', content: 'OK'});
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
    selector: 'jhi-pl-carro-det-hist-popup',
    template: ''
})
export class PlCarroDetHistPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroDetHistPopupService: PlCarroDetHistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plCarroDetHistPopupService
                    .open(PlCarroDetHistDialogComponent as Component, params['id']);
            } else {
                this.plCarroDetHistPopupService
                    .open(PlCarroDetHistDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
