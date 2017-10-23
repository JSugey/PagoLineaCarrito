/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlParamRespuestaDetailComponent } from '../../../../../../main/webapp/app/entities/pl-param-respuesta/pl-param-respuesta-detail.component';
import { PlParamRespuestaService } from '../../../../../../main/webapp/app/entities/pl-param-respuesta/pl-param-respuesta.service';
import { PlParamRespuesta } from '../../../../../../main/webapp/app/entities/pl-param-respuesta/pl-param-respuesta.model';

describe('Component Tests', () => {

    describe('PlParamRespuesta Management Detail Component', () => {
        let comp: PlParamRespuestaDetailComponent;
        let fixture: ComponentFixture<PlParamRespuestaDetailComponent>;
        let service: PlParamRespuestaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlParamRespuestaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlParamRespuestaService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlParamRespuestaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlParamRespuestaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlParamRespuestaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlParamRespuesta(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plParamRespuesta).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
