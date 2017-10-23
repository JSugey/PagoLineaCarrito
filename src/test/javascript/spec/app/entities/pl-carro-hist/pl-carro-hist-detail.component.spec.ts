/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlCarroHistDetailComponent } from '../../../../../../main/webapp/app/entities/pl-carro-hist/pl-carro-hist-detail.component';
import { PlCarroHistService } from '../../../../../../main/webapp/app/entities/pl-carro-hist/pl-carro-hist.service';
import { PlCarroHist } from '../../../../../../main/webapp/app/entities/pl-carro-hist/pl-carro-hist.model';

describe('Component Tests', () => {

    describe('PlCarroHist Management Detail Component', () => {
        let comp: PlCarroHistDetailComponent;
        let fixture: ComponentFixture<PlCarroHistDetailComponent>;
        let service: PlCarroHistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlCarroHistDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlCarroHistService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlCarroHistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlCarroHistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlCarroHistService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlCarroHist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plCarroHist).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
