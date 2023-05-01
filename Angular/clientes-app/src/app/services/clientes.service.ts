import { Cliente } from './../modelo/cliente';
import { CLIENTES } from './../clientes/clientes.json';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ClientesService {
  private urlEndpoint: string = 'http://localhost:8080/api/clientes';
  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {}

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get<Cliente[]>(this.urlEndpoint);
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.urlEndpoint, cliente, {headers: this.httpHeaders});
  }

  update(id: number, tarea: any) {
    return this.http.put(this.urlEndpoint + '/' + id, tarea);
  }

  delete(id: number) {
    return this.http.delete(this.urlEndpoint + '/' + id);
  }
}
