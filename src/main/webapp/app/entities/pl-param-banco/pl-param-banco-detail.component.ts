import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlParamBanco } from './pl-param-banco.model';
import { PlParamBancoService } from './pl-param-banco.service';

@Component({
    selector: 'jhi-pl-param-banco-detail',
    templateUrl: './pl-param-banco-detail.component.html'
})
export class PlParamBancoDetailComponent implements OnInit, OnDestroy {

    plParamBanco: PlParamBanco;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private plParamBancoService: PlParamBancoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlParamBancos();
    }

    load(id) {
        this.plParamBancoService.find(id).subscribe((plParamBanco) => {
            this.plParamBanco = plParamBanco;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlParamBancos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'plParamBancoListModification',
            (response) => this.load(this.plParamBanco.id)
        );
    }
}
