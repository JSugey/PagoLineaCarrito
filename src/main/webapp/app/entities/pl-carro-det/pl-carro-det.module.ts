import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlCarroDetService,
    PlCarroDetPopupService,
    PlCarroDetComponent,
    PlCarroDetDetailComponent,
    PlCarroDetDialogComponent,
    PlCarroDetPopupComponent,
    PlCarroDetDeletePopupComponent,
    PlCarroDetDeleteDialogComponent,
    plCarroDetRoute,
    plCarroDetPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plCarroDetRoute,
    ...plCarroDetPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlCarroDetComponent,
        PlCarroDetDetailComponent,
        PlCarroDetDialogComponent,
        PlCarroDetDeleteDialogComponent,
        PlCarroDetPopupComponent,
        PlCarroDetDeletePopupComponent,
    ],
    entryComponents: [
        PlCarroDetComponent,
        PlCarroDetDialogComponent,
        PlCarroDetPopupComponent,
        PlCarroDetDeleteDialogComponent,
        PlCarroDetDeletePopupComponent,
    ],
    providers: [
        PlCarroDetService,
        PlCarroDetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlCarroDetModule {}
