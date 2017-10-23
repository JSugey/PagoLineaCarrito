/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlCarroDetHistDetailComponent } from '../../../../../../main/webapp/app/entities/pl-carro-det-hist/pl-carro-det-hist-detail.component';
import { PlCarroDetHistService } from '../../../../../../main/webapp/app/entities/pl-carro-det-hist/pl-carro-det-hist.service';
import { PlCarroDetHist } from '../../../../../../main/webapp/app/entities/pl-carro-det-hist/pl-carro-det-hist.model';

describe('Component Tests', () => {

    describe('PlCarroDetHist Management Detail Component', () => {
        let comp: PlCarroDetHistDetailComponent;
        let fixture: ComponentFixture<PlCarroDetHistDetailComponent>;
        let service: PlCarroDetHistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlCarroDetHistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlCarroDetHistService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlCarroDetHistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlCarroDetHistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlCarroDetHistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlCarroDetHist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plCarroDetHist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
