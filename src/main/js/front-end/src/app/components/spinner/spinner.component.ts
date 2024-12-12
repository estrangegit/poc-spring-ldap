import { Component, inject } from "@angular/core";
import { SpinnerService } from "@app/services/spinner/spinner.service";
import { ProgressSpinnerModule } from "primeng/progressspinner";
import { AsyncPipe, NgIf } from "@angular/common";

@Component({
    selector: 'app-spinner',
    templateUrl: './spinner.component.html',
    styleUrls: ['./spinner.component.css'],
    imports: [
        ProgressSpinnerModule,
        NgIf,
        AsyncPipe
    ],
    standalone: true
})
export class SpinnerComponent {
    public spinnerService = inject(SpinnerService)
}
