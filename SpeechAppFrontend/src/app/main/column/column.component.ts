import { Component, OnInit } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-column',
  templateUrl: './column.component.html',
  styleUrls: ['./column.component.css']
})
export class ColumnComponent implements OnInit {

  showHead: boolean = false;

  ngOnInit(): void {
  }

   constructor(private router: Router) {
    // on route change to '/login', set the variable showHead to false
      router.events.forEach((event) => {
        if (event instanceof NavigationStart) {
          if (event['url'] == '/dashboard' || event['url'] == '/upload' || event['url'] == '/patient') {
            this.showHead = false;
          } else {
            this.showHead = true;
          }
        }
      });
    }
}
