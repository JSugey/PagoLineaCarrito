import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamEnvio } from './pl-param-envio.model';
import { PlParamEnvioService } from './pl-param-envio.service';

@Component({
    selector: 'jhi-pl-param-envio-detail',
    templateUrl: './pl-param-envio-detail.component.html'
})
export class PlParamEnvioDetailComponent implements OnInit, OnDestroy {

    plParamEnvio: PlParamEnvio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plParamEnvioService: PlParamEnvioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlParamEnvios();
    }

    load(id) {
        this.plParamEnvioService.find(id).subscribe((plParamEnvio) => {
            this.plParamEnvio = plParamEnvio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlParamEnvios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plParamEnvioListModification',
            (response) => this.load(this.plParamEnvio.id)
        );
    }
}
