import {Location} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {PUBLIC_REFRESH_TOKEN_API_URL} from "@app/models/api-url-constants";
import {Authorization} from "@app/models/auth/authorization";
import {AuthService} from "@app/services/auth/auth.service";
import {catchError, map, Observable, of} from "rxjs";

export function currentUserInitialization(location: Location, authService: AuthService, http: HttpClient, router: Router): () => Observable<any> {
  const refreshTokenObs: Observable<Authorization> = http.post(PUBLIC_REFRESH_TOKEN_API_URL, null) as Observable<Authorization>;

  if (!location.path().endsWith('login') && location.path() != '') {
    return () => refreshTokenObs.pipe(
      map((authorization: Authorization) => {
        authService.setAuthorization(authorization);
        return of();
      }),
      catchError((err) => {
        router.navigate(['/login'])
        return of(err)
      })
    );
  } else {
    router.navigate(['/login'])
    return () => of()
  }

}
