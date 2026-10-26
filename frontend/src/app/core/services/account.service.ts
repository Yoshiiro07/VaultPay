import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LedgerEntry {
    id: string;
    type: string;
    amount: number;
    balanceAfter: number;
    timestamp: string;
}

export interface AccountSummary {
    accountNumber: string;
    balance: number;
    entries: LedgerEntry[];
}

@Injectable({
    providedIn: 'root'
})
export class AccountService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/account';

    getAccountSummary(): Observable<AccountSummary> {
        return this.http.get<AccountSummary>(`${this.apiUrl}/summary`);
    }
}