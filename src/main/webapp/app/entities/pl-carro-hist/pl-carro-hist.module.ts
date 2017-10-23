import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlCarroHistService,
    PlCarroHistPopupService,
    PlCarroHistComponent,
    PlCarroHistDetailComponent,
    PlCarroHistDialogComponent,
    PlCarroHistPopupComponent,
    PlCarroHistDeletePopupComponent,
    PlCarroHistDeleteDialogComponent,
    plCarroHistRoute,
    plCarroHistPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plCarroHistRoute,
    ...plCarroHistPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlCarroHistComponent,
        PlCarroHistDetailComponent,
        PlCarroHistDialogComponent,
        PlCarroHistDeleteDialogComponent,
        PlCarroHistPopupComponent,
        PlCarroHistDeletePopupComponent,
    ],
    entryComponents: [
        PlCarroHistComponent,
        PlCarroHistDialogComponent,
        PlCarroHistPopupComponent,
        PlCarroHistDeleteDialogComponent,
        PlCarroHistDeletePopupComponent,
    ],
    providers: [
        PlCarroHistService,
        PlCarroHistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlCarroHistModule {}
