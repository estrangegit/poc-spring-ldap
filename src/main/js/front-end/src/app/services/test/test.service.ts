import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TEST_ROLE1_API_URL, TEST_ROLE1_CUSTOM_TEST_EXCEPTION_API_URL, TEST_ROLE2_API_URL } from '@app/models/api-url-constants';
import { TestApiResult } from '@app/models/test/test-api-result';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class TestService {

    private readonly http = inject(HttpClient)

    constructor() { }

    getTestRole1ApiResult(): Observable<TestApiResult> {
        return this.http.get(TEST_ROLE1_API_URL) as Observable<TestApiResult>;
    }

    getRole1TestCustomExceptionApiResult(): Observable<TestApiResult> {
        return this.http.get(TEST_ROLE1_CUSTOM_TEST_EXCEPTION_API_URL) as Observable<TestApiResult>;
    }

    getTestRole2ApiResult(): Observable<TestApiResult> {
        return this.http.get(TEST_ROLE2_API_URL) as Observable<TestApiResult>;
    }
}
