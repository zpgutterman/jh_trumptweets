import {Component, ViewChild, ViewChildren, QueryList} from '@angular/core';
import {
  ShapeOptions,
  CircleProgressComponent} from 'angular2-progressbar';

@Component({
  selector: 'progress',
  template: `
  <div class="circle-container">
     <ks-circle-progress [options]="circleOptions"></ks-circle-progress>
   </div>
  `
})
export class AppComponent {

  @ViewChild(CircleProgressComponent) circleComp: CircleProgressComponent;

  private circleOptions: ShapeOptions = {
    color: '#FFEA82',
    trailColor: '#eee',
    trailWidth: 1,
    duration: 1400,
    easing: 'bounce',
    strokeWidth: 6,
    from: { color: '#FFEA82', a: 0 },
    to: { color: '#ED6A5A', a: 1 },
    // Set default step function for all animate calls
    step: function(state: any, circle: any) {
      circle.path.setAttribute('stroke', state.color);
    }
  };



  ngAfterViewInit() {

    this.circleComp.animate(0.8);

  }

}
