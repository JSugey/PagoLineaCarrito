import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PlParamRespuesta } from './pl-param-respuesta.model';
import { PlParamRespuestaService } from './pl-param-respuesta.service';

@Injectable()
export class PlParamRespuestaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private plParamRespuestaService: PlParamRespuestaService

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
                this.plParamRespuestaService.find(id).subscribe((plParamRespuesta) => {
                    this.ngbModalRef = this.plParamRespuestaModalRef(component, plParamRespuesta);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.plParamRespuestaModalRef(component, new PlParamRespuesta());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    plParamRespuestaModalRef(component: Component, plParamRespuesta: PlParamRespuesta): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plParamRespuesta = plParamRespuesta;
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
