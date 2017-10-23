import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PlCarro } from './pl-carro.model';
import { PlCarroPopupService } from './pl-carro-popup.service';
import { PlCarroService } from './pl-carro.service';

@Component({
    selector: 'jhi-pl-carro-dialog',
    templateUrl: './pl-carro-dialog.component.html'
})
export class PlCarroDialogComponent implements OnInit {

    plCarro: PlCarro;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private plCarroService: PlCarroService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plCarro.id !== undefined) {
            this.subscribeToSaveResponse(
                this.plCarroService.update(this.plCarro));
        } else {
            this.subscribeToSaveResponse(
                this.plCarroService.create(this.plCarro));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlCarro>) {
        result.subscribe((res: PlCarro) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PlCarro) {
        this.eventManager.broadcast({ name: 'plCarroListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-pl-carro-popup',
    template: ''
})
export class PlCarroPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroPopupService: PlCarroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.plCarroPopupService
                    .open(PlCarroDialogComponent as Component, params['id']);
            } else {
                this.plCarroPopupService
                    .open(PlCarroDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
