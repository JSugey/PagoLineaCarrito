import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamRespuesta } from './pl-param-respuesta.model';
import { PlParamRespuestaPopupService } from './pl-param-respuesta-popup.service';
import { PlParamRespuestaService } from './pl-param-respuesta.service';

@Component({
    selector: 'jhi-pl-param-respuesta-delete-dialog',
    templateUrl: './pl-param-respuesta-delete-dialog.component.html'
})
export class PlParamRespuestaDeleteDialogComponent {

    plParamRespuesta: PlParamRespuesta;

    constructor(
        private plParamRespuestaService: PlParamRespuestaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plParamRespuestaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plParamRespuestaListModification',
                content: 'Deleted an plParamRespuesta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-param-respuesta-delete-popup',
    template: ''
})
export class PlParamRespuestaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamRespuestaPopupService: PlParamRespuestaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plParamRespuestaPopupService
                .open(PlParamRespuestaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
