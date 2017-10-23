import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamBanco } from './pl-param-banco.model';
import { PlParamBancoPopupService } from './pl-param-banco-popup.service';
import { PlParamBancoService } from './pl-param-banco.service';

@Component({
    selector: 'jhi-pl-param-banco-delete-dialog',
    templateUrl: './pl-param-banco-delete-dialog.component.html'
})
export class PlParamBancoDeleteDialogComponent {

    plParamBanco: PlParamBanco;

    constructor(
        private plParamBancoService: PlParamBancoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.plParamBancoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'plParamBancoListModification',
                content: 'Deleted an plParamBanco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pl-param-banco-delete-popup',
    template: ''
})
export class PlParamBancoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private plParamBancoPopupService: PlParamBancoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.plParamBancoPopupService
                .open(PlParamBancoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
