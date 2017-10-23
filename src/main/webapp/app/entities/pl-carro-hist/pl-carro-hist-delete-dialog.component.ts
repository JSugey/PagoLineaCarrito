import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroHist } from './pl-carro-hist.model';
import { PlCarroHistPopupService } from './pl-carro-hist-popup.service';
import { PlCarroHistService } from './pl-carro-hist.service';

@Component({
    selector: 'jhi-pl-carro-hist-delete-dialog',
    templateUrl: './pl-carro-hist-delete-dialog.component.html'
})
export class PlCarroHistDeleteDialogComponent {

    plCarroHist: PlCarroHist;

    constructor(
        private plCarroHistService: PlCarroHistService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plCarroHistService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plCarroHistListModification',
                content: 'Deleted an plCarroHist'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-carro-hist-delete-popup',
    template: ''
})
export class PlCarroHistDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroHistPopupService: PlCarroHistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plCarroHistPopupService
                .open(PlCarroHistDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
