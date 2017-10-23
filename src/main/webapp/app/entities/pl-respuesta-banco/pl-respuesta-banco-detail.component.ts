import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlRespuestaBanco } from './pl-respuesta-banco.model';
import { PlRespuestaBancoService } from './pl-respuesta-banco.service';

@Component({
    selector: 'jhi-pl-respuesta-banco-detail',
    templateUrl: './pl-respuesta-banco-detail.component.html'
})
export class PlRespuestaBancoDetailComponent implements OnInit, OnDestroy {

    plRespuestaBanco: PlRespuestaBanco;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plRespuestaBancoService: PlRespuestaBancoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlRespuestaBancos();
    }

    load(id) {
        this.plRespuestaBancoService.find(id).subscribe((plRespuestaBanco) => {
            this.plRespuestaBanco = plRespuestaBanco;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlRespuestaBancos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plRespuestaBancoListModification',
            (response) => this.load(this.plRespuestaBanco.id)
        );
    }
}
