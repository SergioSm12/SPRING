import { CLIENTES } from './../clientes/clientes.json';
import { Injectable } from '@angular/core';
import { Cliente } from '../modelo/cliente';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class ClientesService {
  constructor() {}

  getClientes(): Observable<Cliente[]> {
    return of(CLIENTES);
  }
  
}
