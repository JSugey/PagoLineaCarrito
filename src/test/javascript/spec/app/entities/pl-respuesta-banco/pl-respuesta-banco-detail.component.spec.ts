/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlRespuestaBancoDetailComponent } from '../../../../../../main/webapp/app/entities/pl-respuesta-banco/pl-respuesta-banco-detail.component';
import { PlRespuestaBancoService } from '../../../../../../main/webapp/app/entities/pl-respuesta-banco/pl-respuesta-banco.service';
import { PlRespuestaBanco } from '../../../../../../main/webapp/app/entities/pl-respuesta-banco/pl-respuesta-banco.model';

describe('Component Tests', () => {

    describe('PlRespuestaBanco Management Detail Component', () => {
        let comp: PlRespuestaBancoDetailComponent;
        let fixture: ComponentFixture<PlRespuestaBancoDetailComponent>;
        let service: PlRespuestaBancoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlRespuestaBancoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlRespuestaBancoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlRespuestaBancoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlRespuestaBancoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlRespuestaBancoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlRespuestaBanco(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plRespuestaBanco).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
