/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlCarroDetailComponent } from '../../../../../../main/webapp/app/entities/pl-carro/pl-carro-detail.component';
import { PlCarroService } from '../../../../../../main/webapp/app/entities/pl-carro/pl-carro.service';
import { PlCarro } from '../../../../../../main/webapp/app/entities/pl-carro/pl-carro.model';

describe('Component Tests', () => {

    describe('PlCarro Management Detail Component', () => {
        let comp: PlCarroDetailComponent;
        let fixture: ComponentFixture<PlCarroDetailComponent>;
        let service: PlCarroService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlCarroDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlCarroService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlCarroDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlCarroDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlCarroService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlCarro(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plCarro).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
