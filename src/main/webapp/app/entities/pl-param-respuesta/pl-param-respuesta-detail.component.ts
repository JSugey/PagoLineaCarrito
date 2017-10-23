import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamRespuesta } from './pl-param-respuesta.model';
import { PlParamRespuestaService } from './pl-param-respuesta.service';

@Component({
    selector: 'jhi-pl-param-respuesta-detail',
    templateUrl: './pl-param-respuesta-detail.component.html'
})
export class PlParamRespuestaDetailComponent implements OnInit, OnDestroy {

    plParamRespuesta: PlParamRespuesta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plParamRespuestaService: PlParamRespuestaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlParamRespuestas();
    }

    load(id) {
        this.plParamRespuestaService.find(id).subscribe((plParamRespuesta) => {
            this.plParamRespuesta = plParamRespuesta;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlParamRespuestas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plParamRespuestaListModification',
            (response) => this.load(this.plParamRespuesta.id)
        );
    }
}
