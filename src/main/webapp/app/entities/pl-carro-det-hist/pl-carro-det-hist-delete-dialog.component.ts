import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { PlCarroDetHistPopupService } from './pl-carro-det-hist-popup.service';
import { PlCarroDetHistService } from './pl-carro-det-hist.service';

@Component({
    selector: 'jhi-pl-carro-det-hist-delete-dialog',
    templateUrl: './pl-carro-det-hist-delete-dialog.component.html'
})
export class PlCarroDetHistDeleteDialogComponent {

    plCarroDetHist: PlCarroDetHist;

    constructor(
        private plCarroDetHistService: PlCarroDetHistService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plCarroDetHistService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plCarroDetHistListModification',
                content: 'Deleted an plCarroDetHist'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-carro-det-hist-delete-popup',
    template: ''
})
export class PlCarroDetHistDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroDetHistPopupService: PlCarroDetHistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plCarroDetHistPopupService
                .open(PlCarroDetHistDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
