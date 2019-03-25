import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../../../shared/shared.module';
import { UI_COMPONENTS } from '../../../../system-ui';

import { routes } from './integrated-ticketing-out.routes.ts';
import { IntegratedTicketingOutComponent } from './integrated-ticketing-out.component.ts';

@NgModule({
	declarations: [
		IntegratedTicketingOutComponent,
	],
	imports: [
		UI_COMPONENTS,
		CommonModule,
		FormsModule,
		SharedModule,
		RouterModule.forChild(routes),
	],
	schemas: [CUSTOM_ELEMENTS_SCHEMA],
	providers: [DatePipe],
})
export class IntegratedTicketingOutModule {
	public static routes = routes;
}
