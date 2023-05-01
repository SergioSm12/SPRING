import { ClientesService } from '../services/clientes.service';
import { Cliente } from './../modelo/cliente';
import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
})
export class ClientesComponent implements OnInit{
  clientes : Cliente[]=[];
  
  constructor (private  clienteService : ClientesService){

  }
  
  ngOnInit(): void {
    this.clienteService.getClientes().subscribe(
      clientes => this.clientes=clientes
    );
  }
 
  
 
}
