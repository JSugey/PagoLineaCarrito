import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlParamRespuestaService,
    PlParamRespuestaPopupService,
    PlParamRespuestaComponent,
    PlParamRespuestaDetailComponent,
    PlParamRespuestaDialogComponent,
    PlParamRespuestaPopupComponent,
    PlParamRespuestaDeletePopupComponent,
    PlParamRespuestaDeleteDialogComponent,
    plParamRespuestaRoute,
    plParamRespuestaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plParamRespuestaRoute,
    ...plParamRespuestaPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlParamRespuestaComponent,
        PlParamRespuestaDetailComponent,
        PlParamRespuestaDialogComponent,
        PlParamRespuestaDeleteDialogComponent,
        PlParamRespuestaPopupComponent,
        PlParamRespuestaDeletePopupComponent,
    ],
    entryComponents: [
        PlParamRespuestaComponent,
        PlParamRespuestaDialogComponent,
        PlParamRespuestaPopupComponent,
        PlParamRespuestaDeleteDialogComponent,
        PlParamRespuestaDeletePopupComponent,
    ],
    providers: [
        PlParamRespuestaService,
        PlParamRespuestaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlParamRespuestaModule {}
