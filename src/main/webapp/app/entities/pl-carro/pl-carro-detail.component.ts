import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarro } from './pl-carro.model';
import { PlCarroService } from './pl-carro.service';

@Component({
    selector: 'jhi-pl-carro-detail',
    templateUrl: './pl-carro-detail.component.html'
})
export class PlCarroDetailComponent implements OnInit, OnDestroy {

    plCarro: PlCarro;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plCarroService: PlCarroService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlCarros();
    }

    load(id) {
        this.plCarroService.find(id).subscribe((plCarro) => {
            this.plCarro = plCarro;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlCarros() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plCarroListModification',
            (response) => this.load(this.plCarro.id)
        );
    }
}
