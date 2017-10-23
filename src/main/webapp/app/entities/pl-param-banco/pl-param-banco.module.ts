import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlParamBancoService,
    PlParamBancoPopupService,
    PlParamBancoComponent,
    PlParamBancoDetailComponent,
    PlParamBancoDialogComponent,
    PlParamBancoPopupComponent,
    PlParamBancoDeletePopupComponent,
    PlParamBancoDeleteDialogComponent,
    plParamBancoRoute,
    plParamBancoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plParamBancoRoute,
    ...plParamBancoPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlParamBancoComponent,
        PlParamBancoDetailComponent,
        PlParamBancoDialogComponent,
        PlParamBancoDeleteDialogComponent,
        PlParamBancoPopupComponent,
        PlParamBancoDeletePopupComponent,
    ],
    entryComponents: [
        PlParamBancoComponent,
        PlParamBancoDialogComponent,
        PlParamBancoPopupComponent,
        PlParamBancoDeleteDialogComponent,
        PlParamBancoDeletePopupComponent,
    ],
    providers: [
        PlParamBancoService,
        PlParamBancoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlParamBancoModule {}
