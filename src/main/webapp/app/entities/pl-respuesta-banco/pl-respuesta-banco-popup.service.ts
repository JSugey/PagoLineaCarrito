import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PlRespuestaBanco } from './pl-respuesta-banco.model';
import { PlRespuestaBancoService } from './pl-respuesta-banco.service';

@Injectable()
export class PlRespuestaBancoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private plRespuestaBancoService: PlRespuestaBancoService

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
                this.plRespuestaBancoService.find(id).subscribe((plRespuestaBanco) => {
                    plRespuestaBanco.fecha = this.datePipe
                        .transform(plRespuestaBanco.fecha, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.plRespuestaBancoModalRef(component, plRespuestaBanco);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plRespuestaBancoModalRef(component, new PlRespuestaBanco());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plRespuestaBancoModalRef(component: Component, plRespuestaBanco: PlRespuestaBanco): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plRespuestaBanco = plRespuestaBanco;
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
