import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlIntentoPago } from './pl-intento-pago.model';
import { PlIntentoPagoPopupService } from './pl-intento-pago-popup.service';
import { PlIntentoPagoService } from './pl-intento-pago.service';

@Component({
    selector: 'jhi-pl-intento-pago-delete-dialog',
    templateUrl: './pl-intento-pago-delete-dialog.component.html'
})
export class PlIntentoPagoDeleteDialogComponent {

    plIntentoPago: PlIntentoPago;

    constructor(
        private plIntentoPagoService: PlIntentoPagoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plIntentoPagoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plIntentoPagoListModification',
                content: 'Deleted an plIntentoPago'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-intento-pago-delete-popup',
    template: ''
})
export class PlIntentoPagoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plIntentoPagoPopupService: PlIntentoPagoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plIntentoPagoPopupService
                .open(PlIntentoPagoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
