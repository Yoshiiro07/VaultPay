import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <div class="login-card">
        <h2>VaultPay - Acesso</h2>
        <form (ngSubmit)="onLogin()">
          <div class="form-group">
            <label for="email">E-mail</label>
            <input type="email" id="email" [(ngModel)]="email" name="email" required />
          </div>
          
          <div class="form-group">
            <label for="password">Senha</label>
            <input type="password" id="password" [(ngModel)]="password" name="password" required />
          </div>

          <button type="submit" class="btn-submit">Entrar</button>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .login-container { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f4f6f9; }
    .login-card { background: white; padding: 2rem; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 100%; max-width: 400px; }
    .form-group { margin-bottom: 1rem; display: flex; flex-direction: column; }
    .form-group label { margin-bottom: 0.5rem; font-weight: bold; }
    .form-group input { padding: 0.75rem; border: 1px solid #ccc; border-radius: 4px; }
    .btn-submit { width: 100%; padding: 0.75rem; background-color: #007bff; color: white; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
    .btn-submit:hover { background-color: #0056b3; }
  `]
})
export class LoginComponent {
  email = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.login({ email: this.email, passwordHash: this.password }).subscribe({
      next: (response) => {
        // Armazena o token JWT recebido do back-end
        localStorage.setItem('token', response.token);
        // Redireciona para o dashboard/extrato
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Erro ao realizar login', err);
        alert('Credenciais inválidas!');
      }
    });
  }
}