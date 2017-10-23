import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamEnvio } from './pl-param-envio.model';
import { PlParamEnvioPopupService } from './pl-param-envio-popup.service';
import { PlParamEnvioService } from './pl-param-envio.service';

@Component({
    selector: 'jhi-pl-param-envio-delete-dialog',
    templateUrl: './pl-param-envio-delete-dialog.component.html'
})
export class PlParamEnvioDeleteDialogComponent {

    plParamEnvio: PlParamEnvio;

    constructor(
        private plParamEnvioService: PlParamEnvioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plParamEnvioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plParamEnvioListModification',
                content: 'Deleted an plParamEnvio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-param-envio-delete-popup',
    template: ''
})
export class PlParamEnvioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamEnvioPopupService: PlParamEnvioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plParamEnvioPopupService
                .open(PlParamEnvioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
