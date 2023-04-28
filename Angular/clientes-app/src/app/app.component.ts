import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bienvenido a angular';

  curso : string = 'Curso Spring 3 con angular 7';
  profesor: string= 'Andrés Guzmán';
}
