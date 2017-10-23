import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarro } from './pl-carro.model';
import { PlCarroPopupService } from './pl-carro-popup.service';
import { PlCarroService } from './pl-carro.service';

@Component({
    selector: 'jhi-pl-carro-delete-dialog',
    templateUrl: './pl-carro-delete-dialog.component.html'
})
export class PlCarroDeleteDialogComponent {

    plCarro: PlCarro;

    constructor(
        private plCarroService: PlCarroService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plCarroService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plCarroListModification',
                content: 'Deleted an plCarro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-carro-delete-popup',
    template: ''
})
export class PlCarroDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plCarroPopupService: PlCarroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plCarroPopupService
                .open(PlCarroDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
