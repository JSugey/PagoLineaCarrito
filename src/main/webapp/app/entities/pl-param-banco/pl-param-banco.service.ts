import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PlParamBanco } from './pl-param-banco.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PlParamBancoService {

    private resourceUrl = 'api/pl-param-bancos';

    constructor(private http: Http) { }

    create(plParamBanco: PlParamBanco): Observable<PlParamBanco> {
        const copy = this.convert(plParamBanco);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(plParamBanco: PlParamBanco): Observable<PlParamBanco> {
        const copy = this.convert(plParamBanco);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PlParamBanco> {
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

    private convert(plParamBanco: PlParamBanco): PlParamBanco {
        const copy: PlParamBanco = Object.assign({}, plParamBanco);
        return copy;
    }
}
