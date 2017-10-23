import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PagolineaSharedModule } from '../../shared';
import {
    PlIntentoPagoService,
    PlIntentoPagoPopupService,
    PlIntentoPagoComponent,
    PlIntentoPagoDetailComponent,
    PlIntentoPagoDialogComponent,
    PlIntentoPagoPopupComponent,
    PlIntentoPagoDeletePopupComponent,
    PlIntentoPagoDeleteDialogComponent,
    plIntentoPagoRoute,
    plIntentoPagoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...plIntentoPagoRoute,
    ...plIntentoPagoPopupRoute,
];

@NgModule({
    imports: [
        PagolineaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlIntentoPagoComponent,
        PlIntentoPagoDetailComponent,
        PlIntentoPagoDialogComponent,
        PlIntentoPagoDeleteDialogComponent,
        PlIntentoPagoPopupComponent,
        PlIntentoPagoDeletePopupComponent,
    ],
    entryComponents: [
        PlIntentoPagoComponent,
        PlIntentoPagoDialogComponent,
        PlIntentoPagoPopupComponent,
        PlIntentoPagoDeleteDialogComponent,
        PlIntentoPagoDeletePopupComponent,
    ],
    providers: [
        PlIntentoPagoService,
        PlIntentoPagoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaPlIntentoPagoModule {}
