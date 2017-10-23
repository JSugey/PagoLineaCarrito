import { BaseEntity } from './../../shared';

const enum StatusIntentoPago {
    'DISPERSA',
    'ACEPTADA',
    'RECHAZADA'
}

export class PlIntentoPago implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public enviado?: boolean,
        public status?: StatusIntentoPago,
        public auth?: string,
        public historialcarro?: BaseEntity,
    ) {
        this.enviado = false;
    }
}
