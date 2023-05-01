import { ClientesService } from 'src/app/services/clientes.service';
import { Cliente } from './../../modelo/cliente';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent {
  public cliente: Cliente = new Cliente(0, '', '', '', '');

  public titulo: string = 'Crear cliente';

  constructor(
    private clienteService: ClientesService,
    private router: Router
  ) {}

  public create(): void {
    this.clienteService
      .create(this.cliente)
      .subscribe((response) => this.router.navigate(['/clientes']));
  }
}
