import { BaseEntity } from './../../shared';

const enum TipoParamBanco {
    'IN',
    'OUT'
}

export class PlParamBanco implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public tipo?: TipoParamBanco,
        public enUso?: string,
        public plParamEnvio?: BaseEntity,
        public plParamRespuesta?: BaseEntity,
    ) {
    }
}
