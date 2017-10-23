import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PlCarroDetHist } from './pl-carro-det-hist.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlCarroDetHistService {

    private resourceUrl = 'api/pl-carro-det-hists';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(plCarroDetHist: PlCarroDetHist): Observable<PlCarroDetHist> {
        const copy = this.convert(plCarroDetHist);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(plCarroDetHist: PlCarroDetHist): Observable<PlCarroDetHist> {
        const copy = this.convert(plCarroDetHist);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PlCarroDetHist> {
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

    private convert(plCarroDetHist: PlCarroDetHist): PlCarroDetHist {
        const copy: PlCarroDetHist = Object.assign({}, plCarroDetHist);
        copy.fechaVigencia = this.dateUtils
            .convertLocalDateToServer(plCarroDetHist.fechaVigencia);
        return copy;
    }
}
