import { Routes } from '@angular/router';
import { IntegratedTicketingOutComponent } from './integrated-ticketing-out.component.ts';

export const routes: Routes = [
	{ path: '', component: IntegratedTicketingOutComponent, pathMatch: 'full', data: {breadcrumb: '一票制出库查询'} },
];