<!-- markdownlint-disable MD024 -->
# Angular

## 1. Architecture

Angular is a platform and Framework for building client apps in HTML + TS.
Basic building blocks of an ng app are NgModules, which:

- provide a compilation context to components,
- group related code into functional sets.

An app has atleast a root NgModule (AppModule => app.module.ts) (for bootstrapping) then optional NgModules.

Components define views and use services which provide specific view non-related functionalities like fetching, calculations, log and are injected into components as dependencies.

Svs and Comps are simple clasess which are decorated. Decorators mark their types and provide metadata that tell ng how to use them.

### & NgModules Modules

NgModules import functionality from other modules as well as export.
An NgModule is a class decorated by the @NgModule() decorator.
This decorator is a function that takes a single root paramater object whose props define the component.

    import { NgModule }      from '@angular/core';
    import { BrowserModule } from '@angular/platform-browser';

    @NgModule({
      imports:      [ Other NgModules whose exported classes are needed by component templates declared in this NgModule ],
      providers:    [ Services that this NgModule contributes to the global collection of services; they become accessible in all parts of the app. (You can also specify providers at the component level, which is often preferred.) ],
      declarations: [ components, directives, and pipes that belong to this NgModule, each must be declared in exactly 1 NgModule to be usable],
      exports:      [ Declarations that should be visible and usable in the component templates of other NgModules ],
      bootstrap:    [ The main application view, called the root component, which hosts all other app views. Only the root NgModule should set the bootstrap property ]
    })
    export class AppModule { }
    // AppModule not must export because no NgModule imports from the App.module.ts

Angular libraries are collection of js modules each lib name is prefixed @angular, are installed using npm.

      import { Component } from '@angular/core';
      import { BrowserModule } from '@angular/platform-browser';

### Components

A comp + template = view
Each app has atleast a root component that connects the comp hieraryhy to the DOM.
A template combines HTML with ng markup that can modify HTML elements before they are displayed.

@Component metadata

    @Component({
      selector:    'app-hero-list',// A CSS selector that tells Angular to create and insert an instance of this component wherever it finds the corresponding tag
      templateUrl: './hero-list.component.html',
      template: 'The HTML markup is written directly here (inline) instead of using a separate file with templateUrl',
      providers:  [ An Array of Service Providers that this component requires]
    })
    export class HeroListComponent implements OnInit {
    }

They transform values before display

DI

## 2. Components & Templates

### [Template Syntax](https://angular.io/guide/template-syntax)

One-way from data source to view target

    {{expression}}

    [target]="expression"
    bind-target="expression"

One-way from view target to data source

    (target)="statement"
    on-target="statement"

Two-way-binding

    [(target)]="expression"
    bindon-target="expression"

### [User Input](https://angular.io/guide/user-input)

    @Component({
      selector: 'app-key-up3',
      template: `
        <input #box 
        (keyup)="onKeyUpUsingEvent($event)
        (keyup.enter)="onEnter(box.value)
        (blur)="update(box.value)">
        <p>{{value}}</p>
      `
    })
    export class KeyUpComponent_v3 {
      value = '';
      onKeyUpUsingEvent(event: any) { this.values += event.target.value + ' | ';}
      onKeyUpUsingEvent(event: KeyboardEvent) { this.values += event.target.value + ' | ';}
      onEnter(value: string) { this.value = value; }
      update(value: string) { this.value = value; } // onblur
    }

### [Lifecycle Hooks](https://angular.io/guide/lifecycle-hooks)

#### ngOnChanges (Called before ngOnInit and whenever a data-bound input properties change)

    ngOnChanges(changes: SimpleChanges) {
      for (let propName in changes) {
        let chng = changes[propName];
        let cur  = JSON.stringify(chng.currentValue);
        let prev = JSON.stringify(chng.previousValue);
        this.changeLog.push(`${propName}: currentValue = ${cur}, previousValue = ${prev}`);
      }
    }

ngOnInit (Called once, after the first ngOnChanges, Initialize the directive/component)

#### ngDoCheck (Called immediately after ngOnChanges and ngOnInit, Detect and act upon changes that Angular can't or won't detect on its own

    ngDoCheck() {

      if (this.hero.name !== this.oldHeroName) {
        this.changeDetected = true;
        this.changeLog.push(`DoCheck: Hero name changed to "${this.hero.name}" from "${this.oldHeroName}"`);
        this.oldHeroName = this.hero.name;
      }

      if (this.power !== this.oldPower) {
        this.changeDetected = true;
        this.changeLog.push(`DoCheck: Power changed to "${this.power}" from "${this.oldPower}"`);
        this.oldPower = this.power;
      }

      if (this.changeDetected) {
          this.noChangeCount = 0;
      } else {
          // log that hook was called when there was no relevant change.
          let count = this.noChangeCount += 1;
          let noChangeMsg = `DoCheck called ${count}x when no change to hero or power`;
          if (count === 1) {
            // add new "no change" message
            this.changeLog.push(noChangeMsg);
          } else {
            // update last "no change" message
            this.changeLog[this.changeLog.length - 1] = noChangeMsg;
          }
      }

      this.changeDetected = false;
    }

ngAfterContentInit (Called once after the first ngDoCheck after Angular projects external content into the component's view / the view that a directive is in)

ngAfterContentChecked (Called after the ngAfterContentInit and every subsequent ngDoCheck)

ngAfterViewInit (Called once after the first ngAfterContentChecked, initializes the component's views and child views)

ngAfterViewChecked (Called after the ngAfterViewInit and every subsequent ngAfterContentChecked)

ngOnDestroy (Called just before Angular destroys the directive/component, Here you should Unsubscribe Observables and detach event handlers to avoid memory leaks)

### Component Interaction

#### Pass data from parent to child with input binding

child.component.ts

    import { Component, Input } from '@angular/core';

    @Component({
      selector: 'app-hero-child',
      template: `
        <h3>{{hero.name}} says:</h3>
        <p>I, {{hero.name}}, am at your service, {{renamedMaster}}.</p>
        <h3>"{{name}}"</h3>
      `
    })
    export class HeroChildComponent {
      private _name = '';

      @Input() hero: Hero;
      @Input('master') renamedMaster: string;

      // Using set to modify the input before using it
      @Input()
      set name(name: string) {
        this._name = (name && name.trim()) || '<no name set>';
      }
      get name(): string { return this._name; }
    }

parent.component.ts

    @Component({
      template: `
        <app-hero-child 
          [hero]="hero"
          [master]="master">
        </app-hero-child>
      `
    })
    export class ParentComponent {
      heroes: Array<Hero>   = HEROES;
      master: Hero          = 'Master';
      names = Array<String> = ['Dr IQ', '   ', '  Bombasto  '];
    }

#### Intercept input property changes with ngOnChanges

Detect and act upon input changes using ngOnChanges() method of the OnChanges lifecycle hook interface.

parent.component.ts

    import { Component } from '@angular/core';

    @Component({
      template: `
        <h2>Source code version</h2>
        <button (click)="newMinor()">New minor version</button>
        <button (click)="newMajor()">New major version</button>
        <app-child [major]="major" [minor]="minor"></app-child>
      `
    })
    export class VersionParentComponent {
      major = 1;
      minor = 23;

      newMinor() {
        this.minor++;
      }

      newMajor() {
        this.major++;
        this.minor = 0;
      }
    }

child.component.ts

    import { Component, Input, OnChanges, SimpleChange } from '@angular/core';

    @Component({
      selector: 'app-child',
      template: `
        <h3>Version {{major}}.{{minor}}</h3>
        <h4>Change log:</h4>
        <ul>
          <li *ngFor="let change of changeLog">{{change}}</li>
        </ul>
      `
    })
    export class ChildComponent implements OnChanges {
      @Input() major: number;
      @Input() minor: number;
      changeLog: string[] = [];

      ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
        let log: string[] = [];

        for (let propName in changes) {
          let changedProp = changes[propName];
          let to = JSON.stringify(changedProp.currentValue);
          if (changedProp.isFirstChange()) {
            log.push(`Initial value of ${propName} set to ${to}`);
          } else {
            let from = JSON.stringify(changedProp.previousValue);
            log.push(`${propName} changed from ${from} to ${to}`);
          }
        }
        this.changeLog.push(log.join(', '));
      }
    }

#### Parent listens for child event

parent.component.ts

    import { Component }      from '@angular/core';

    @Component({
      template: `
        <h2>Should mankind colonize the Universe?</h2>
        <h3>Agree: {{agreed}}, Disagree: {{disagreed}}</h3>
        <app-child *ngFor="let voter of voters"
          [name]="voter"
          (voted)="onVoted($event)"> <-- bind an output to the child
        </app-child>
      `
    })
    export class VoteTakerComponent {
      agreed = 0;
      disagreed = 0;
      voters = ['Narco', 'Celeritas', 'Bombasto'];

      onVoted(agreed: boolean) {
        agreed ? this.agreed++ : this.disagreed++;
      }
    }

child.component.ts

    import { Component, EventEmitter, Input, Output } from '@angular/core';

    @Component({
      selector: 'app-child',
      template: `
        <h4>{{name}}</h4>
        <button (click)="vote(true)"  [disabled]="didVote">Agree</button>
        <button (click)="vote(false)" [disabled]="didVote">Disagree</button>
      `
    })
    export class VoterComponent {
      @Input()  name: string;
      @Output() voted = new EventEmitter<boolean>(); <-- declare the output
      didVote = false;

      vote(agreed: boolean) {
        this.voted.emit(agreed); <-- emit change
        this.didVote = true;
      }
    }  

#### Parent interacts with child via local variable

Parent accesses the child's properties by referencing the child using a template variable
parent only has access to the child in the template ubt not in the component.

parent.component.ts.

    import { Component }                from '@angular/core';
    import { CountdownTimerComponent }  from './countdown-timer.component';

    @Component({
      template: `
        <button (click)="timer.start()">Start</button>
        <button (click)="timer.stop()">Stop</button>
        <div class="seconds">{{timer.seconds}}</div>
        <app-countdown-timer #timer></app-countdown-timer>
      `,
    })
    export class CountdownLocalVarParentComponent { }

child.component.ts

    import { Component, OnDestroy, OnInit } from '@angular/core';

    @Component({
      selector: 'app-countdown-timer',
      template: '<p>{{message}}</p>'
    })
    export class CountdownTimerComponent implements OnInit, OnDestroy {

      intervalId = 0;
      message = '';
      seconds = 11;

      clearTimer() { clearInterval(this.intervalId); }
      ngOnInit()    { this.start(); }
      ngOnDestroy() { this.clearTimer(); }
      start() { this.countDown(); }
      stop()  {
        this.clearTimer();
        this.message = `Holding at T-${this.seconds} seconds`;
      }

      private countDown() {
        this.clearTimer();
        this.intervalId = window.setInterval(() => {
          this.seconds -= 1;
          if (this.seconds === 0) {
            this.message = 'Blast off!';
          } else {
            if (this.seconds < 0) { this.seconds = 10; } // reset
            this.message = `T-${this.seconds} seconds and counting`;
          }
        }, 1000);
      }
    }

#### Parent calls an @ViewChild()

parent.component.ts

    import { Component, AfterViewInit, ViewChild } from '@angular/core';
    import { CountdownTimerComponent }  from './countdown-timer.component';

    @Component({
      template: `
        <button (click)="start()">Start</button>
        <button (click)="stop()">Stop</button>
        <div class="seconds">{{ seconds() }}</div>
        <app-countdown-timer></app-countdown-timer>
      `,
    })
    export class CountdownViewChildParentComponent implements AfterViewInit {

      @ViewChild(CountdownTimerComponent, {static: false})
      private timerComponent: CountdownTimerComponent;

      seconds() { return 0; }

      ngAfterViewInit() {
        setTimeout( () => this.seconds = () => this.timerComponent.seconds, 0);
      }

      start() { this.timerComponent.start(); }
      stop() { this.timerComponent.stop(); }
    }

#### Parent and children communicate via a service

mediator.service.ts

    import { Injectable } from '@angular/core';
    import { Subject }    from 'rxjs';

    @Injectable()
    export class MissionService {

      // Observable string sources
      private missionAnnouncedSource = new Subject<string>();
      private missionConfirmedSource = new Subject<string>();

      // Observable string streams
      missionAnnounced$ = this.missionAnnouncedSource.asObservable();
      missionConfirmed$ = this.missionConfirmedSource.asObservable();

      // Service message commands
      announceMission(mission: string) {
        this.missionAnnouncedSource.next(mission);
      }

      confirmMission(astronaut: string) {
        this.missionConfirmedSource.next(astronaut);
      }
    }

missioncontrol.component.ts

    import { Component }          from '@angular/core';
    import { MissionService }     from './mission.service';

    @Component({
      template: `
        <button (click)="announce()">Announce mission</button>
        <app-astronaut *ngFor="let astronaut of astronauts"
          [astronaut]="astronaut">
        </app-astronaut>

        <h3>History</h3>
        <ul>
          <li *ngFor="let event of history">{{event}}</li>
        </ul>
      `,
      providers: [MissionService]
    })
    export class MissionControlComponent {
      astronauts = ['Lovell', 'Swigert', 'Haise'];
      history: string[] = [];
      missions = ['Fly to the moon!',
                  'Fly to mars!',
                  'Fly to Vegas!'];
      nextMission = 0;

      constructor(private missionService: MissionService) {
        missionService.missionConfirmed$.subscribe(
          astronaut => {
            this.history.push(`${astronaut} confirmed the mission`);
          });
      }

      announce() {
        let mission = this.missions[this.nextMission++];
        this.missionService.announceMission(mission);
        this.history.push(`Mission "${mission}" announced`);
        if (this.nextMission >= this.missions.length) { this.nextMission = 0; }
      }
    }

child.component.ts

    import { Component, Input, OnDestroy } from '@angular/core';
    import { MissionService } from './mission.service';
    import { Subscription }   from 'rxjs';

    @Component({
      selector: 'app-astronaut',
      template: `
        <p>
          {{astronaut}}: <strong>{{mission}}</strong>
          <button
            (click)="confirm()"
            [disabled]="!announced || confirmed">
            Confirm
          </button>
        </p>
      `
    })
    export class AstronautComponent implements OnDestroy {
      @Input() astronaut: string;
      mission = '<no mission announced>';
      confirmed = false;
      announced = false;
      subscription: Subscription;

      constructor(private missionService: MissionService) {
        this.subscription = missionService.missionAnnounced$.subscribe(
          mission => {
            this.mission = mission;
            this.announced = true;
            this.confirmed = false;
        });
      }

      confirm() {
        this.confirmed = true;
        this.missionService.confirmMission(this.astronaut);
      }

      ngOnDestroy() {
        // prevent memory leak when component destroyed
        this.subscription.unsubscribe();
      }
    }

### Component Style

Component scoped style, not inhrited by children, nor parent

    @Component({
      styles:[
        "a {background: red; padding: 1rem;}",
        "h1 {color: yellow}",
        ":host {border: solid 1rem green}",
        ":host (.hover) {padding: 4rem; background: white;}" <-- Style of self, eg set the border of card
      ]
    })
    export class MyComponent

Template inline styles

    @Component({
      template: `
        <style>
          button {
            background-color: white;
            border: 1px solid #777;
          }
        </style>
        <h3>Controls</h3>
        <button (click)="activate()">Activate</button>
      `
    })

Template link tags

    @Component({
      selector: 'app-hero-team',
      template: `
        <!-- We must use a relative URL so that the AOT compiler can find the stylesheet -->
        <link rel="stylesheet" href="../assets/hero-team.component.css">
        <h3>Team</h3>
        <ul>
          <li *ngFor="let member of hero.team">
            {{member}}
          </li>
        </ul>`
    })

CSS @imports

    /* The AOT compiler needs the `./` to show that this is local */
    @import './hero-details-box.css';

Non-CSS style files

    @Component({
      styleUrls: ['./app.component.scss', 'path-to-style.less']
    })

### [Dynamic Components](https://angular.io/guide/dynamic-component-loader)

Components created/loaded at runtime.

#### app.module.ts

    @NgModule({
      providers:       [AdService],
      declarations:    [ AppComponent, AdBannerComponent, HeroJobAdComponent, HeroProfileComponent, AdDirective ],
      entryComponents: [ HeroJobAdComponent, HeroProfileComponent ], <-- declare the dynamic components
      bootstrap:       [ AppComponent ]
    })
    export class AppModule {
      constructor() {}
    }

#### app.component.ts

    import { Component, OnInit } from '@angular/core';
    import { AdService }         from './ad.service';
    import { AdItem }            from './ad-item';

    @Component({
      template: `
        <div>
          <app-ad-banner [ads]="ads"></app-ad-banner>
        </div>
      `
    })
    export class AppComponent implements OnInit {
      ads: AdItem[];

      constructor(private adService: AdService) {}

      ngOnInit() {
        this.ads = this.adService.getAds();
      }
    }

#### ad-item.ts

    import { Type } from '@angular/core';

    export class AdItem {
      constructor(public component: Type<any>, public data: any) {}
    }

#### ad.service.ts

    import { Injectable }           from '@angular/core';
    import { HeroJobAdComponent }   from './hero-job-ad.component';
    import { HeroProfileComponent } from './hero-profile.component';
    import { AdItem }               from './ad-item';

    @Injectable()
    export class AdService {
      getAds() {
        return [
          new AdItem(HeroProfileComponent, {name: 'Bombasto', bio: 'Brave as they come'}),
          new AdItem(HeroProfileComponent, {name: 'Dr IQ', bio: 'Smart as they come'}),
          
          new AdItem(HeroJobAdComponent,   {headline: 'Hiring for several positions', body: 'Submit your resume today!'}),
          new AdItem(HeroJobAdComponent,   {headline: 'Openings in all departments', body: 'Apply today'}),
        ];
      }
    }

#### hero-job-ad.component.ts

    import { Component, Input } from '@angular/core';
    import { AdComponent }      from './ad.component';

    @Component({
      template: `
        <div class="job-ad">
          <h4>{{data.headline}}</h4>
          {{data.body}}
        </div>
      `
    })
    export class HeroJobAdComponent implements AdComponent {
      @Input() data: any;
    }

#### hero-profile.component.ts

    import { Component, Input }  from '@angular/core';
    import { AdComponent }       from './ad.component';

    @Component({
      template: `
        <div class="hero-profile">
          <h3>Featured Hero Profile</h3>
          <h4>{{data.name}}</h4>
          <p>{{data.bio}}</p>
          <strong>Hire this hero today!</strong>
        </div>
      `
    })
    export class HeroProfileComponent implements AdComponent {
      @Input() data: any;
    }

#### ad-banner.component.ts

    import { Component, Input, OnInit, ViewChild, ComponentFactoryResolver, OnDestroy } from '@angular/core';
    import { AdDirective } from './ad.directive'; <-- anchor point
    import { AdItem }      from './ad-item'; <-- data type
    import { AdComponent } from './ad.component'; <-- interface

    @Component({
      selector: 'app-ad-banner',
      template: `
                  <div class="ad-banner-example">
                    <h3>Advertisements</h3>
                    <ng-template ad-host></ng-template>
                  </div>
                `
    })
    export class AdBannerComponent implements OnInit, OnDestroy {
      @Input() ads: AdItem[];
      currentAdIndex = -1;
      @ViewChild(AdDirective, {static: true}) adHost: AdDirective;
      interval: any;

      constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

      ngOnInit() {
        this.loadComponent();
        this.getAds();
      }

      ngOnDestroy() {
        clearInterval(this.interval);
      }

      loadComponent() {
        this.currentAdIndex = (this.currentAdIndex + 1) % this.ads.length;

        const adItem            = this.ads[this.currentAdIndex],
              componentFactory  = this.componentFactoryResolver.resolveComponentFactory(adItem.component),
              viewContainerRef  = this.adHost.viewContainerRef;

        viewContainerRef.clear();

        const componentRef = viewContainerRef.createComponent(componentFactory);

        (<AdComponent>componentRef.instance).data = adItem.data;
      }

      getAds() {
        this.interval = setInterval(() => {
          this.loadComponent();
        }, 3000);
      }
    }

#### ad.directive.t

    import { Directive, ViewContainerRef } from '@angular/core';

    @Directive({
      selector: '[ad-host]',
    })
    export class AdDirective {
      constructor(public viewContainerRef: ViewContainerRef) {}
    }

#### ad.component.ts

    export interface AdComponent {
      data: any;
    }

### Angular Elements (Missing)

### Directives

There are 3 types:

- Component-directive(Component)
- Attribute-directives(Change the appearance or behavoir of a DOM, Component, Directive)
- [Structural-directives](https://angular.io/guide/structural-directives) Changes the DOM layout by ++,-- DOM elements eg NgFor, NgIf

#### Attribute directive

    ng generate directive highlight

highlight.directive.ts

    /* tslint:disable:member-ordering */
    import { Directive, ElementRef, HostListener, Input } from '@angular/core';

    @Directive({
      selector: '[appHighlight]'
    })
    export class HighlightDirective {

      constructor(private el: ElementRef) { }

      @Input() defaultColor: string;

      @Input('appHighlight') highlightColor: string;

      @HostListener('mouseenter') onMouseEnter() {
        this.highlight(this.highlightColor || this.defaultColor || 'red');
      }

      @HostListener('mouseleave') onMouseLeave() {
        this.highlight(null);
      }

      private highlight(color: string) {
        this.el.nativeElement.style.backgroundColor = color;
      }
    }

app.component.ts

    import { Component } from '@angular/core';

    @Component({
      template: `
      <h1>My First Attribute Directive</h1>
        <h4>Pick a highlight color</h4>
        <div>
          <input type="radio" name="colors" (click)="color='lightgreen'">Green
          <input type="radio" name="colors" (click)="color='yellow'">Yellow
          <input type="radio" name="colors" (click)="color='cyan'">Cyan
        </div>

        <p [appHighlight]="color">Highlight me!</p>
        <p [appHighlight]="color" defaultColor="violet">Highlight me too! </p>
        `
    })
    export class AppComponent {
      color: string;
    }


    <h4>Pick a highlight color</h4>
    <div>
      <input type="radio" name="colors" (click)="color='lightgreen'">Green
      <input type="radio" name="colors" (click)="color='yellow'">Yellow
      <input type="radio" name="colors" (click)="color='cyan'">Cyan
    </div>

    <p [appHighlight]="color">Highlight me!</p>
    <p [appHighlight]="color" defaultColor="violet">Highlight me too! </p>

app/app.module.ts

    import { AppComponent } from './app.component';
    import { HighlightDirective } from './highlight.directive';

    @NgModule({
      declarations: [
        AppComponent,
        HighlightDirective  <-- directived too must be declared
      ],
      bootstrap: [ AppComponent ]
    })
    export class AppModule { }

#### [Structural Directive](https://angular.io/guide/structural-directives)

app.component.ts

    import { Component } from '@angular/core';

    import { Hero, heroes } from './hero';

    @Component({
      selector: 'app-root',
      templateUrl: './app.component.html',
      styleUrls: [ './app.component.css' ]
    })
    export class AppComponent {
      heroes = heroes;
      hero = this.heroes[0];

      condition = false;
      logs: string[] = [];
      showSad = true;
      status = 'ready';

      trackById(index: number, hero: Hero): number { return hero.id; }
    }

### [Pipes](https://angular.io/guide/pipes)

-[https://angular.io/api?type=pipe](https://angular.io/api?type=pipe)

## 3. [Forms](https://angular.io/guide/forms-overview)

### [Reactive Forms](https://angular.io/guide/reactive-forms#reactive-forms-api)

A model driven way of handling forms whose input change over time. No need for data-binding, uses FromControl, FormGroup instead

app.module.ts

    import { ReactiveFormsModule } from '@angular/forms'; <-- import the library

    @NgModule({
      imports: [
        ReactiveFormsModule  <-- Import this to all of the module's components
      ],
    })
    export class AppModule { }

#### FormControl (Controls a single input=FormControl)

name-editor.component.ts

    import { Component } from '@angular/core';
    import { FormControl } from '@angular/forms';

    @Component({
      selector: 'app-name-editor',
      template: `
        <label>
          Name:
          <input type="text" [formControl]="name">
        </label>

        <p>Value: {{ name.value }}</p>

        <p>
          <button (click)="updateName()">Update Name</button>
        </p>
      `
    })
    export class NameEditorComponent {
      name = new FormControl('');
      updateName() {
        this.name.setValue('Nancy'); <-- Replace the value of a form control
      }
    }

#### FormGroup (Controls a form FormGroup)

    // profile-editor.component.ts (imports)
    
    import { Component } from '@angular/core';
    import { FormControl, FormGroup } from '@angular/forms';

    @Component({
      template: `
        <form [formGroup]="profileForm" (ngSubmit)="onSubmit()">
          <label>
            First Name:
            <input type="text" formControlName="firstName">
          </label>

          <label>
            Last Name:
            <input type="text" formControlName="lastName">
          </label>
          <button type="submit" [disabled]="!profileForm.valid">Submit</button>
        </form>
      `
    })
    export class ProfileEditorComponent {
      profileForm = new FormGroup({
        firstName: new FormControl(''),
        lastName: new FormControl(''),
      });

      onSubmit() {
        console.warn(this.profileForm.value); 
      }
    }

#### Nested FromGroups, FormControls

profile-editor.component.ts

    import { Component } from '@angular/core';
    import { FormGroup, FormControl } from '@angular/forms';

    @Component({
      template`

        <label>
          FirstName:
          <input type="text" formControlName="firstName">
        </label>

        <label>
          LastName:
          <input type="text" formControlName="lastName">
        </label>
        
        <div formGroupName="address">
          <h3>Address</h3>

          <label>
            Street:
            <input type="text" formControlName="street">
          </label>

          <label>
            City:
            <input type="text" formControlName="city">
          </label>
          
          <label>
            State:
            <input type="text" formControlName="state">
          </label>

          <label>
            Zip Code:
            <input type="text" formControlName="zip">
          </label>
        </div> 
      `
    })
    export class ProfileEditorComponent {
      
      profileForm = new FormGroup({
        firstName: new FormControl(''),
        lastName: new FormControl(''),
        address: new FormGroup({
          street: new FormControl(''),
          city: new FormControl(''),
          state: new FormControl(''),
          zip: new FormControl('')
        })
      });

      updateProfile() { <<-- Updating the model partially
        this.profileForm.patchValue({
          firstName: 'Nancy',
          address: {
            street: '123 Drew Street'
          }
        });
      }
    }

#### FormBuilder

    import { FormBuilder } from '@angular/forms';

With form builder

    @Component({
      template='
        <form>
        </form>
      ',
    })
    export class ProfileEditorComponent {
      profileForm = this.fb.group({
        firstName: [''],
        lastName: [''],
        address: this.fb.group({
          street: [''],
          city: [''],
          state: [''],
          zip: ['']
        }),
      });

      constructor(private fb: FormBuilder) { }
    }

Without Form builder

    @Component({})
    export class ProfileEditorComponent {
      profileForm = new FormGroup({
        firstName: new FormControl(''),
        lastName: new FormControl(''),
        address: new FormGroup({
          street: new FormControl(''),
          city: new FormControl(''),
          state: new FormControl(''),
          zip: new FormControl('')
        })
      });
    }

#### [Form Validation](https://angular.io/guide/form-validation)

    import { FormBuilder } from '@angular/forms';
    import { Validators } from '@angular/forms';

    With form builder

        @Component({
          template: '<input type="text" formControlName="firstName" required>'
        })
        export class ProfileEditorComponent {
          profileForm = this.fb.group({
            firstName: [''],
            lastName: [''],
            address: this.fb.group({
              street: [''],
              city: [''],
              state: [''],
              zip: ['']
            }),
          });

          constructor(private fb: FormBuilder) { }
        }

### [Template Driven Forms](https://angular.io/guide/forms)

Uses  [(ngModel)] for input-binding

app.module.ts

    import { NgModule }      from '@angular/core';
    import { FormsModule }   from '@angular/forms';

    @NgModule({
      imports: [
        FormsModule
      ],
    })
    export class AppModule { } 

hero.model.s

    export class Hero {

      constructor(
        public id: number,
        public name: string,
        public power: string,
        public alterEgo?: string
      ) {  }

    }

hero-form.component.ts

    import { Component } from '@angular/core';
    import { Hero }    from '../hero';

    @Component({
        template: `
          <div class="container">
            <div [hidden]="submitted">
              <h1>Hero Form</h1>
              <form (ngSubmit)="onSubmit()" #heroForm="ngForm">
                <div class="form-group">
                  <label for="name">Name</label>
                  <input type="text" class="form-control" id="name"
                        required
                        [(ngModel)]="model.name" name="name"
                        #name="ngModel">
                  <div [hidden]="name.valid || name.pristine"
                      class="alert alert-danger">
                    Name is required
                  </div>
                </div>

                <div class="form-group">
                  <label for="alterEgo">Alter Ego</label>
                  <input type="text" class="form-control" id="alterEgo"
                        [(ngModel)]="model.alterEgo" name="alterEgo">
                </div>

                <div class="form-group">
                  <label for="power">Hero Power</label>
                  <select class="form-control" id="power"
                          required
                          [(ngModel)]="model.power" name="power"
                          #power="ngModel">
                    <option *ngFor="let pow of powers" [value]="pow">{{pow}}</option>
                  </select>
                  <div [hidden]="power.valid || power.pristine" class="alert alert-danger">
                    Power is required
                  </div>
                </div>

                <button type="submit" class="btn btn-success" [disabled]="!heroForm.form.valid">Submit</button>
                <button type="button" class="btn btn-default" (click)="newHero(); heroForm.reset()">New Hero</button>
              </form>
            </div>

            <div [hidden]="!submitted">
              <h2>You submitted the following:</h2>
              <div class="row">
                <div class="col-xs-3">Name</div>
                <div class="col-xs-9">{{ model.name }}</div>
              </div>
              <div class="row">
                <div class="col-xs-3">Alter Ego</div>
                <div class="col-xs-9">{{ model.alterEgo }}</div>
              </div>
              <div class="row">
                <div class="col-xs-3">Power</div>
                <div class="col-xs-9">{{ model.power }}</div>
              </div>
              <br>
              <button class="btn btn-primary" (click)="submitted=false">Edit</button>
            </div>
          </div>
        `
    })
    export class HeroFormComponent {

      powers = ['Really Smart', 'Super Flexible', 'Super Hot', 'Weather Changer'];

      model = new Hero(18, 'Dr IQ', this.powers[0], 'Chuck Overstreet');

      submitted = false;

      onSubmit() { this.submitted = true; }

      newHero() {
        this.model = new Hero(42, '', '');
      }
    }

#### [Validation](https://angular.io/guide/form-validation#template-driven-validation)

    <input id="name" name="name" class="form-control"
          required minlength="4" appForbiddenName="bob"
          [(ngModel)]="hero.name" #name="ngModel" >

    <div *ngIf="name.invalid && (name.dirty || name.touched)"
        class="alert alert-danger">

      <div *ngIf="name.errors.required">
        Name is required.
      </div>
      <div *ngIf="name.errors.minlength">
        Name must be at least 4 characters long.
      </div>
      <div *ngIf="name.errors.forbiddenName">
        Name cannot be Bob.
      </div>

    </div>

### [Dynamic Forms](https://angular.io/guide/dynamic-form)

Creating forms dynamically based on metadata that describe the business object model.

#### question-template.ts

    export class Question <T> {
      value     : T;
      key       : string;
      label     : string;
      required  : boolean;
      order     : number;
      type      : string;

      constructor(options: {
          value?    : T,
          key?      : string,
          label?    : string,
          required? : boolean,
          order?    : number,
          type?     : string
        } = {}) {
          this.value = options.value;
          this.key = options.key || '';
          this.label = options.label || '';
          this.required = !!options.required;
          this.order = options.order === undefined ? 1 : options.order;
          this.type = options.type || '';
      }
    }

    export class Dropdown extends Question<string> {
      options: {key: string, value: string}[] = [];
      type = 'dropdown';

      constructor(options: {} = {}) {
        super(options);
        this.options = options['options'] || [];
      }
    }

    export class TextBox extends Question<string> {
      type = 'textbox';
      type: string;

      constructor(options: {} = {}) {
        super(options);
        this.type = options['type'] || '';
      }
    }

#### question.service.ts

    import { Injectable }       from '@angular/core';
    import { Question, TextBox, Dropdown}     from './question-template';

    @Injectable()
    export class QuestionService {

      getQuestions() {
        let questions: Question<any>[] = [

          new Dropdown({
            key: 'brave',
            label: 'Bravery Rating',
            options: [
              {key: 'solid',    value: 'Solid'},
              {key: 'great',    value: 'Great'},
              {key: 'good',     value: 'Good'},
              {key: 'unproven', value: 'Unproven'}
            ],
            order: 3
          }),

          new TextBox({
            key: 'firstName',
            label: 'First name',
            value: 'Bombasto',
            required: true,
            order: 1
          }),

          new TextBox({
            key: 'emailAddress',
            label: 'Email',
            type: 'email',
            order: 2
          })
        ];

        return questions.sort((a, b) => a.order - b.order);
      }
    } 

#### app-component.ts

    import { Component }       from '@angular/core';
    import { QuestionService } from './question.service';

    @Component({
      selector: 'app-root',
      template: `<app-form [questions]="questions"></app-form>`,
      providers:  [QuestionService]
    })
    export class AppComponent {
      questions: any[];

      constructor(service: QuestionService) {
        this.questions = service.getQuestions();
      }
    }

#### app-form.component.ts

    import { Component, Input, OnInit }  from '@angular/core';
    import { FormGroup }                 from '@angular/forms';
    import { Question }                  from './question-template';
    import { FormControlService }        from './form-control.service';

    @Component({
      selector: 'app-form',
      providers: [ FormControlService ],
      template: "
        <div>
          <form (ngSubmit)="onSubmit()" [formGroup]="form">

            <div *ngFor="let question of questions" class="form-row">
              <app-question [question]="question" [form]="form"></app-question>
            </div>

            <div class="form-row">
              <button type="submit" [disabled]="!form.valid">Save</button>
            </div>
          </form>

          <div *ngIf="payLoad" class="form-row">
            <strong>Saved the following values</strong><br>{{payLoad}}
          </div>
        </div>
      "
    })
    export class FormComponent implements OnInit {

      @Input() questions: Question<any>[] = [];
      form: FormGroup;
      payLoad = '';

      constructor(private fc: FormControlService) {  }

      ngOnInit() {
        this.form = this.fc.toFormGroup(this.questions);
      }

      onSubmit() {
        this.payLoad = JSON.stringify(this.form.value);
      }
    }

#### app-question.component.ts

    import { Component, Input } from '@angular/core';
    import { FormGroup }        from '@angular/forms';

    import { Question }     from './question-template';

    @Component({
      selector: 'app-question',
      template:
        <div [formGroup]="form">
          <label [attr.for]="question.key">{{question.label}}</label>

          <div [ngSwitch]="question.type">

            <input *ngSwitchCase="'textbox'" 
                    [formControlName]="question.key"
                    [id]="question.key" 
                    [type]="question.type">

            <select *ngSwitchCase="'dropdown'"
                    [id]="question.key"  
                    [formControlName]="question.key">
              <option *ngFor="let option of question.options" [value]="option.key">{{option.value}}</option>
            </select>

          </div> 

          <div class="errorMessage" *ngIf="!isValid">{{question.label}} is required</div>
        </div>
    })
    export class QuestionComponent {
      @Input() question: Question<any>;
      @Input() form: FormGroup;
      get isValid() { return this.form.controls[this.question.key].valid; }
    }

#### form-control.service.ts

    import { Injectable }   from '@angular/core';
    import { FormControl, FormGroup, Validators } from '@angular/forms';

    import { Question } from './question-template';

    @Injectable()
    export class FormControlService {
      constructor() { }

      toFormGroup(questions: Question<any>[] ) {
        let group: any = {};

        questions.forEach(question => {
          group[question.key] = question.required ? new FormControl(question.value || '', Validators.required)
                                                  : new FormControl(question.value || '');
        });
        return new FormGroup(group);
      }
    }

#### AppModule.ts

    import { BrowserModule }                from '@angular/platform-browser';
    import { ReactiveFormsModule }          from '@angular/forms';
    import { NgModule }                     from '@angular/core';

    import { AppComponent }                 from './app-component';
    import { FormComponent }                from './app-form.component';
    import { QuestionComponent }            from './app-question.component';

    @NgModule({
      imports: [ BrowserModule, ReactiveFormsModule ],
      declarations: [ AppComponent, FormComponent, QuestionComponent ],
      bootstrap: [ AppComponent ]
    })
    export class AppModule {
      constructor() {
      }
    }

## 4. Observables & RxJS

## 5. [Bootstrapping](https://angular.io/guide/bootstrapping) (Nothing to discuss here)

## 6. [NgModules](https://angular.io/guide/ngmodule-api) To be continued

Entry Components: any component that is not refed in the template (dynamic component).

    @NgModule({
      declarations: [
        AppComponent
      ],
      imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule
      ],
      providers: [],
      bootstrap: [AppComponent] // bootstrapped entry component
    })

### Providers (providedIn)

  import { Injectable } from '@angular/core';
  import { UserModule } from './user.module';

  @Injectable({
    providedIn: 'root', // provide this service to the root injector. This svs will be available to all the app
    providedIn: UserModule', // This service will be available to the app only iff they inport the to UserModule, alternatively add this svs to the providers: [UserService] of UserModule
  })
  export class UserService {}

my.component.ts

  @Component({
    providers: [UserService] // The UserService is only available to this component because the UserService set providedIn: empty
  })

### Singleton services (A single instance of this svs exist throught out the app)

Can be done by providing specifying the root injector in the service itself

    // user.service.ts
    import { Injectable } from '@angular/core';

    @Injectable({
      providedIn: 'root', // The service is provided through the root injector
    })
    export class UserService {}

The other way is registering the service in the root module, rather than specifying the providedIn in the service

    // app.module.ts
    @NgModule({
      providers: [UserService]
    })
    export class AppModule {}

### Desingletonization (to be continued)

To avoid the de-singletonization of a svs: use the 'providedIn' syntax, or separate svs in their own modules, or fefine forRoot() & forChild() methods in the module.

    // greeting.module.ts
    static forRoot(config: UserServiceConfig): ModuleWithProviders {
      return {
        ngModule: GreetingModule,
        providers: [
          {provide: UserServiceConfig, useValue: config }
        ]
      };
    }

### Lazy Loading

#### app.module.ts

    import { NgModule } from '@angular/core';
    import { BrowserModule } from '@angular/platform-browser';
    import { AppRoutingModule } from './app-routing.module';
    import { AppComponent } from './app.component';

    @NgModule({
      declarations: [
        AppComponent
      ],
      imports: [
        BrowserModule,
        AppRoutingModule
      ],
      providers: [],
      bootstrap: [AppComponent]
    })
    export class AppModule { }

#### app-routing.module.ts

    import { NgModule } from '@angular/core';
    import { Routes, RouterModule } from '@angular/router';

    const routes: Routes = [
      {
        path: 'customers',
        loadChildren: () => import('./customers/customers.module').then(module => module.CustomersModule)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(module => module.OrdersModule)
      },
      {
        path: '',
        redirectTo: '',
        pathMatch: 'full'
      }
    ];

    @NgModule({
      imports: [
        RouterModule.forRoot(routes)
      ],
      exports: [RouterModule],
      providers: []
    })
    export class AppRoutingModule { }

#### app.component.ts

    import { Component } from '@angular/core';

    @Component({
      selector: 'app-root',
      template: '
        <router-outlet></router-outlet>
      '
    })
    export class AppComponent {
      title = 'Lazy loading feature modules';
    }

### Sharing Modules (Forwarding imported NgModules)

    import { CommonModule } from '@angular/common';
    import { NgModule } from '@angular/core';
    import { FormsModule } from '@angular/forms';
    import { CustomerComponent } from './customer.component';
    import { NewItemDirective } from './new-item.directive';
    import { OrdersPipe } from './orders.pipe';

    @NgModule({
      imports:      [ CommonModule ],
      declarations: [ CustomerComponent, NewItemDirective, OrdersPipe ],
      exports:      [ CustomerComponent, NewItemDirective, OrdersPipe, // Components are shared to otherNgModules
                      CommonModule, // Used by Directives in this module and also shared
                      FormsModule   // Not used by Directives in this module but shared with others so they will not need to import this module too
                    ]
    })
    export class SharedModule { }

## 7. Dependency Injection

A class asks for dependencies from other classes rather than re inventing them.

    ng generate service heroes/hero

    //hero.service.ts
    import { Injectable } from '@angular/core';

      @Injectable({
        providedIn: 'root',
      })
      export class HeroService {
        constructor() { }
    }

### Injecting services

    import { Component }   from '@angular/core';

    @Component({
    })
    export class HeroListComponent {
      constructor(heroService: HeroService) {     // The service is injected here into a private variable 'heroService
        this.heroes = heroService.getHeroes();    // use functions of this service
      }
    }

Services that need other services

    // loger.service.ts
    import { Injectable } from '@angular/core';
    @Injectable({
      providedIn: 'root'
    })
    export class Logger {
      log(message: string) {}
    }

    // hero.service.ts
    import { Injectable } from '@angular/core';
    import { Logger }     from './logger.service';
    @Injectable({
      providedIn: 'root',
    })
    export class HeroService {
      constructor(private logger: Logger) {  } // Injects another service
    }

Optional Dependencies

    import { Optional } from '@angular/core';

    constructor(@Optional() private logger: Logger) {
      if (this.logger) {
        this.logger.log(some_message);
      }
    }

### The 2 Hierarchical injectors (Define the visibility (scope))

#### ModuleInjector Hierarchy (@Injectable.providedIn || @NgModule.providers[])

    import { Injectable } from '@angular/core';

    @Injectable({
      providedIn: 'root'      // <--provides this service in the root ModuleInjector
      providedIn: ModuleName  // <--provides this service in the ModuleName
    })
    export class ItemService {
      name = 'telephone';
    }
    This method is preferable because oprimization tool.Tree-shaking removes unused modules.

providers declared in @NgModule overrides that declared in @Injectable.

#### ElementInjector Hierarchy (@Component.providers:Injectable, @component.viewProviders:Injectable)

    @Component({
      providers: [
        { provide: ItemService, useValue: { name: 'lamp' } }
      ]
    })
    export class MyComponent

### Resolution rule

Angular looks for Dependency starting from the Directive gowing upwards to the ancestors, i.e. providers are passed to inner Directives.

### Resolution modifiers (@Optional, @Self, @SkipSelf, @Host)

    // @Optional
    export class OptionalComponent {
      constructor(@Optional() public svc: OptionalService) {} // <-- Resolve the service to null instead of throwing an error if service cannot be resolved
    }

    // @Self
    @Component({}) // No providers,
    export class SelfNoDataComponent {
      constructor(@Self() public svc: Svs) { } // <-- Only look at the ServiceInjector for the current Directive, do not search the ancestors
      constructor(@Self() @Optional() public svs: Svs) { } // Use @Optional also, to be more defenssive
    }

    // @SkipSelf() Ignore the service in this Directive and start searching in the parent ElementInjector
    @Component({
      providers: [{ provide: Svs, useValue: { emoji: '🍁' } }] // Angular would ignore this Svs instance
    })
    export class SkipselfComponent {
      constructor(@SkipSelf() public svs: Svs) { }
      constructor(@Optional() @SkipSelf() svs: Svs) {} // use @Optional to prevent Error
    }


    // @Host() Designate a Directive (ancestor) as the last stop while searching for Providers
    @Component({
      providers: [{ provide: Svs, useValue: { emoji: '🌼' } }] //  provide the service
    })
    export class HostComponent {
      constructor(@Host() public svs: Svs) { }//
      constructor(@Host() @Optional() public svs: Svs) { }//
    }

### Logical structure of the template

View is used in this tutorial to represent where a component's template is placed

    <app-root>
      <#VIEW> // <-- The template of the app-root
        <app-child>
          <#VIEW> // <-- The template of the app-child
            ...content goes here...
          </#VIEW>
        </app-child>
      <#VIEW>
    </app-root>

### Providing services in @Component (providers vs viewProviders)

A component class can provide services using providers & viewProviders. Each method affects service visibility.

    // Using providers:[]
    @Component({
      providers: [
        {provide: FlowerService, useValue: {emoji: '🌺'}}
      ]
    })

    // Using viewProviders: []  will not be visible to the child component rendered using <ng-content> </ng-content>
    @Component({
      viewProviders: [
        {provide: AnimalService, useValue: {emoji: '🐶'}}
      ]
    })

#### my.services.ts

    import { Injectable } from '@angular/core';

    @Injectable({providedIn: 'root'})
    export class FlowerService {
      emoji = '🌺';
    }

    @Injectable({providedIn: 'root'})
    export class AnimalService {
      emoji = '🐳';
    }

#### app.component.ts

    import { Component } from '@angular/core';
    import { FlowerService, AnimalService } from './my.services';

    @Component({
      selector: 'app-root',
      template: `
        <p>{{flower.emoji}}</p> // 🌺
        <p>{{animal.emoji}}</p> // 🐳

        <hr />

        <app-child>
          <app-inspector></app-inspector>
        </app-child>
      `,
    })
    export class AppComponent  {
      constructor(public flower: FlowerService, public animal: AnimalService) {}
    }

#### app. child.component

    import { Component, OnInit, Host, SkipSelf, Optional } from '@angular/core';
    import { FlowerService, AnimalService } from '../my.services';

    @Component({
      selector: 'app-child',
      template: `
        <ng-content></ng-content>       <-- This component will not see services provided through viewProviders
        <app-inspector></app-inspector> <-- This component sees services provided through viewProviders
      `,
      providers: [{ provide: FlowerService, useValue: { emoji: '🌻' } }],
      viewProviders: [{ provide: AnimalService, useValue: { emoji: '🐶' } }]
    })

    export class ChildComponent {
      constructor( public flower: FlowerService, public animal: AnimalService) { }
    }

#### inspector.component.ts

    import { Component } from '@angular/core';
    import { FlowerService, AnimalService } from '../my.services';

    @Component({
      selector: 'app-inspector',
      template: `
        <p>Emoji from FlowerService: {{flower.emoji}}</p>
        <p>Emoji from AnimalService: {{animal.emoji}}</p>
      `,
      styleUrls: ['./inspector.component.css']
    })
    export class InspectorComponent {
      constructor(public flower: FlowerService, public animal: AnimalService) { }
    }

### Modifying service visibility (Irrelevant for now)

### Service

      ng generate service services/api --module=app // 

#### api.service.ts

    import { Injectable } from '@angular/core';
    import { Observable, of, pipe} from 'rxjs';
    import { catchError, map, tap } from 'rxjs/operators';
    import {HttpClient, HttpHeaders} from '@angular/common/http';
    
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
    
    @Injectable()
    export class APIService {
    
      constructor(private http: HttpClient) { }

      get(path): Observable<any[]> {
          return this.http.get<any>(path, httpOptions)        
          .pipe(
            tap(
              data => console.log(data),
              error => console.error(error)
            )
        );
      }

      post(path:string,item:any): Observable<any[]>{
        return this.http.post<any>('http://localhost:3000/api/Scrum/'+path, item);
      }

      update(path:string,item:any): Observable<any[]>{
        return this.http.put<any>('http://localhost:3000/api/Scrum/'+path, item);
      }   

      remove(path:string,item:any): Observable<any>{
        return this.http.delete<any>('http://localhost:3000/api/Scrum/'+path, item);
      }
    }

#### app.module.ts

    import { AppRoutingModule } from './app-routing.module';
    import { HttpClientModule} from '@angular/common/http';

    import {APIService} from "./services.api.service";
    imports: [    
      BrowserModule,
      HttpClientModule
    ]
    providers:[APIService]

#### app.component.ts

    @Component({})
    export class HeroesComponent implements OnInit {

      constructor(private apiService: ApiService) { }

      ngOnInit() {
        this.apiService.get('users')
            .subscribe(heroes => this.heroes = heroes);
      }
    }

## 8. HttpClient

Angular uses the browser.XMLHttpRequest based @angular.common.http.HttpClientModule for http requests.

### Misc

app.module.ts

    import { NgModule }         from '@angular/core';
    import { BrowserModule }    from '@angular/platform-browser';
    import { HttpClientModule } from '@angular/common/http';

    @NgModule({
      imports: [
        BrowserModule,
        HttpClientModule, // import HttpClientModule after BrowserModule.
      ],
      declarations: [
        AppComponent,
      ],
      bootstrap: [ AppComponent ]
    })
    export class AppModule {}

my.sevice.ts

    import { Injectable } from '@angular/core';
    import { HttpClient } from '@angular/common/http';

    @Injectable()
    export class ConfigService {
      constructor(private http: HttpClient) { }
    }

    // read the full http response + additional headers
    HttpClient.get<Config>(url, { observe: 'response' });

    // JSONP request
    HttpClient.jsonp(url, 'callback').pipe( catchError(this.handleError('searchHeroes', []) // then handle the error

    // non JSON-data
    getTextFile(filename: string) {
      return HttpClient.get(filename, {responseType: 'text'})
        .pipe(
          tap( // Log the result or error
            data => this.log(filename, data),
            error => this.logError(filename, error)
          )
        );
    }

    // Retrying
    getConfig() {
      return HttpClient.get<Config>(url)
        .pipe(
          retry(3), // retry a failed request up to 3 times
          catchError(this.handleError) // then handle the error
        );
    }

### Error handling

    // sevice
    getConfig() {
      return this.http.get<Config>(url)
        .pipe(catchError(this.handleError));
    }
      private handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
        } else {
          console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);
        }
        return throwError('Something bad happened; please try again later.');
      };

    // component
    this.configService.getConfig()
      .subscribe(
        (data: Config) => this.config = { ...data }, // success path
        error => this.error = error // error path
    );

### HTTP headers

    import { HttpHeaders } from '@angular/common/http';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'my-auth-token'
      })
    };

    // update a header field
    httpOptions.headers = httpOptions.headers.set('Authorization', 'my-new-auth-token');

### CRUD service

    import { Injectable } from '@angular/core';
    import { Observable, of } from 'rxjs';
    import { HttpClient, HttpHeaders } from '@angular/common/http';
    import { catchError, map, tap } from 'rxjs/operators';
    import { Hero } from './hero';
    import { HEROES } from './mock-heroes'; // returns an array of heroes
    import { MessageService } from './message.service';

    @Injectable({providedIn: 'root'})
    export class HeroService {

      private url = 'api/heroes';  // URL to web api

      httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      };

      constructor(private http: HttpClient, private messageService: MessageService) { }

      getHeroes(): Observable<Hero[]> {
        return this.http.get<Hero[]>(this.url)
          .pipe(
            tap(_ => this.log('fetched heroes')),
            catchError(this.handleError<Hero[]>('getHeroes', []))
          );
      }

      /** GET hero by id. Return `undefined` when id not found */
      getHeroNo404<Data>(id: number): Observable<Hero> {
        return this.http.get<Hero[]>(`${this.url}/?id=${id}`)
          .pipe(
            map(heroes => heroes[0]), // returns a {0|1} element array
            tap(h => {
              const outcome = h ? `fetched` : `did not find`;
              this.log(`${outcome} hero id=${id}`);
            }),
            catchError(this.handleError<Hero>(`getHero id=${id}`))
          );
      }

      /** GET hero by id. Will 404 if id not found */
      getHero(id: number): Observable<Hero> {
        return this.http.get<Hero>(`${this.url}/${id}`).pipe(
          tap(_ => this.log(`fetched hero id=${id}`)),
          catchError(this.handleError<Hero>(`getHero id=${id}`))
        );
      }

      /* GET heroes whose name contains search term */
      searchHeroes(term: string): Observable<Hero[]> {
        if (!term.trim()) {
          return of([]);
        }

      /** POST: add a new hero to the server */
      addHero (hero: Hero): Observable<Hero> {
        return this.http.post<Hero>(this.url, hero, this.httpOptions).pipe(
          tap((newHero: Hero) => this.log(`added hero w/ id=${newHero.id}`)),
          catchError(this.handleError<Hero>('addHero'))
        );
      }

      /** DELETE: delete the hero from the server */
      deleteHero (hero: Hero | number): Observable<Hero> {
        const id = typeof hero === 'number' ? hero : hero.id;
        const url = `${this.url}/${id}`;

        return this.http.delete<Hero>(url, this.httpOptions).pipe(
          tap(_ => this.log(`deleted hero id=${id}`)),
          catchError(this.handleError<Hero>('deleteHero'))
        );
      }

      /** PUT: update the hero on the server */
      updateHero (hero: Hero): Observable<any> {
        return this.http.put(this.url, hero, this.httpOptions).pipe(
          tap(_ => this.log(`updated hero id=${hero.id}`)),
          catchError(this.handleError<any>('updateHero'))
        );
      }

      /**
      * Handle Http operation that failed.
      * Let the app continue.
      * @param operation - name of the operation that failed
      * @param result - optional value to return as the observable result
      */
      private handleError<T> (operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

          // TODO: send the error to remote logging infrastructure
          console.error(error); // log to console instead

          // TODO: better job of transforming error for user consumption
          this.log(`${operation} failed: ${error.message}`);

          // Let the app keep running by returning an empty result.
          return of(result as T);
        };
      }

      /** Log a HeroService message with the MessageService */
      private log(message: string) {
        this.messageService.add(`HeroService: ${message}`);
      }

### HTTP interceptors

#### Intercept http requests and responses, then alter or not the content

    import { Injectable } from '@angular/core';
    import { Observable } from 'rxjs';
    import {
      HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
    } from '@angular/common/http';


    /** Pass untouched request through to the next request handler. */
    @Injectable()
    export class NoopInterceptor implements HttpInterceptor {
      intercept(req: HttpRequest<any>, next: HttpHandler): //transforms the request into an Observable then returns the HTTP
        Observable<HttpEvent<any>> {
        return next.handle(req); // handle() transforms the HTTP request into an Observable of HTTPEvents
      }
    }

#### AuthService produces an authorization token. Here is its AuthInterceptor that injects that service to get the token and adds an authorization header with that token to every outgoing request

    import { AuthService } from '../auth.service';

    @Injectable()
    export class AuthInterceptor implements HttpInterceptor {

      constructor(private auth: AuthService) {}

      intercept(req: HttpRequest<any>, next: HttpHandler) {
        const authToken = this.auth.getAuthorizationToken();

        // Clone the request and replace the original headers with cloned headers, updated with the authorization.
        const authReq = req.clone({
          headers: req.headers.set('Authorization', authToken)
        });

        // Clone the request and set the new header in one step.
        const authReq = req.clone({ setHeaders: { Authorization: authToken } });

        return next.handle(authReq); // <-- send cloned request with header to the next handler.
      }
    }

#### LoggingInterceptor, captures the time of the request, the time of the response, and logs the outcome with the elapsed time with the injected MessageService

    import { finalize, tap } from 'rxjs/operators';
    import { MessageService } from '../message.service';

    @Injectable()
    export class LoggingInterceptor implements HttpInterceptor {
      constructor(private messenger: MessageService) {}

      intercept(req: HttpRequest<any>, next: HttpHandler) {
        const started = Date.now();
        let ok: string;

        // extend server response observable with logging
        return next.handle(req)
          .pipe(
            tap(
              // <-- Succeeds when there is a response; ignore other events
              event => ok = event instanceof HttpResponse ? 'succeeded' : '',
              // Operation failed; error is an HttpErrorResponse
              error => ok = 'failed'
            ),

            // Log when response observable either completes or errors
            finalize(() => {
              const elapsed = Date.now() - started;
              const msg = `${req.method} "${req.urlWithParams}"
                ${ok} in ${elapsed} ms.`;
              this.messenger.add(msg);
            })
          );
      }
    }

#### CachingInterceptor

    @Injectable()
    export class CachingInterceptor implements HttpInterceptor {
      constructor(private cache: RequestCache) {}

      intercept(req: HttpRequest<any>, next: HttpHandler) {
        
        if (!isCachable(req)) { return next.handle(req); } // continue if not cachable.

        const cachedResponse = this.cache.get(req);

        return cachedResponse ? 
          of(cachedResponse) : 
          sendRequest(req, next, this.cache);
      }

      function sendRequest(
        req: HttpRequest<any>,
        next: HttpHandler,
        cache: RequestCache): Observable<HttpEvent<any>> {

        // No headers allowed in npm search request
        const noHeaderReq = req.clone({ headers: new HttpHeaders() });

        return next.handle(noHeaderReq).pipe(
          tap(event => {
            // There may be other events besides the response.
            if (event instanceof HttpResponse) {
              cache.put(req, event);
            }
          })
        );
      }
    }

#### Return a multi-valued Observable emit the cached response, then emit the fetched response

    @Injectable()
    export class CachingInterceptor implements HttpInterceptor {
      constructor(private cache: RequestCache) {}

      intercept(req: HttpRequest<any>, next: HttpHandler) {

        if (req.headers.get('x-refresh')) {
          const results$ = sendRequest(req, next, this.cache);
          return cachedResponse ?
            results$.pipe( startWith(cachedResponse) ) :
            results$;
        }

        return cachedResponse ?
          of(cachedResponse) : sendRequest(req, next, this.cache);
      }

      function sendRequest(
        req: HttpRequest<any>,
        next: HttpHandler,
        cache: RequestCache): Observable<HttpEvent<any>> {

        // No headers allowed in npm search request
        const noHeaderReq = req.clone({ headers: new HttpHeaders() });

        return next.handle(noHeaderReq).pipe(
          tap(event => {
            // There may be other events besides the response.
            if (event instanceof HttpResponse) {
              cache.put(req, event);
            }
          })
        );
      }
    }

### Config the request

    import {HttpParams} from "@angular/common/http";
    searchHeroes(term: string): Observable<Hero[]> {
      term = term.trim();

      
      // Add safe, URL encoded search parameter if there is a search term
      const options = term ? { params: new HttpParams().set('name', term) } : {};

      //or
      const params = new HttpParams({fromString: 'name=foo'});

      return this.http.get<Hero[]>(this.heroesUrl, options)
        .pipe(
          catchError(this.handleError<Hero[]>('searchHeroes', []))
        );
    }

### Debouncing requests

      withRefresh = false;
      packages$: Observable<NpmPackageInfo[]>;
      private searchText$ = new Subject<string>();

      search(packageName: string) {
        this.searchText$.next(packageName);
      }

      ngOnInit() {
        this.packages$ = this.searchText$.pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap(packageName =>
            this.searchService.search(packageName, this.withRefresh))
        );
      }

      constructor(private searchService: PackageSearchService) { }

## 9. Routing & Navigation

    ng generate module app-routing --module app --flat

    //--flat puts the file in src/app instead of its own folder.
    //--module=app tells the CLI to register it in the imports array of the AppModule.    

### app-routing.module.ts

    import { NgModule } from '@angular/core';
    import { RouterModule, Routes }  from '@angular/router';
    import { HomeComponent } from './a/a.component';
    import { ItemDetailComponent } from './b/b.component';

    const routes: Routes = [
      { path: 'home', component: HomeComponent },
      { path: 'detail/:id', component: ItemDetailComponent },
      { path: '', redirectTo: 'home', pathMatch: 'full'},
      { path: '**', component: HomeComponent }, // default route must be the last
    ];

    @NgModule({
      imports: [
        RouterModule.forRoot(routes)
      ],
      exports: [
        RouterModule
      ]
    })
    export class AppRoutingModule { }

### app.module.ts

    import { AppRoutingModule } from './app-routing.module';
      
    @NgModule({
      declarations: [
        ItemDetailComponent,
        HomeComponent
      ],
      imports: [
        AppRoutingModule
      ]
    })

### app.component.ts

    import { Component } from '@angular/core';

    @Component({
      template: `
        <Header></Header>
        <router-outlet></router-outlet>
      `,
    })
    export class AppComponent {}

### header.component.html

    import { Component } from '@angular/core';

    @Component({
      template: `
        <nav><a routerLink="/home">home</a></nav>
        <nav><a routerLink="/detail">detail</a></nav>
      `,
    })
    export class AppComponent {}

### my.component.ts

    import { Component } from '@angular/core';
    import { Router, ActivatedRoute, ParamMap } from '@angular/router';
    import { switchMap } from 'rxjs/operators';

    @Component({})
    export class AppComponent {
      constructor(
        private route: ActivatedRoute,
        private router: Router,
        private service: HeroService
      ) {};

      ngOnInit() {

        this.route.paramMap.subscribe((params : ParamMap) => {  
          this.apiService.getUser(params.get('id')).subscribe(user => this.user = user)
        });  

        this.hero$ = this.route.paramMap.pipe(
          switchMap((params: ParamMap) =>
            this.service.getHero(params.get('id')))
        );

        // snapshot provides route paramaters without subscription but doesnot update on component reuse
        let id = this.route.snapshot.paramMap.get('id');
        this.hero$ = this.service.getHero(id);
      }

      navigateBack() {
        this.router.navigate(['/heroes']);
      }

    }

## 10. Animation

## 11. Security

## 12. Internationalization (i18n)

## 13. Accessibility

## 14. Service Workers & PWA

## 15. Server-side Rendering

## 16. Additional Topics

### Upgrading From AngularJs

### Angular Libraries

### Web Workers

## 17. Dev Workflow

### AoT Compilation

### Building & Serving

### Testing

### Deployment

### (Dev Tool Intergration)

## 18. [Glossary](https://angular.io/guide/glossary)

## Extra Topics

### Configuration

### Dev Tool integration

### Project File Structure

### Workspace Configuration

### NPM Dependencies

### Typescript Config

### [Release Information](https://angular.io/guide/deprecations)

### [Quick Reference](https://angular.io/guide/cheatsheet)

### [API](https://angular.io/api)

### Misc

Create an app + app.routing.ts

    ng new customer-app --routing

### [ng generate](https://angular.io/cli/generate)
