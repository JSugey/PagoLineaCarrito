import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlCarroHistComponent } from './pl-carro-hist.component';
import { PlCarroHistDetailComponent } from './pl-carro-hist-detail.component';
import { PlCarroHistPopupComponent } from './pl-carro-hist-dialog.component';
import { PlCarroHistDeletePopupComponent } from './pl-carro-hist-delete-dialog.component';

export const plCarroHistRoute: Routes = [
    {
        path: 'pl-carro-hist',
        component: PlCarroHistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroHist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-carro-hist/:id',
        component: PlCarroHistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroHist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plCarroHistPopupRoute: Routes = [
    {
        path: 'pl-carro-hist-new',
        component: PlCarroHistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-hist/:id/edit',
        component: PlCarroHistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-hist/:id/delete',
        component: PlCarroHistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroHist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
