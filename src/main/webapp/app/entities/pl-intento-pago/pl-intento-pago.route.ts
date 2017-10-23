import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlIntentoPagoComponent } from './pl-intento-pago.component';
import { PlIntentoPagoDetailComponent } from './pl-intento-pago-detail.component';
import { PlIntentoPagoPopupComponent } from './pl-intento-pago-dialog.component';
import { PlIntentoPagoDeletePopupComponent } from './pl-intento-pago-delete-dialog.component';

export const plIntentoPagoRoute: Routes = [
    {
        path: 'pl-intento-pago',
        component: PlIntentoPagoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plIntentoPago.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pl-intento-pago/:id',
        component: PlIntentoPagoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plIntentoPago.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const plIntentoPagoPopupRoute: Routes = [
    {
        path: 'pl-intento-pago-new',
        component: PlIntentoPagoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plIntentoPago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-intento-pago/:id/edit',
        component: PlIntentoPagoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plIntentoPago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pl-intento-pago/:id/delete',
        component: PlIntentoPagoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'pagolineaApp.plIntentoPago.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
