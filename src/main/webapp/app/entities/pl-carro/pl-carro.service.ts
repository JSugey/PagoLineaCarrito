import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PlCarro } from './pl-carro.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlCarroService {

    private resourceUrl = 'api/pl-carros';

    constructor(private http: Http) { }

    create(plCarro: PlCarro): Observable<PlCarro> {
        const copy = this.convert(plCarro);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(plCarro: PlCarro): Observable<PlCarro> {
        const copy = this.convert(plCarro);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PlCarro> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(plCarro: PlCarro): PlCarro {
        const copy: PlCarro = Object.assign({}, plCarro);
        return copy;
    }
}
