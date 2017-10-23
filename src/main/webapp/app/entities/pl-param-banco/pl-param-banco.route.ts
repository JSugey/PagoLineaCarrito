import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlParamBancoComponent } from './pl-param-banco.component';
import { PlParamBancoDetailComponent } from './pl-param-banco-detail.component';
import { PlParamBancoPopupComponent } from './pl-param-banco-dialog.component';
import { PlParamBancoDeletePopupComponent } from './pl-param-banco-delete-dialog.component';

export const plParamBancoRoute: Routes = [
    {
        path: 'pl-param-banco',
        component: PlParamBancoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamBanco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-param-banco/:id',
        component: PlParamBancoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamBanco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plParamBancoPopupRoute: Routes = [
    {
        path: 'pl-param-banco-new',
        component: PlParamBancoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-banco/:id/edit',
        component: PlParamBancoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-param-banco/:id/delete',
        component: PlParamBancoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plParamBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
