import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlParamEnvioComponent } from './pl-param-envio.component';
import { PlParamEnvioDetailComponent } from './pl-param-envio-detail.component';
import { PlParamEnvioPopupComponent } from './pl-param-envio-dialog.component';
import { PlParamEnvioDeletePopupComponent } from './pl-param-envio-delete-dialog.component';

export const plParamEnvioRoute: Routes = [
    {
        path: 'pl-param-envio',
        component: PlParamEnvioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamEnvio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-param-envio/:id',
        component: PlParamEnvioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamEnvio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plParamEnvioPopupRoute: Routes = [
    {
        path: 'pl-param-envio-new',
        component: PlParamEnvioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamEnvio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-envio/:id/edit',
        component: PlParamEnvioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamEnvio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-envio/:id/delete',
        component: PlParamEnvioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamEnvio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
