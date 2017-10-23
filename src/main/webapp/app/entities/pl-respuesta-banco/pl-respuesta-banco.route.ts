import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlRespuestaBancoComponent } from './pl-respuesta-banco.component';
import { PlRespuestaBancoDetailComponent } from './pl-respuesta-banco-detail.component';
import { PlRespuestaBancoPopupComponent } from './pl-respuesta-banco-dialog.component';
import { PlRespuestaBancoDeletePopupComponent } from './pl-respuesta-banco-delete-dialog.component';

export const plRespuestaBancoRoute: Routes = [
    {
        path: 'pl-respuesta-banco',
        component: PlRespuestaBancoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plRespuestaBanco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-respuesta-banco/:id',
        component: PlRespuestaBancoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plRespuestaBanco.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plRespuestaBancoPopupRoute: Routes = [
    {
        path: 'pl-respuesta-banco-new',
        component: PlRespuestaBancoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plRespuestaBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-respuesta-banco/:id/edit',
        component: PlRespuestaBancoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plRespuestaBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-respuesta-banco/:id/delete',
        component: PlRespuestaBancoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plRespuestaBanco.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
