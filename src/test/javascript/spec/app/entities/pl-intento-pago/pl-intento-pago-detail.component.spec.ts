/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlIntentoPagoDetailComponent } from '../../../../../../main/webapp/app/entities/pl-intento-pago/pl-intento-pago-detail.component';
import { PlIntentoPagoService } from '../../../../../../main/webapp/app/entities/pl-intento-pago/pl-intento-pago.service';
import { PlIntentoPago } from '../../../../../../main/webapp/app/entities/pl-intento-pago/pl-intento-pago.model';

describe('Component Tests', () => {

    describe('PlIntentoPago Management Detail Component', () => {
        let comp: PlIntentoPagoDetailComponent;
        let fixture: ComponentFixture<PlIntentoPagoDetailComponent>;
        let service: PlIntentoPagoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlIntentoPagoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlIntentoPagoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlIntentoPagoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlIntentoPagoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlIntentoPagoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlIntentoPago(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plIntentoPago).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
