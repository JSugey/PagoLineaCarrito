import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { PlCarroDetHistService } from './pl-carro-det-hist.service';

@Injectable()
export class PlCarroDetHistPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private plCarroDetHistService: PlCarroDetHistService

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
                this.plCarroDetHistService.find(id).subscribe((plCarroDetHist) => {
                    if (plCarroDetHist.fechaVigencia) {
                        plCarroDetHist.fechaVigencia = {
                            year: plCarroDetHist.fechaVigencia.getFullYear(),
                            month: plCarroDetHist.fechaVigencia.getMonth() + 1,
                            day: plCarroDetHist.fechaVigencia.getDate()
                        };
                    }
                    this.ngbModalRef = this.plCarroDetHistModalRef(component, plCarroDetHist);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plCarroDetHistModalRef(component, new PlCarroDetHist());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plCarroDetHistModalRef(component: Component, plCarroDetHist: PlCarroDetHist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plCarroDetHist = plCarroDetHist;
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
