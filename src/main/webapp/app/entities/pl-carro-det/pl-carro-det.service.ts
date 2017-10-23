import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PlCarroDet } from './pl-carro-det.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlCarroDetService {

    private resourceUrl = 'api/pl-carro-dets';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(plCarroDet: PlCarroDet): Observable<PlCarroDet> {
        const copy = this.convert(plCarroDet);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(plCarroDet: PlCarroDet): Observable<PlCarroDet> {
        const copy = this.convert(plCarroDet);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PlCarroDet> {
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
        entity.fechaVigencia = this.dateUtils
            .convertLocalDateFromServer(entity.fechaVigencia);
    }

    private convert(plCarroDet: PlCarroDet): PlCarroDet {
        const copy: PlCarroDet = Object.assign({}, plCarroDet);
        copy.fechaVigencia = this.dateUtils
            .convertLocalDateToServer(plCarroDet.fechaVigencia);
        return copy;
    }
}
