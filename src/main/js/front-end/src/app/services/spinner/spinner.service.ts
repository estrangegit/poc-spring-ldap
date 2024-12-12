import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SpinnerService {

    public showSpinner$: BehaviorSubject<Boolean> = new BehaviorSubject<Boolean>(undefined);

    constructor() { }

    showSpinner(showSpinner: boolean): void {
        this.showSpinner$.next(showSpinner);
    }
}
