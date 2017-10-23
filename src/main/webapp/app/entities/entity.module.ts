import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PagolineaPlCarroModule } from './pl-carro/pl-carro.module';
import { PagolineaPlCarroDetModule } from './pl-carro-det/pl-carro-det.module';
import { PagolineaPlCarroHistModule } from './pl-carro-hist/pl-carro-hist.module';
import { PagolineaPlCarroDetHistModule } from './pl-carro-det-hist/pl-carro-det-hist.module';
import { PagolineaPlIntentoPagoModule } from './pl-intento-pago/pl-intento-pago.module';
import { PagolineaPlParamBancoModule } from './pl-param-banco/pl-param-banco.module';
import { PagolineaPlParamEnvioModule } from './pl-param-envio/pl-param-envio.module';
import { PagolineaPlParamRespuestaModule } from './pl-param-respuesta/pl-param-respuesta.module';
import { PagolineaPlRespuestaBancoModule } from './pl-respuesta-banco/pl-respuesta-banco.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PagolineaPlCarroModule,
        PagolineaPlCarroDetModule,
        PagolineaPlCarroHistModule,
        PagolineaPlCarroDetHistModule,
        PagolineaPlIntentoPagoModule,
        PagolineaPlParamBancoModule,
        PagolineaPlParamEnvioModule,
        PagolineaPlParamRespuestaModule,
        PagolineaPlRespuestaBancoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagolineaEntityModule {}
