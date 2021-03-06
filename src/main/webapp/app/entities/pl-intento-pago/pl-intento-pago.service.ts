import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PlIntentoPago } from './pl-intento-pago.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlIntentoPagoService {

    private resourceUrl = 'api/pl-intento-pagos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(plIntentoPago: PlIntentoPago): Observable<PlIntentoPago> {
        const copy = this.convert(plIntentoPago);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(plIntentoPago: PlIntentoPago): Observable<PlIntentoPago> {
        const copy = this.convert(plIntentoPago);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PlIntentoPago> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.fecha = this.dateUtils
            .convertDateTimeFromServer(entity.fecha);
    }

    private convert(plIntentoPago: PlIntentoPago): PlIntentoPago {
        const copy: PlIntentoPago = Object.assign({}, plIntentoPago);

        copy.fecha = this.dateUtils.toDate(plIntentoPago.fecha);
        return copy;
    }
}
