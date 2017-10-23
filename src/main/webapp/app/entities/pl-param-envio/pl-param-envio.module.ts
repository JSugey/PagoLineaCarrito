import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlParamEnvioService,
    PlParamEnvioPopupService,
    PlParamEnvioComponent,
    PlParamEnvioDetailComponent,
    PlParamEnvioDialogComponent,
    PlParamEnvioPopupComponent,
    PlParamEnvioDeletePopupComponent,
    PlParamEnvioDeleteDialogComponent,
    plParamEnvioRoute,
    plParamEnvioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plParamEnvioRoute,
    ...plParamEnvioPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlParamEnvioComponent,
        PlParamEnvioDetailComponent,
        PlParamEnvioDialogComponent,
        PlParamEnvioDeleteDialogComponent,
        PlParamEnvioPopupComponent,
        PlParamEnvioDeletePopupComponent,
    ],
    entryComponents: [
        PlParamEnvioComponent,
        PlParamEnvioDialogComponent,
        PlParamEnvioPopupComponent,
        PlParamEnvioDeleteDialogComponent,
        PlParamEnvioDeletePopupComponent,
    ],
    providers: [
        PlParamEnvioService,
        PlParamEnvioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlParamEnvioModule {}
