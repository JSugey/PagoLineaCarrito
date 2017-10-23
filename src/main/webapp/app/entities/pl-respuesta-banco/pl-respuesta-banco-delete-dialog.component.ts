import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlRespuestaBanco } from './pl-respuesta-banco.model';
import { PlRespuestaBancoPopupService } from './pl-respuesta-banco-popup.service';
import { PlRespuestaBancoService } from './pl-respuesta-banco.service';

@Component({
    selector: 'jhi-pl-respuesta-banco-delete-dialog',
    templateUrl: './pl-respuesta-banco-delete-dialog.component.html'
})
export class PlRespuestaBancoDeleteDialogComponent {

    plRespuestaBanco: PlRespuestaBanco;

    constructor(
        private plRespuestaBancoService: PlRespuestaBancoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plRespuestaBancoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plRespuestaBancoListModification',
                content: 'Deleted an plRespuestaBanco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-respuesta-banco-delete-popup',
    template: ''
})
export class PlRespuestaBancoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plRespuestaBancoPopupService: PlRespuestaBancoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plRespuestaBancoPopupService
                .open(PlRespuestaBancoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
