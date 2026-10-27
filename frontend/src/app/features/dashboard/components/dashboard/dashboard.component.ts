import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService, AccountSummary } from '../../../../core/services/account.service';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <header class="dashboard-header">
        <h1>VaultPay <span class="badge">Digital Banking</span></h1>
        <button class="btn-logout" (click)="authService.logout()">Sair</button>
      </header>

      @if (isLoading) {
        <div class="loading">Carregando dados da conta...</div>
      } @else {
        <div class="dashboard-content">
          <!-- Card de Saldo -->
          <div class="balance-card">
            <p class="label">Saldo Disponível</p>
            <h2 class="amount">{{ summary?.balance | currency:'BRL' }}</h2>
            <p class="account-info">Conta: {{ summary?.accountNumber }}</p>
          </div>

          <!-- Histórico / Livro Razão -->
          <div class="ledger-section">
            <h3>Livro Razão (Extrato Imutável)</h3>
            
            @if (summary?.entries?.length === 0) {
              <p class="no-entries">Nenhuma movimentação registrada.</p>
            } @else {
              <table class="ledger-table">
                <thead>
                  <tr>
                    <th>Data</th>
                    <th>Tipo</th>
                    <th>Valor</th>
                    <th>Saldo Após</th>
                  </tr>
                </thead>
                <tbody>
                  @for (entry of summary?.entries; track entry.id) {
                    <tr>
                      <td>{{ entry.timestamp | date:'dd/MM/yyyy HH:mm' }}</td>
                      <td><span class="tag" [class]="entry.type.toLowerCase()">{{ entry.type }}</span></td>
                      <td [class.positive]="entry.amount > 0" [class.negative]="entry.amount < 0">
                        {{ entry.amount | currency:'BRL' }}
                      </td>
                      <td>{{ entry.balanceAfter | currency:'BRL' }}</td>
                    </tr>
                  }
                </tbody>
              </table>
            }
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .dashboard-container { min-height: 100vh; background-color: #0f172a; color: #f8fafc; font-family: sans-serif; padding: 2rem; }
    .dashboard-header { display: flex; justify-content: space-between; align-items: center; max-width: 900px; margin: 0 auto 2rem auto; border-bottom: 1px solid #334155; padding-bottom: 1rem; }
    h1 { color: #38bdf8; font-size: 1.8rem; margin: 0; }
    .badge { font-size: 0.75rem; background: #0369a1; padding: 0.2rem 0.5rem; border-radius: 4px; vertical-align: middle; }
    .btn-logout { background: #ef4444; color: white; border: none; padding: 0.5rem 1rem; border-radius: 6px; cursor: pointer; font-weight: bold; }
    .btn-logout:hover { background: #dc2626; }
    
    .dashboard-content { max-width: 900px; margin: 0 auto; }
    .balance-card { background: #1e293b; padding: 2rem; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.2); margin-bottom: 2rem; border-left: 5px solid #38bdf8; }
    .balance-card .label { color: #94a3b8; font-size: 0.9rem; margin-bottom: 0.5rem; }
    .balance-card .amount { font-size: 2.5rem; color: #fff; margin: 0 0 0.5rem 0; }
    .account-info { color: #64748b; font-size: 0.85rem; }

    .ledger-section { background: #1e293b; padding: 1.5rem; border-radius: 12px; }
    .ledger-section h3 { margin-top: 0; color: #cbd5e1; border-bottom: 1px solid #334155; padding-bottom: 0.75rem; }
    
    .ledger-table { width: 100%; border-collapse: collapse; margin-top: 1rem; text-align: left; font-size: 0.9rem; }
    .ledger-table th, .ledger-table td { padding: 0.75rem; border-bottom: 1px solid #334155; }
    .ledger-table th { color: #94a3b8; font-weight: 600; }
    
    .positive { color: #4ade80; }
    .negative { color: #f87171; }
    .no-entries { color: #64748b; text-align: center; padding: 2rem; }
    .loading { text-align: center; color: #94a3b8; margin-top: 4rem; font-size: 1.2rem; }
  `]
})
export class DashboardComponent implements OnInit {
  // Tipagem explícita adicionada para evitar o erro de tipo 'unknown'
  private accountService: AccountService = inject(AccountService);
  public authService: AuthService = inject(AuthService);

  summary: AccountSummary | null = null;
  isLoading = true;

  ngOnInit() {
    this.accountService.getAccountSummary().subscribe({
      next: (data) => {
        this.summary = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar dados da conta', err);
        this.isLoading = false;
      }
    });
  }
}