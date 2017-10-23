import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlCarroComponent } from './pl-carro.component';
import { PlCarroDetailComponent } from './pl-carro-detail.component';
import { PlCarroPopupComponent } from './pl-carro-dialog.component';
import { PlCarroDeletePopupComponent } from './pl-carro-delete-dialog.component';

export const plCarroRoute: Routes = [
    {
        path: 'pl-carro',
        component: PlCarroComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-carro/:id',
        component: PlCarroDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plCarroPopupRoute: Routes = [
    {
        path: 'pl-carro-new',
        component: PlCarroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro/:id/edit',
        component: PlCarroPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-carro/:id/delete',
        component: PlCarroDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plCarro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
