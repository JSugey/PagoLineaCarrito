import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PlCarroHist } from './pl-carro-hist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlCarroHistService {

    private resourceUrl = 'api/pl-carro-hists';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(plCarroHist: PlCarroHist): Observable<PlCarroHist> {
        const copy = this.convert(plCarroHist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(plCarroHist: PlCarroHist): Observable<PlCarroHist> {
        const copy = this.convert(plCarroHist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PlCarroHist> {
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
        entity.fechaEnvio = this.dateUtils
            .convertDateTimeFromServer(entity.fechaEnvio);
    }

    private convert(plCarroHist: PlCarroHist): PlCarroHist {
        const copy: PlCarroHist = Object.assign({}, plCarroHist);

        copy.fechaEnvio = this.dateUtils.toDate(plCarroHist.fechaEnvio);
        return copy;
    }
}
