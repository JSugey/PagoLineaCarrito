import { BaseEntity } from './../../shared';

export class PlRespuestaBanco implements BaseEntity {
    constructor(
        public id?: number,
        public exitoso?: boolean,
        public fecha?: any,
        public intentopago?: BaseEntity,
    ) {
        this.exitoso = false;
    }
}
