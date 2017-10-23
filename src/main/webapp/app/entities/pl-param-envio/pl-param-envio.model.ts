import { BaseEntity } from './../../shared';

export class PlParamEnvio implements BaseEntity {
    constructor(
        public id?: number,
        public valor?: string,
        public intentopago?: BaseEntity,
        public parametrobancos?: BaseEntity[],
    ) {
    }
}
