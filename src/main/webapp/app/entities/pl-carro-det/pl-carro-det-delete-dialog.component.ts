import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroDet } from './pl-carro-det.model';
import { PlCarroDetPopupService } from './pl-carro-det-popup.service';
import { PlCarroDetService } from './pl-carro-det.service';

@Component({
    selector: 'jhi-pl-carro-det-delete-dialog',
    templateUrl: './pl-carro-det-delete-dialog.component.html'
})
export class PlCarroDetDeleteDialogComponent {

    plCarroDet: PlCarroDet;

    constructor(
        private plCarroDetService: PlCarroDetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plCarroDetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plCarroDetListModification',
                content: 'Deleted an plCarroDet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-carro-det-delete-popup',
    template: ''
})
export class PlCarroDetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroDetPopupService: PlCarroDetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plCarroDetPopupService
                .open(PlCarroDetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
