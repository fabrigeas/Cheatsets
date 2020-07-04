# Angular 6

## [1. Architecture overview](https://angular.io/guide/architecture)

    The basic building blocks of an Angular application are NgModules, which provide a compilation context for components. NgModule differs from ES modules cuz it simply defines a compilation context for the app. An app always has at least a root module that enables bootstrapping, and typically has many more feature modules.

### [Components](https://angular.io/api/core/Component)

A component controls a view (a patch of the screen)
Component + Template = Angular view
Each Angular app has atleast a root component that connects the component hierarchy to the DOM.
A Component defines a class containing the application data and logic and is associated with an HTML template that defines a view to be displayed in a target env.

A Template combines HTML with Angular markup that can modify HTML elements before they are displayed.
Template directives provide program logic
Binding markup connects the app data with the dom.
Pipes are functions used by templates to improve user experience by transforming values before display.

#### [Component metadata](https://angular.io/api/core/Component)

The @Component decorator docorates the class immediately below it as a Component,
thereby telling ng where to find the building block required to create and present the component and its view.

    @Component({
      selector:    'app-hero-list',
      templateUrl: '.The path to the template file',
      template: `An inline template. ie the html`
      styles: []
      styleUrls: ['path.css', ]
      providers:  [ AListOfServiceProviders ] Each instance of this component receive a new instance of each services declared
    })
    export class HeroListComponent {}

### [Modules](https://angular.io/guide/architecture-modules)

NgModules are containers that group application parts in a domain.
Each Ng app has atleast the root module AppModule => app.module.ts.

Defined by a class and decorated by @NgModule(), which is a function that takes a single metadata object whose props describe the module

#### [NgModule](https://angular.io/api/core/NgModule)

    // @NgModule decorator with its metadata
    @NgModule({
      declarations: [The list of components, directives, and pipes that belong to this NgModule],// Each Directive must be declared in [1] NgModule
      imports: [Other modules whose exported classes are needed by component templates declared in this NgModule],
      exports: [The subset of declarations that should be visible and usable in the component templates of other NgModules],
      providers: [Creators of services that this NgModule contributes to the global collection of services; they become accessible in all parts of the app. (You can also specify providers at the component level, which is often preferred],
      bootstrap: [AppComponent]
      bootstrap:[The main application view, called the root component, which hosts all other app views. Only the root NgModule should set the bootstrap property]
    })
    export class AppModule {}

App.module.ts NOT must export anything because no module will import AppModule

#### Angular libraries

Each Angular library name begins with the @angular prefix. Install them with the node package manager npm and import parts of them with JavaScript import statements.

    # import Angular's Component decorator from the @angular/core library
    import { Component } from '@angular/core';

    # imports the BrowserModule NgModule from the platform-browser library
    import { BrowserModule } from '@angular/platform-browser';

### [Introduction to services and dependency injection](https://angular.io/guide/architecture-services)

Services are just helpers to components.

src/app/logger.service.ts

    @Injectable({})
    export class Logger {
      log(msg: any)   { console.log(msg); }
      error(msg: any) { console.error(msg); }
      warn(msg: any)  { console.warn(msg); }
    }

src/app/hero.service.ts

    @Injectable({})
    export class HeroService {
      private heroes: Hero[] = [];

      constructor(
        private backend: BackendService,
        private logger: Logger) { }

      getHeroes() {
        this.backend.getAll(Hero).then( (heroes: Hero[]) => {
          this.logger.log(`Fetched ${heroes.length} heroes.`);
          this.heroes.push(...heroes); // fill cache
        });
        return this.heroes;
      }
    }

#### Providing (registering) services

Root

    // By default, the Angular CLI command ng generate service registers a provider with the root injector. ng creates a single instance of the service to be accessible by
    every component that injects it.
    @Injectable({
      providedIn: 'root',
    })
    export class XYZService{}

Specific NgModule

    // This makes the service accessible by only the components of this module
    @NgModule({
      providers: [
        BackendService,
        Logger
      ],
    })

component level

    //a new instance of the service with each new instance of that component
    @Component({
      selector:    'app-hero-list',
      templateUrl: './hero-list.component.html',
      providers:  [ HeroService ]
    })
    export class MyComponent{}

## [Components & techniques](https://angular.io/guide/displaying-data)

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

### [Component Interaction](https://angular.io/guide/component-interaction)

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

parent.component.js

    @Component({
      template: `
        <app-hero-child
          [hero]="hero"
          [master]="master">
        </app-hero-child>

        <h2>Master controls {{names.length}} names</h2>
        <app-name-child *ngFor="let name of names" [name]="name"></app-name-child>
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
parent.component.ts.
parent has access to the child in the template only and not in the component.

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

parent.component.js

    import { AfterViewInit, ViewChild } from '@angular/core';
    import { Component }                from '@angular/core';
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

### [Component Style](https://angular.io/guide/component-styles)

Component scoped style, not inhreited by children, nor parent

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
      declarations:    [ AppComponent,AdBannerComponent,HeroJobAdComponent,HeroProfileComponent,AdDirective ],
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

#### src/app/ad-banner.component.ts

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

#### src/app/ad.directive.t

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

### [Angular Elements](https://angular.io/guide/elements)

Angular Elements are Components that are packaged as custom elements(Web Components)
Custom Elements extends HTML by allowing you to define a tag contolled by js.

### Directives

There are 3 types:

- Component-directive(Component)
- Attribute-directives(Change the appearance or behavoir of a DOM, Component, Directive)
- [Structural-directives](https://angular.io/guide/structural-directives) Changes the DOM layout by ++,-- DOM elements eg NgFor, NgIf

#### Attribute directive

    ng generate directive highlight

src/app/highlight.directive.ts

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

## [Forms](https://angular.io/guide/forms-overview)

### [Reactive Forms](https://angular.io/guide/reactive-forms#reactive-forms-api)

A model driven way of handling forms whose input change over time

src/app/app.module.ts

    import { ReactiveFormsModule } from '@angular/forms'; <-- import the library

    @NgModule({
      imports: [
        ReactiveFormsModule  <-- Import this to all of the module's components
      ],
    })
    export class AppModule { }

#### FormControl (Controls a single input=FormControl)

src/app/name-editor/name-editor.component.ts

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

src/app/profile-editor/profile-editor.component.ts (imports)

    import { Component } from '@angular/core';
    import { FormGroup, FormControl } from '@angular/forms';

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

#### Creating nested form groups [Nesting FromGroups and FormControls into a FromGroup]

src/app/profile-editor/profile-editor.component.ts

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

#### Generating form controls with FormBuilder

    import { FormBuilder } from '@angular/forms';

With form builder

    @Component({})
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

## 2. The Application Shell

Create a new app and start it

    ng new angular-tour-of-heroes
    cd angular-tour-of-heroes
    ng serve --open

src/app/app.component.ts

    import { Component } from '@angular/core';

    @Component({
      selector: 'app-root', //The name to be used from other components <app-root></app-root>
      templateUrl: './app.component.html', //Template file
      template: `<h1>{{title}}</h1> `, //template inline
      styleUrls: ['./app.component.css']
    })

    // This class will be avaialable to the component.html(template)
    export class AppComponent {
      title = 'Tour of Heroes';
    }

### [Create the heroes component](https://angular.io/cli/generate#component-command)

    ng generate component heroes
    ng generate component Name
    ng generate component child --it --is --flat --module=app
    ng generate directive highlight --it --is --flat --module=app
    ng generate module app-routing --flat --module=app

src/app/app.module.ts

    import { BrowserModule } from '@angular/platform-browser';
    import { NgModule } from '@angular/core';
    import { FormsModule } from '@angular/forms'; // <-- NgModel lives here
    import { AppComponent } from './app.component';
    import { HeroesComponent } from './heroes/heroes.component';

    @NgModule({
      // All the components must be added to the module declarations
      declarations: [
        AppComponent,
        HeroesComponent
      ],

      // Each Module must be added to the imports in order to be accessible by the declared components
      imports: [
        BrowserModule,
        FormsModule
      ],
      providers: [],
      bootstrap: [AppComponent]
    })
    export class AppModule { }

src/app/app.component.ts

    import { Component } from '@angular/core';

    @Component({
      template: `<h1>{{title}}</h1>
        <app-heroes></app-heroes>`
     })
    export class AppComponent {
      title = 'Tour of Heroes';
    }

src/app/hero.ts

    export class Hero {
      id: number;
      name: string;
    }

src/app/heroes/heroes.component.ts

    import { Component, OnInit } from '@angular/core';
    import { Hero } from '../hero';

    @Component({
      template: '
        <h2>{{hero.name | uppercase}} Details</h2>
        <div><span>id: </span>{{hero.id}}</div>
        <div>
          <label>name:
            <input [(ngModel)]="hero.name" placeholder="name"/>
          </label>
        </div>'
    });
    export class HeroesComponent implements OnInit {
      hero: Hero = {
        id: 1,
        name: 'Windstorm'
      };
    }

### Component Binding

src/app/app.module.ts

    import { BrowserModule } from '@angular/platform-browser';
    import { NgModule } from '@angular/core';
    import { FormsModule } from '@angular/forms';

    import { AppComponent } from './app.component';
    import { HeroesComponent } from './heroes/heroes.component';
    import { HeroDetailComponent } from './hero-detail/hero-detail.component';

    @NgModule({
      declarations: [list all your components here],
      imports: [list all modules here to inject them to each component],
      bootstrap: [AppComponent] //The bootstrapped component
    })
    export class AppModule { }

src/app/heroes/heroes.component.html

    <ul class="heroes">
      <li *ngFor="let hero of heroes"
        [class.selected]="hero === selectedHero"
        (click)="onSelect(hero)">
        <span class="badge">{{hero.id}}</span> {{hero.name}}
      </li>
    </ul>
    <app-hero-detail [hero]="selectedHero"></app-hero-detail>

src/app/hero-detail/hero-detail.component.ts

    import { Component, OnInit, Input } from '@angular/core';
    import { Hero } from '../hero';// The Hero model

    @Component({})

    export class HeroDetailComponent implements OnInit {
      @Input() hero: Hero;// bind hero to the input
    }

src/app/hero-detail/hero-detail.component.html

    <div *ngIf="hero">
      <h2>{{hero.name | uppercase}} Details</h2>
      <div><span>id: </span>{{hero.id}}</div>
      <div>
        <label>name:
          <input [(ngModel)]="hero.name" placeholder="name"/>
        </label>
      </div>
    </div>

### [Services and HTTP](https://angular.io/tutorial/toh-pt4)

    ng generate service hero

src/app/app.module.ts

    import ***

    @NgModule({
      declarations: [AppComponent,HeroesComponent,HeroDetailComponent,MessagesComponent],
      imports: [Modules that will be used by components],
      providers: [
        // no need to place any providers due to the `providedIn` flag...
      ],
      bootstrap: [ AppComponent ]
    })
    export class AppModule { }

src/app/app.component.html

    <h1>{{title}}</h1>
    <app-heroes></app-heroes>
    <app-messages></app-messages>

src/app/message.service.ts

    import { Injectable } from '@angular/core';

    // Mark this class as injectable by decorating it
    @Injectable({
      providedIn: 'root',//So that you don't need to specify this in the app.module.providers
    })
    export class MessageService {
      messages: string[] = [];
      add(message: string) {this.messages.push(message);}
      clear() {this.messages = [];}
    }

src/app/hero.service.ts

    import { Injectable } from '@angular/core';
    import { Observable, of } from 'rxjs';
    import { HttpClient, HttpHeaders } from '@angular/common/http';
    import { catchError, map, tap } from 'rxjs/operators';
    import { Hero } from './hero';
    import { HEROES } from './mock-heroes'; // returns an array of heroes
    import { MessageService } from './message.service';

    @Injectable({providedIn: 'root'})
    export class HeroService {

      private heroesUrl = 'api/heroes';  // URL to web api

      httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      };

      //Inject the MessageService and HttpClient as a private variable  to the class
      // This works because both are decorated as Injectable
      constructor(private http: HttpClient, private messageService: MessageService) { }

      getHeroes(): Observable<Hero[]> {
      return this.http.get<Hero[]>(this.heroesUrl)
        .pipe(
          tap(_ => this.log('fetched heroes')),
          catchError(this.handleError<Hero[]>('getHeroes', []))
        );
      }

      /** GET hero by id. Return `undefined` when id not found */
      getHeroNo404<Data>(id: number): Observable<Hero> {
        return this.http.get<Hero[]>(`${this.heroesUrl}/?id=${id}`)
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
        return this.http.get<Hero>(`${this.heroesUrl}/${id}`).pipe(
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
        return this.http.post<Hero>(this.heroesUrl, hero, this.httpOptions).pipe(
          tap((newHero: Hero) => this.log(`added hero w/ id=${newHero.id}`)),
          catchError(this.handleError<Hero>('addHero'))
        );
      }

      /** DELETE: delete the hero from the server */
      deleteHero (hero: Hero | number): Observable<Hero> {
        const id = typeof hero === 'number' ? hero : hero.id;
        const url = `${this.heroesUrl}/${id}`;

        return this.http.delete<Hero>(url, this.httpOptions).pipe(
          tap(_ => this.log(`deleted hero id=${id}`)),
          catchError(this.handleError<Hero>('deleteHero'))
        );
      }

      /** PUT: update the hero on the server */
      updateHero (hero: Hero): Observable<any> {
        return this.http.put(this.heroesUrl, hero, this.httpOptions).pipe(
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
    }

    }

src/app/heroes/heroes.component.ts

    import { Component, OnInit } from '@angular/core';
    import { Hero } from '../hero';
    import { HeroService } from '../hero.service';

    @Component({})
    export class HeroesComponent implements OnInit {
      selectedHero: Hero;
      heroes: Hero[];

      constructor(private heroService: HeroService) { }

      ngOnInit() {this.getHeroes();}

      onSelect(hero: Hero): void {
        this.selectedHero = hero;
      }

      getHeroes(): void {
        this.heroService.getHeroes()
            .subscribe(heroes => this.heroes = heroes);
      }
    }

src/app/messages/messages.component.ts

    import { Component, OnInit } from '@angular/core';
    import { MessageService } from '../message.service';

    @Component({})
    export class MessagesComponent implements OnInit {
      constructor(public messageService: MessageService) {}
    }

    <div *ngIf="messageService.messages.length">
      <h2>Messages</h2>
      <button class="clear"(click)="messageService.clear()">clear</button>
      <div *ngFor='let message of messageService.messages'> {{message}} </div>
    </div>

### [Routing](https://angular.io/tutorial/toh-pt5)

    ng generate module app-routing --module app --flat

    //--flat puts the file in src/app instead of its own folder.
    //--module=app tells the CLI to register it in the imports array of the AppModule.

app-routing.module.ts

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

app.component.html

    <Header></Header>
    <router-outlet></router-outlet>

header.component.html

    <nav><a routerLink="/home">home</a></nav>
    <nav><a routerLink="/detail">detail</a></nav>

app.module

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

#### Getting request parameters

src/app/hero-detail/hero-detail.component.ts

    ngOnInit(): void {
      this.getHero();
    }

    getHero(): void {
      const id = this.route.snapshot.paramMap.get('id');
      this.heroService.getHero(id)
        .subscribe(hero => this.hero = hero);
    }

    goBack(): void {
      this.location.back();
    }

# next steps

class property

    <div [class.special]="isSpecial">Special</div>

style property

    src/app/app.component.html
    <button [style.color]="isSpecial ? 'red' : 'green'">

ngClass

    currentClasses: {};

    setCurrentClasses() {
      // CSS classes: added/removed per current state of component properties
      this.currentClasses =  {
        'saveable': this.canSave,
        'modified': !this.isUnchanged,
        'special':  this.isSpecial
      };
    }

    <div [ngClass]="currentClasses">
      This div is initially saveable, unchanged, and special
    </div>

NgStyle

    currentStyles: {};
    setCurrentStyles() {
      // CSS styles: set per current state of component properties
      this.currentStyles = {
        'font-style':  this.canSave      ? 'italic' : 'normal',
        'font-weight': !this.isUnchanged ? 'bold'   : 'normal',
        'font-size':   this.isSpecial    ? '24px'   : '12px'
      };
    }

    <div [ngStyle]="currentStyles">
      This div is initially italic, normal weight, and extra large (24px).
    </div>

\*NgIf

    <app-hero-detail *ngIf="isActive"></app-hero-detail>

NgSwitch

    <div [ngSwitch]="currentHero.emotion">
      <app-happy-hero    *ngSwitchCase="'happy'"    [hero]="currentHero"></app-happy-hero>
      <app-sad-hero      *ngSwitchCase="'sad'"      [hero]="currentHero"></app-sad-hero>
      <app-confused-hero *ngSwitchCase="'confused'" [hero]="currentHero"></app-confused-hero>
      <app-unknown-hero  *ngSwitchDefault           [hero]="currentHero"></app-unknown-hero>
    </div>

Template reference variables ( #var )

    <input #phone placeholder="phone number">
    <button (click)="callPhone(phone.value)">Call</button>

## Component comunication interaction

Parent accesses child's methods

    #child
    import {Input} from '@angular/core';
    export class ChildComponent implements OnInit {
      start() {}
    }

    #Parent
    <button (click)="child.start()">Start</button>
    <app-child #child></app-child>

Parent accesses child Component

    #parent
    import {ViewChild } from '@angular/core';
    import {ChildComponent} from "./child.component";

    @Component({
      template: '
        <button (click)="child.medthod()">Start</button>
        <app-child #child></app-child>',
    })
    export class AppComponent {
      @ViewChild(ChildComponent)
      private child: ChildComponent;
      start() { this.child.method(); }
    }

    #child see above

Components comunicate via Service

    #Service
    import { Subject }    from 'rxjs';
    private missionAnnouncedSource = new Subject<any>();
    private missionConfirmedSource = new Subject<any>();
    missionAnnounced$ = this.missionAnnouncedSource.asObservable();
    missionConfirmed$ = this.missionConfirmedSource.asObservable();
    announceMission(anyData:any) {
      this.missionAnnouncedSource.next(anyData);
    }
    confirmMission(anyData: any) {
      this.missionConfirmedSource.next(anyData);
    }

    #parent
    <button (click)="announce()">send mission</button>

    constructor(private service: Service) {
      service.missionConfirmed$.subscribe(
        data => {
      });
    }
    announce() {
      this.service.announceMission( anyData );
    }

    #children
    import { Subscription }   from 'rxjs';
    subscription: Subscription;
    constructor(private servce:Service){
      this.subscription = servce.missionAnnounced$.subscribe(
        receivedData => {
      });
    }
    sendConfirmation() {
      this.servce.confirmMission( anyData );
    }

## Event binding, EventEmitter, child signaling parent

    #AppComponent.html
    <app-child (eventEmitter)="onEventEmitted($event)"></app-new-card-input>

    #parent.component
    eventEmitter( data:any ) {}

    #child.component
    import {Output, EventEmitter } from '@angular/core';
    @Output() onCardAdd = new EventEmitter<any>();
    someMethod(data:any) {
      this.eventEmitter.emit(data);
    }

## Attribute directives

(create a custom attribute) this attribute just sets the background of the component to yellow

    ng generate directive highlight --it --is --flat --module=app

    # highlight.directive.ts

    import { Directive, ElementRef, HostListener, Input } from '@angular/core';
    @Directive({ selector: '[appHighlight]' })
    export class HighlightDirective {
      constructor(private el: ElementRef) { }
      @Input('appHighlight') highlightColor: string;
      @Input() defaultColor: string;
      @HostListener('mouseenter') onMouseEnter() { this.highlight(this.highlightColor || this.defaultColor || 'red'); }
      @HostListener('mouseleave') onMouseLeave() { this.highlight(null); }
      private highlight(color: string) { this.el.nativeElement.style.backgroundColor = color; }
    }

    # component

    <input type="radio" name="colors" (click)="color='lightgreen'">Green
    <input type="radio" name="colors" (click)="color='yellow'">Yellow
    <input type="radio" name="colors" (click)="color='cyan'">Cyan
    <p [appHighlight]="color" defaultColor="violet">Highlight me too!</p>

### Structural Directives (DOM Manipulation)

    ng-container
    <p> I turned the corner
      <ng-container *ngIf="hero">
        and saw {{hero.name}}. I waved
      </ng-container>
      and continued on my way.
    </p>

### Conditional option

    <div> Pick your favorite hero
      (<label><input type="checkbox" checked (change)="showSad = !showSad">show sad</label>)
    </div>

    <select [(ngModel)]="hero">
      <ng-container *ngFor="let h of heroes">
        <ng-container *ngIf="showSad || h.emotion !== 'sad'">
          <option [ngValue]="h">{{h.name}} ({{h.emotion}})</option>
        </ng-container>
      </ng-container>
    </select>

## Forms

template variable

    @Component({
      template: `
        <input #box (keyup)="onKey(box.value)">
        <p>{{values}}</p>
      ` })

    export class Component {
      onKey(value: string) { }
    }

    # Enter key handler
    @Component({ template: `
        <input #box (keyup.enter)="onEnterPressed(box.value)">
    `})
    export class component{
      onEnterPressed(value: string) { this.value = value; }
    }

Access template variable in component

    import { ViewChild, ElementRef } from '@angular/core';
    @ViewChild('templateVariableSelector') templateVariable: ElementRef;
    inAFunction() {
      this.templateVariable.nativeElement
    }

### Template Driven Form

      export class Hero {
        constructor(
          public id: number,
          public name: string,
          public power: string,
          public alterEgo?: string
        ) {  }

    powers = ['Really Smart', 'Super Flexible', 'Super Hot', 'Weather Changer'];
    model = new Hero(18, 'Dr IQ', this.powers[0], 'Chuck Overstreet');

    <div [hidden]="submitted">
      <form (ngSubmit)="onSubmit()" #heroForm="ngForm">
         <div class="form-group">
           <input type="text" class="form-control" id="name" required [(ngModel)]="model.name" name="name" #name="ngModel">
          <div [hidden]="name.valid || name.pristine" class="alert alert-danger"> Name is required </div>
        </div>
        <div class="form-group">
          <input type="text" class="form-control" id="alterEgo" [(ngModel)]="model.alterEgo" name="alterEgo">
        </div>

        <div class="form-group">
          <select class="form-control" id="power" required [(ngModel)]="model.power" name="power" #power="ngModel">
             <option *ngFor="let pow of powers" [value]="pow">{{pow}}</option>
          </select>
          <div [hidden]="power.valid || power.pristine" class="alert alert-danger">Power is required </div>
        </div>

        <button type="submit" class="btn btn-success" [disabled]="!heroForm.form.valid">Submit</button>
        <button type="button" class="btn btn-default" (click)="newHero(); heroForm.reset()">New Hero</button>
      </form>
    </div>

[Reactive Forms](https://angular.io/api/forms/FormControlName#use-with-ngmodel)

#app.module
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

#component
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
loginForm : FormGroup;
constructor(private fb: FormBuilder) {
this.loginForm = this.fb.group({
UserName: ['', Validators.required ],
Password: ['', Validators.required ]
});
}

#template

<form [formGroup]="loginForm" (ngSubmit)="submitLoginForm(\$event)" novalidate>
<input #UserName [(ngModel)]="User.UserName" formControlName="UserName" name="UserName">
<input #Password [(ngModel)]="User.Password" formControlName="Password" name="Password">
  


## Data binding

1-way binding //value changed in template doesn't change that in component

    count: number = 4;
    text: string = 'Add an Item';
    <input type="submit" class="btn" [value]="text">
    <input type="submit" class="btn" value="{{ text }}">

2-way binding //Value changed both way

    #app.modules
    import {FormsModule} from "@angular/forms";
    imports: [
        FormsModule
    ],

    #component
     attribute: any = { Name:"", Age:"" };
     <input [(ngModel)]="attribute.Name">

Object binding //values changed by the child alse changes that in parent

    #parent
    <app-child [cards]="cards"></app-child>

    #app-child
    import { Input } from '@angular/core';
    @Input() cards: Array<any>; // @Input() cards: Card[];

    <list *ngFor="let card of cards">{{card}}</list>

## Services

        ng generate service data
        ng generate service services/data //into folder /services
        ng generate service services/data --module=app //

        import  { Injectable } from '@angular/core';
        import { Observable, of } from 'rxjs';
        import {HttpClient, HttpHeaders} from '@angular/common/http';

        const httpOptions = {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        };

        @Injectable()
        export class ScrumService {

          constructor(private http: HttpClient) { }

          get(path:string,project:number): Observable<any[]> {
            return this.http.get<any>('http://localhost:3000/api/Scrum/'+path+"/"+project,httpOptions);
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

app.module
import {ScrumService} from "./scrum.service";
import { HttpClientModule} from '@angular/common/http';
imports: [HttpClientModule]
providers:[ScrumService]

#component
constructor(private dataService:ScrumService) { }
ngOnInit() {
this.dataService.getProjects("Resources",12345678).subscribe(data => {
this.Resources = data;
});
}

<li \*ngFor="let resource of Resources">{{resource}}</li>

### Routing Params

app.routing.module.ts

    const routes: Routes = [
      { path: 'detail/:id', component: DetailComponent },
    ];

component

import { Router, ActivatedRoute, ParamMap } from '@angular/router';

constructor(private route: ActivatedRoute, private dataService: DataService) {
}

    ngOnInit() {
        this.item = this.route.paramMap.pipe(
        switchMap((params: ParamMap) =>
            this.dataService.getItem(params.get('id'))
            )
        );
    }

    navigateToDetail(id: string) {
      this.router.navigate(['/detail/' +id]);
    }

### Event binding

    <input type="submit" class="btn" value="{{ btnText }}" (click)="addItem()">

    export class HomeComponent implements OnInit {
      itemCount: number = 4;
      btnText: string = 'Add an Item';
      goalText: string = 'My first life goal';
      goals = [];

      ngOnInit() { this.itemCount = this.goals.length; }

      addItem() {
        this.goals.push(this.goalText);
        this.goalText = '';
        this.itemCount = this.goals.length;
      }

    }

## Read JSON file

    import { HttpClient } from '@angular/common/http';
    constructor(private http: HttpClient){
      http.get("./assets/vessels.json").subscribe(data => {
        console.log(data)
      });
    }

## No 'Access-Control-Allow-Origin'

    npm install cors --save

    #backend.app.js
    const cors = require('cors');
     var corsOptions = {
        origin: 'http://localhost:4200',
        optionsSuccessStatus: 200 // some legacy browsers (IE11, various SmartTVs) choke on 204
     };
     app.use(cors(corsOptions));

# Misc

generate components without specs

    .angular.cli.json
       "defaults": {
          "styleExt": "css",
          "component": { "spec": false },
          "service": { "spec": false }
        }

Custom validator

          <div class="form-group row">
          <div class="col-lg-10">
            <input id="project-Name"
                   type="text"
                   class="form-control form-control-lg"
                   required
                   name="projectName"
                   minlength="3"
                   pattern="^[A-Za-z0-9_ .,]*$"
                   forbiddenString="fuck"
                   #projectName="ngModel"
                   [(ngModel)]="project.Name">
            <div *ngIf="projectName.errors">Cannot contain 'fuck'.</div>
          </div>
        </div>

generate directive ForbiddenPattern

        import { Directive, Input, OnChanges, SimpleChanges } from '@angular/core';
        import { AbstractControl, NG_VALIDATORS, Validator, ValidatorFn, Validators } from '@angular/forms';

        /** Input's value can't match the given regular expression */
        export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
          return (control: AbstractControl): {[key: string]: any} => {
            const forbidden = nameRe.test(control.value);
            return forbidden ? {'forbiddenName': {value: control.value}} : null;
          };
        }

        @Directive({
          selector: '[forbiddenString]',
          providers: [{provide: NG_VALIDATORS, useExisting: ForbiddenValidatorDirective, multi: true}]
        })
        export class ForbiddenValidatorDirective implements Validator {
          @Input('forbiddenString') forbiddenName: string;

          validate(control: AbstractControl): {[key: string]: any} {
            return this.forbiddenName ? forbiddenNameValidator(new RegExp(this.forbiddenName, 'i'))(control) : null;
          }
        }

    #module.app
    import {ForbiddenValidatorDirective} from './forbidden-pattern.directive';
    @NgModule({
      declarations: [
      ForbiddenValidatorDirective
    ],

Reactive Forms

      <form [formGroup]="priorityFormGroup" novalidate>
              <input placeholder="Priority" formControlName="name" #name [(ngModel)] = "Settings.Priority.Name" class="form-control">

              <input formControlName="color" #color [(ngModel)]="Settings.Priority.Color"
                     placeholder="Color"
                     [style.background]="Settings.Priority.Color"
                     [colorPicker]="Settings.Priority.Color"
                     [cpPosition]="'bottom'"
                     [cpOKButton]="(true)"
                     [cpOKButtonText]="'ok'"
                     (colorPickerChange)="Settings.Priority.Color=$event"/>

              <button [disabled]="priorityFormGroup.pristine || priorityFormGroup.invalid"
                      class="btn btn-light"
                      (click)="addSetting('Priorities','Priority',Settings.Priority.Name,Settings.Priority.Color)">
                <fa name="plus" ></fa>
              </button>
      </form>

    #component
    import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';

      Settings : Settings ={
        Priorities :[],
        Statuses :[],
        Priority :{Name:"",Color:""},
        Status :{Name:"",Color:""}
      };

      priorityFormGroup: FormGroup;

      constructor(private fb: FormBuilder) {
        this.priorityFormGroup = this.fb.group({
          name:  ['', Validators.required ],
          color: ['', Validators.required ]
        });
      }
    }

## Dialog

npm install --save @angular/material @angular/cdk @angular/animations

#app.module.ts
import {
MatDialogModule,
MatFormFieldModule,
MatInputModule, MatNativeDateModule,
MatOptionModule,
MatSelectModule,
...
} from '@angular/material';
import {MatDatepickerModule} from '@angular/material/datepicker';

imports: [
MatDialogModule,
MatFormFieldModule,
BrowserAnimationsModule,
MatSelectModule,
MatOptionModule,
MatInputModule,
MatDatepickerModule,
MatNativeDateModule,
...
],
entryComponents: [TaskDialogComponent]

component

    <button (click)="openTaskCreationDialog()">Add file</button>

    import {MatDialog} from "@angular/material";
    constructor(private dialog: MatDialog) {}
    openTaskCreationDialog() {
      this.dialog.open(TaskDialogComponent, {
        disableClose: false,
        hasBackdrop: false,
        data:{
          Title : "Task Dialog",
          task : new Task(-1)
        }
      });
    }

task-dialog

    import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
    import {Component, Inject, OnInit} from "@angular/core";
    import {Task} from "../models/task";

    task: task;
    title: string;

    constructor(private dialogRef: MatDialogRef<TaskDialogComponent>, @Inject(MAT_DIALOG_DATA) data) {
       this.task  = data.task;
       this.title = data.title;
    }

    save() {
      this.dialogRef.close();
    }

    close() {
      this.dialogRef.close();
    }

    <div class="container">

      <h2 mat-dialog-title>Task Dialog</h2>

      <mat-dialog-content class="container task-form-dialog">

        <mat-form-field appearance="legacy">
          <input matInput name="Name" #Name [(ngModel)]="task.Name" placeholder="Task Name">
        </mat-form-field>

        <mat-form-field appearance="outline">
          <textarea matInput name="Description" #Description [(ngModel)]="task.Description" placeholder="Task Description"></textarea>
        </mat-form-field>

        <mat-form-field>
          <mat-select placeholder="Priority">
            <!--<mat-option *ngFor="let food of foods" [value]="food.value">-->
            <!--{{ food.viewValue }}-->
            <!--</mat-option>-->
            <mat-option [value]="Normal">Normal</mat-option>
            <mat-option [value]="Low">Low</mat-option>
            <mat-option [value]="High">High</mat-option>
          </mat-select>
        </mat-form-field>

        <div class="row">

          <mat-form-field class="col">
            <input matInput [matDatepicker]="StartDate" placeholder="Starts on">
            <mat-datepicker-toggle matSuffix [for]="StartDate"></mat-datepicker-toggle>
            <mat-datepicker #StartDate></mat-datepicker>
          </mat-form-field>

          <mat-form-field class="col">
            <input matInput [matDatepicker]="EndDate" placeholder="Ends on">
            <mat-datepicker-toggle matSuffix [for]="EndDate"></mat-datepicker-toggle>
            <mat-datepicker #EndDate></mat-datepicker>
          </mat-form-field>
        </div>
      </mat-dialog-content>

      <mat-dialog-actions>
        <button class="mat-raised-button btn btn-warning"(click)="close()">Close</button>
        <button class="mat-raised-button mat-primary btn btn-primary"(click)="save()">Save</button>
      </mat-dialog-actions>
    </div>

Custom pipe | formatter

ng generate pipe DaysRemaining

#pipe
import { Pipe, PipeTransform } from '@angular/core';
import \* as moment from 'moment';

@Pipe({
name: 'daysRemaining'
})
export class DaysRemainingPipe implements PipeTransform {
transform(value: any, unit?: any): any {
return moment(value).diff(moment(),unit)+ " "+ unit;
}
}
#template
<span class="badge badge-light mr-md-3">
{{Sprint.EndDate | daysRemaining:'days'}}
</span>

Font-awesome

    https://www.npmjs.com/package/angular-font-awesome
    npm install --save font-awesome angular-font-awesome

    #app.module
    import { AngularFontAwesomeModule } from 'angular-font-awesome';
    imports: [
        AngularFontAwesomeModule
    ],

    #angular.cli.json

    "styles": [
      "styles.css",
      "../node_modules/font-awesome/css/font-awesome.css"
    ],

    #index.html
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />

Jquery \$

    npm install --save jquery

    #.angular.cli.json
    scripts": [
      "./node_modules/jquery/dist/jquery.min.js"
    ]

    #component
    import * as $ from 'jquery';

    public ngOnInit()
    {
      $(document).ready(function(){
        $("button").click(function(){
            var div = $("div");
            div.animate({left: '100px'}, "slow");
            div.animate({fontSize: '5em'}, "slow");
        });
      });
    }

boostrap

npm install --save bootstrap
npm install ngx-bootstrap --save

#src/style.css
@import "~bootstrap/dist/css/bootstrap.min.css";
@import "~font-awesome/css/font-awesome.css";

#app.modules.ts
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
imports: [
NgbModule,
],

### Reactive form

app.modules

    import { ReactiveFormsModule } from '@angular/forms';
    imports: [
      ReactiveFormsModule
    ],

Simple

    import { FormGroup, FormControl } from '@angular/forms';

    address = new FormGroup({
      street:  new FormControl(''),
      number:  new FormControl(''),
    });

    onSubmitSignUpForm() {
      console.log(this.address.value)
    }

    <form [formGroup]="address" (ngSubmit)="onSubmitSignUpForm()">
        <label>Street: <input type="text" formControlName="street"> </label>
        <label>Number: <input type="number" formControlName="number"> </label>
        <button type="submit" value="submit" >Submit</button>
    </form>

Nested form group

    User = new FormGroup({
      name: new FormControl(''),
      address: new FormGroup({
        state: new FormControl(''),
        ...
      })
    });

    //set some values
    updateProfile() {
      this.profileForm.patchValue({
        name: 'Nancy',
        address: {
          state: '123 Drew Street'
        }
      });
    }

    <div formGroupName="address">
      //I guess the name is somewhere else
      <input type="text" formControlName="state">
    </div>
