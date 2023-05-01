import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Cliente } from 'src/app/modelo/cliente';
import { ClientesService } from 'src/app/services/clientes.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  public cliente: Cliente = new Cliente(0, '', '', '', '');

  public titulo: string = 'Crear cliente';

  constructor(
    private clienteService: ClientesService,
    private router: Router,
    private activateRoute: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.cargarCliente();
  }

  cargarCliente(): void {
    this.activateRoute.params.subscribe((params) => {
      let id = params['id'];
      if (id) {
        this.clienteService
          .getCliente(id)
          .subscribe((cliente) => (this.cliente = cliente));
      }
    });
  }

  create(): void {
    this.clienteService.create(this.cliente).subscribe((response) => {
      this.router.navigate(['/clientes']);
      swal.fire(
        'Nuevo Cliente',
        `Cliente ${this.cliente.nombre} creado con éxito!`,
        'success'
      );
    });
  }

  update(): void {
    this.clienteService.update(this.cliente).subscribe((json) => {
      this.router.navigate(['/clientes']);
      swal.fire(
        'Cliente Actualizado',
        `Cliente ${json.cliente.nombre} Actualizado con exito`,
        'success'
      );
    });
  }
}
