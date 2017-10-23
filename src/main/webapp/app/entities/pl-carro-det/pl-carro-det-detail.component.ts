import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroDet } from './pl-carro-det.model';
import { PlCarroDetService } from './pl-carro-det.service';

@Component({
    selector: 'jhi-pl-carro-det-detail',
    templateUrl: './pl-carro-det-detail.component.html'
})
export class PlCarroDetDetailComponent implements OnInit, OnDestroy {

    plCarroDet: PlCarroDet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plCarroDetService: PlCarroDetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlCarroDets();
    }

    load(id) {
        this.plCarroDetService.find(id).subscribe((plCarroDet) => {
            this.plCarroDet = plCarroDet;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlCarroDets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plCarroDetListModification',
            (response) => this.load(this.plCarroDet.id)
        );
    }
}
