import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlCarroDetHistService,
    PlCarroDetHistPopupService,
    PlCarroDetHistComponent,
    PlCarroDetHistDetailComponent,
    PlCarroDetHistDialogComponent,
    PlCarroDetHistPopupComponent,
    PlCarroDetHistDeletePopupComponent,
    PlCarroDetHistDeleteDialogComponent,
    plCarroDetHistRoute,
    plCarroDetHistPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plCarroDetHistRoute,
    ...plCarroDetHistPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlCarroDetHistComponent,
        PlCarroDetHistDetailComponent,
        PlCarroDetHistDialogComponent,
        PlCarroDetHistDeleteDialogComponent,
        PlCarroDetHistPopupComponent,
        PlCarroDetHistDeletePopupComponent,
    ],
    entryComponents: [
        PlCarroDetHistComponent,
        PlCarroDetHistDialogComponent,
        PlCarroDetHistPopupComponent,
        PlCarroDetHistDeleteDialogComponent,
        PlCarroDetHistDeletePopupComponent,
    ],
    providers: [
        PlCarroDetHistService,
        PlCarroDetHistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlCarroDetHistModule {}
