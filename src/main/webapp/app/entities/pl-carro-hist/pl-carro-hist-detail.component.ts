import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlCarroHist } from './pl-carro-hist.model';
import { PlCarroHistService } from './pl-carro-hist.service';

@Component({
    selector: 'jhi-pl-carro-hist-detail',
    templateUrl: './pl-carro-hist-detail.component.html'
})
export class PlCarroHistDetailComponent implements OnInit, OnDestroy {

    plCarroHist: PlCarroHist;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plCarroHistService: PlCarroHistService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlCarroHists();
    }

    load(id) {
        this.plCarroHistService.find(id).subscribe((plCarroHist) => {
            this.plCarroHist = plCarroHist;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlCarroHists() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plCarroHistListModification',
            (response) => this.load(this.plCarroHist.id)
        );
    }
}
