import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>` // <-- Mudamos de templateUrl para template direto aqui!
})
export class App {
  title = 'vaultpay-app';
}