import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlCarroDetHistComponent } from './pl-carro-det-hist.component';
import { PlCarroDetHistDetailComponent } from './pl-carro-det-hist-detail.component';
import { PlCarroDetHistPopupComponent } from './pl-carro-det-hist-dialog.component';
import { PlCarroDetHistDeletePopupComponent } from './pl-carro-det-hist-delete-dialog.component';

export const plCarroDetHistRoute: Routes = [
    {
        path: 'pl-carro-det-hist',
        component: PlCarroDetHistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDetHist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-carro-det-hist/:id',
        component: PlCarroDetHistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDetHist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plCarroDetHistPopupRoute: Routes = [
    {
        path: 'pl-carro-det-hist-new',
        component: PlCarroDetHistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDetHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-det-hist/:id/edit',
        component: PlCarroDetHistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDetHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-det-hist/:id/delete',
        component: PlCarroDetHistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDetHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
