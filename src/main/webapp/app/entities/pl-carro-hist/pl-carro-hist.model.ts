import { BaseEntity } from './../../shared';

export class PlCarroHist implements BaseEntity {
    constructor(
        public id?: number,
        public fechaEnvio?: any,
        public referencia?: string,
        public carro?: BaseEntity,
    ) {
    }
}
