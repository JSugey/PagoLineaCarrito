/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { PagolineaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PlParamBancoDetailComponent } from '../../../../../../main/webapp/app/entities/pl-param-banco/pl-param-banco-detail.component';
import { PlParamBancoService } from '../../../../../../main/webapp/app/entities/pl-param-banco/pl-param-banco.service';
import { PlParamBanco } from '../../../../../../main/webapp/app/entities/pl-param-banco/pl-param-banco.model';

describe('Component Tests', () => {

    describe('PlParamBanco Management Detail Component', () => {
        let comp: PlParamBancoDetailComponent;
        let fixture: ComponentFixture<PlParamBancoDetailComponent>;
        let service: PlParamBancoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PagolineaTestModule],
                declarations: [PlParamBancoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PlParamBancoService,
                    JhiEventManager
                ]
            }).overrideTemplate(PlParamBancoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlParamBancoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlParamBancoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PlParamBanco(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.plParamBanco).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
