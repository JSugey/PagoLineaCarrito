import { BaseEntity } from './../../shared';

export class PlCarroDet implements BaseEntity {
    constructor(
        public id?: number,
        public idLiquidacion?: number,
        public fechaVigencia?: any,
        public importe?: number,
        public llave?: string,
        public concepto?: string,
        public generoUs?: boolean,
        public carro?: BaseEntity,
    ) {
        this.generoUs = false;
    }
}
