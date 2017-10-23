import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PlCarroDet } from './pl-carro-det.model';
import { PlCarroDetService } from './pl-carro-det.service';

@Injectable()
export class PlCarroDetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private plCarroDetService: PlCarroDetService

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
                this.plCarroDetService.find(id).subscribe((plCarroDet) => {
                    if (plCarroDet.fechaVigencia) {
                        plCarroDet.fechaVigencia = {
                            year: plCarroDet.fechaVigencia.getFullYear(),
                            month: plCarroDet.fechaVigencia.getMonth() + 1,
                            day: plCarroDet.fechaVigencia.getDate()
                        };
                    }
                    this.ngbModalRef = this.plCarroDetModalRef(component, plCarroDet);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plCarroDetModalRef(component, new PlCarroDet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plCarroDetModalRef(component: Component, plCarroDet: PlCarroDet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plCarroDet = plCarroDet;
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
