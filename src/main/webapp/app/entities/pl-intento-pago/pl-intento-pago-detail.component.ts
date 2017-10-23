import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlIntentoPago } from './pl-intento-pago.model';
import { PlIntentoPagoService } from './pl-intento-pago.service';

@Component({
    selector: 'jhi-pl-intento-pago-detail',
    templateUrl: './pl-intento-pago-detail.component.html'
})
export class PlIntentoPagoDetailComponent implements OnInit, OnDestroy {

    plIntentoPago: PlIntentoPago;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plIntentoPagoService: PlIntentoPagoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlIntentoPagos();
    }

    load(id) {
        this.plIntentoPagoService.find(id).subscribe((plIntentoPago) => {
            this.plIntentoPago = plIntentoPago;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlIntentoPagos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plIntentoPagoListModification',
            (response) => this.load(this.plIntentoPago.id)
        );
    }
}
