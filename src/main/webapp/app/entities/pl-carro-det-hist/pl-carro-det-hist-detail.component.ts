import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { PlCarroDetHistService } from './pl-carro-det-hist.service';

@Component({
    selector: 'jhi-pl-carro-det-hist-detail',
    templateUrl: './pl-carro-det-hist-detail.component.html'
})
export class PlCarroDetHistDetailComponent implements OnInit, OnDestroy {

    plCarroDetHist: PlCarroDetHist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plCarroDetHistService: PlCarroDetHistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlCarroDetHists();
    }

    load(id) {
        this.plCarroDetHistService.find(id).subscribe((plCarroDetHist) => {
            this.plCarroDetHist = plCarroDetHist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlCarroDetHists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plCarroDetHistListModification',
            (response) => this.load(this.plCarroDetHist.id)
        );
    }
}
