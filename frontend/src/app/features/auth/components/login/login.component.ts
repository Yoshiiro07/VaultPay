import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="login-container">
      <div class="login-card">
        <h2>VaultPay <span class="badge">Acesso</span></h2>
        <p class="subtitle">Entre com sua conta para acessar o painel</p>

        <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label for="username">Usuário</label>
            <input 
              id="username" 
              type="text" 
              formControlName="username" 
              placeholder="Digite seu usuário"
            />
          </div>

          <div class="form-group">
            <label for="password">Senha</label>
            <input 
              id="password" 
              type="password" 
              formControlName="password" 
              placeholder="Digite sua senha"
            />
          </div>

          @if (errorMessage) {
            <div class="error-msg">{{ errorMessage }}</div>
          }

          <button type="submit" class="btn-submit" [disabled]="loginForm.invalid || isLoading">
            @if (isLoading) {
              Entrando...
            } @else {
              Entrar
            }
          </button>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .login-container { display: flex; justify-content: center; align-items: center; min-height: 100vh; background-color: #0f172a; font-family: sans-serif; }
    .login-card { background: #1e293b; padding: 2.5rem; border-radius: 12px; width: 100%; max-width: 400px; box-shadow: 0 4px 6px rgba(0,0,0,0.3); color: #f8fafc; border-top: 4px solid #38bdf8; }
    h2 { margin-top: 0; color: #38bdf8; font-size: 1.6rem; }
    .badge { font-size: 0.7rem; background: #0369a1; padding: 0.2rem 0.5rem; border-radius: 4px; vertical-align: middle; }
    .subtitle { color: #94a3b8; font-size: 0.85rem; margin-bottom: 1.5rem; }
    .form-group { margin-bottom: 1.2rem; }
    .form-group label { display: block; color: #cbd5e1; font-size: 0.85rem; margin-bottom: 0.4rem; }
    .form-group input { width: 100%; padding: 0.75rem; border-radius: 6px; border: 1px solid #334155; background: #0f172a; color: #fff; box-sizing: border-box; }
    .form-group input:focus { outline: none; border-color: #38bdf8; }
    .btn-submit { width: 100%; background: #38bdf8; color: #0f172a; border: none; padding: 0.75rem; border-radius: 6px; font-weight: bold; cursor: pointer; margin-top: 1rem; transition: background 0.2s; }
    .btn-submit:hover:not(:disabled) { background: #7dd3fc; }
    .btn-submit:disabled { background: #475569; color: #94a3b8; cursor: not-allowed; }
    .error-msg { background: rgba(248, 113, 113, 0.1); color: #f87171; border: 1px solid #f87171; padding: 0.75rem; border-radius: 6px; font-size: 0.85rem; margin-top: 1rem; text-align: center; }
  `]
})
export class LoginComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  isLoading = false;
  errorMessage = '';

  loginForm: FormGroup = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Erro no login', err);
        this.errorMessage = 'Usuário ou senha inválidos.';
        this.isLoading = false;
      }
    });
  }
}