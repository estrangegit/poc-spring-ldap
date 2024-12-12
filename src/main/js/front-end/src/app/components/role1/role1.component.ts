import { Component, inject, OnInit } from '@angular/core';
import { TestApiResult } from '@app/models/test/test-api-result';
import { SpinnerService } from '@app/services/spinner/spinner.service';
import { TestService } from '@app/services/test/test.service';
import { PanelModule } from 'primeng/panel';
import { finalize } from 'rxjs';

@Component({
    selector: 'app-role-1',
    templateUrl: './role1.component.html',
    styleUrls: ['./role1.component.scss'],
    imports: [
        PanelModule
    ],
    standalone: true
})
export class Role1Component implements OnInit {
    public readonly testService = inject(TestService);
    public readonly spinnerService = inject(SpinnerService);

    testApiResult: TestApiResult = { result: 'KO' };

    ngOnInit() {
        this.spinnerService.showSpinner(true)
        this.testService.getTestRole1ApiResult()
            .pipe(
                finalize(() => this.spinnerService.showSpinner(false))
            )
            .subscribe({ next: res => this.testApiResult = res })
    }
}
