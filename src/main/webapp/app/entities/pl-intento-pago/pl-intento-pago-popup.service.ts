import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PlIntentoPago } from './pl-intento-pago.model';
import { PlIntentoPagoService } from './pl-intento-pago.service';

@Injectable()
export class PlIntentoPagoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private plIntentoPagoService: PlIntentoPagoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.plIntentoPagoService.find(id).subscribe((plIntentoPago) => {
                    plIntentoPago.fecha = this.datePipe
                        .transform(plIntentoPago.fecha, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.plIntentoPagoModalRef(component, plIntentoPago);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plIntentoPagoModalRef(component, new PlIntentoPago());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plIntentoPagoModalRef(component: Component, plIntentoPago: PlIntentoPago): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plIntentoPago = plIntentoPago;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
