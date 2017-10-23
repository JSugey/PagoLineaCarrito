import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlParamRespuestaComponent } from './pl-param-respuesta.component';
import { PlParamRespuestaDetailComponent } from './pl-param-respuesta-detail.component';
import { PlParamRespuestaPopupComponent } from './pl-param-respuesta-dialog.component';
import { PlParamRespuestaDeletePopupComponent } from './pl-param-respuesta-delete-dialog.component';

export const plParamRespuestaRoute: Routes = [
    {
        path: 'pl-param-respuesta',
        component: PlParamRespuestaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamRespuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-param-respuesta/:id',
        component: PlParamRespuestaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamRespuesta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plParamRespuestaPopupRoute: Routes = [
    {
        path: 'pl-param-respuesta-new',
        component: PlParamRespuestaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamRespuesta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-respuesta/:id/edit',
        component: PlParamRespuestaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamRespuesta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-respuesta/:id/delete',
        component: PlParamRespuestaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamRespuesta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
