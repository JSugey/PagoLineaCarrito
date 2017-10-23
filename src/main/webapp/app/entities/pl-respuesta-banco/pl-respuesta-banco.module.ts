import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlRespuestaBancoService,
    PlRespuestaBancoPopupService,
    PlRespuestaBancoComponent,
    PlRespuestaBancoDetailComponent,
    PlRespuestaBancoDialogComponent,
    PlRespuestaBancoPopupComponent,
    PlRespuestaBancoDeletePopupComponent,
    PlRespuestaBancoDeleteDialogComponent,
    plRespuestaBancoRoute,
    plRespuestaBancoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plRespuestaBancoRoute,
    ...plRespuestaBancoPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlRespuestaBancoComponent,
        PlRespuestaBancoDetailComponent,
        PlRespuestaBancoDialogComponent,
        PlRespuestaBancoDeleteDialogComponent,
        PlRespuestaBancoPopupComponent,
        PlRespuestaBancoDeletePopupComponent,
    ],
    entryComponents: [
        PlRespuestaBancoComponent,
        PlRespuestaBancoDialogComponent,
        PlRespuestaBancoPopupComponent,
        PlRespuestaBancoDeleteDialogComponent,
        PlRespuestaBancoDeletePopupComponent,
    ],
    providers: [
        PlRespuestaBancoService,
        PlRespuestaBancoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlRespuestaBancoModule {}
