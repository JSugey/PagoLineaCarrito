import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlCarroDetComponent } from './pl-carro-det.component';
import { PlCarroDetDetailComponent } from './pl-carro-det-detail.component';
import { PlCarroDetPopupComponent } from './pl-carro-det-dialog.component';
import { PlCarroDetDeletePopupComponent } from './pl-carro-det-delete-dialog.component';

export const plCarroDetRoute: Routes = [
    {
        path: 'pl-carro-det',
        component: PlCarroDetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-carro-det/:id',
        component: PlCarroDetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plCarroDetPopupRoute: Routes = [
    {
        path: 'pl-carro-det-new',
        component: PlCarroDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-det/:id/edit',
        component: PlCarroDetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro-det/:id/delete',
        component: PlCarroDetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarroDet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
