import { BaseEntity } from './../../shared';

export class PlParamRespuesta implements BaseEntity {
    constructor(
        public id?: number,
        public valor?: string,
        public plParamBanco?: BaseEntity,
        public respuestabanco?: BaseEntity,
    ) {
    }
}
