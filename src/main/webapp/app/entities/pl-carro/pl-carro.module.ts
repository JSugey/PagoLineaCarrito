import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlCarroService,
    PlCarroPopupService,
    PlCarroComponent,
    PlCarroDetailComponent,
    PlCarroDialogComponent,
    PlCarroPopupComponent,
    PlCarroDeletePopupComponent,
    PlCarroDeleteDialogComponent,
    plCarroRoute,
    plCarroPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plCarroRoute,
    ...plCarroPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlCarroComponent,
        PlCarroDetailComponent,
        PlCarroDialogComponent,
        PlCarroDeleteDialogComponent,
        PlCarroPopupComponent,
        PlCarroDeletePopupComponent,
    ],
    entryComponents: [
        PlCarroComponent,
        PlCarroDialogComponent,
        PlCarroPopupComponent,
        PlCarroDeleteDialogComponent,
        PlCarroDeletePopupComponent,
    ],
    providers: [
        PlCarroService,
        PlCarroPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlCarroModule {}
