/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlCarroDetDetailComponent } from '../../../../../../main/webapp/app/entities/pl-carro-det/pl-carro-det-detail.component';
import { PlCarroDetService } from '../../../../../../main/webapp/app/entities/pl-carro-det/pl-carro-det.service';
import { PlCarroDet } from '../../../../../../main/webapp/app/entities/pl-carro-det/pl-carro-det.model';

describe('Component Tests', () => {

    describe('PlCarroDet Management Detail Component', () => {
        let comp: PlCarroDetDetailComponent;
        let fixture: ComponentFixture<PlCarroDetDetailComponent>;
        let service: PlCarroDetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlCarroDetDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlCarroDetService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlCarroDetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlCarroDetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlCarroDetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlCarroDet(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plCarroDet).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
