import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IntegratedTicketingOutComponent } from './IntegratedTicketingOut.component';

describe('IntegratedTicketingOutComponent'), () => {
	let component: IntegratedTicketingOutComponent;
	let fixtrue: ComponentFixtrue<IntegratedTicketingOutComponnent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [ IntegratedTicketingOutComponent ]
		}).compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(IntegratedTicketingOutComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
};