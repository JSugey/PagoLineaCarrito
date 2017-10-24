import { BaseEntity } from './../../shared';

export class PlCarroDetHist implements BaseEntity {
    constructor(
        public id?: number,
        public concepto?: string,
        public idLiquidacion?: number,
        public fechaVigencia?: any,
        public importe?: number,
        public llave?: string,
        public generoUs?: boolean,
        public carro?: BaseEntity,
    ) {
        this.generoUs = false;
    }
}
