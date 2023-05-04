import { Router } from '@angular/router';
import { Cliente } from './../modelo/cliente';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import Swal from 'sweetalert2';
import { formatDate } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class ClientesService {
  private urlEndpoint: string = 'http://localhost:8080/api/clientes';
  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) {}

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndpoint).pipe(
      tap((response) => {
        let clientes = response as Cliente[];
        console.log('Clientes Service: tap 1')
        clientes.forEach((cliente) => {
          console.log(cliente.nombre);
        });
      }),

      map((response) => {
        let clientes = response as Cliente[];
        return clientes.map((cliente) => {
          //Convertir los nombres a mayusculas
          cliente.nombre = cliente.nombre.toUpperCase();
          /*cliente.createAt = formatDate(
            cliente.createAt,
            'EEEE dd, MMMM yyyy',
            'es-CO'
          );*/
          return cliente;
        });
      }),
      tap((response) => {
        console.log('Clientes Service: tap 2')
        response.forEach((cliente) => {
          console.log(cliente.nombre);
        });
      })
    );
  }

  create(cliente: Cliente): Observable<Cliente> {
    return this.http
      .post<Cliente>(this.urlEndpoint, cliente, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(() => e);
          }

          console.error(e.error.mensaje);
          Swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  getCliente(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`).pipe(
      catchError((e) => {
        this.router.navigate(['/clientes']);
        console.error(e.error.mensaje);
        Swal.fire('Error al editar', e.error.mensaje, 'error');
        return throwError(() => e);
      })
    );
  }

  update(cliente: Cliente): Observable<any> {
    return this.http
      .put<Cliente>(`${this.urlEndpoint}/${cliente.id}`, cliente, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(() => e);
          }

          console.error(e.error.mensaje);
          Swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  delete(id: number): Observable<Cliente> {
    return this.http
      .delete<Cliente>(`${this.urlEndpoint}/${id}`, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          console.error(e.error.mensaje);
          Swal.fire('Error al eliminar cliente', e.error.mensaje, 'error');
          return throwError(() => e);
        })
      );
  }
}
