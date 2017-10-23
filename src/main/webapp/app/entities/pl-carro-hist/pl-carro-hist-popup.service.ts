import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PlCarroHist } from './pl-carro-hist.model';
import { PlCarroHistService } from './pl-carro-hist.service';

@Injectable()
export class PlCarroHistPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private plCarroHistService: PlCarroHistService

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
                this.plCarroHistService.find(id).subscribe((plCarroHist) => {
                    plCarroHist.fechaEnvio = this.datePipe
                        .transform(plCarroHist.fechaEnvio, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.plCarroHistModalRef(component, plCarroHist);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plCarroHistModalRef(component, new PlCarroHist());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plCarroHistModalRef(component: Component, plCarroHist: PlCarroHist): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plCarroHist = plCarroHist;
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
