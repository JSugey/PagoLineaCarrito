/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlParamEnvioDetailComponent } from '../../../../../../main/webapp/app/entities/pl-param-envio/pl-param-envio-detail.component';
import { PlParamEnvioService } from '../../../../../../main/webapp/app/entities/pl-param-envio/pl-param-envio.service';
import { PlParamEnvio } from '../../../../../../main/webapp/app/entities/pl-param-envio/pl-param-envio.model';

describe('Component Tests', () => {

    describe('PlParamEnvio Management Detail Component', () => {
        let comp: PlParamEnvioDetailComponent;
        let fixture: ComponentFixture<PlParamEnvioDetailComponent>;
        let service: PlParamEnvioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlParamEnvioDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlParamEnvioService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlParamEnvioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlParamEnvioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlParamEnvioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlParamEnvio(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plParamEnvio).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
