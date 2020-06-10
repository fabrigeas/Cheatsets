# [Angular@material](https://material.angular.io/components/categories)

## init

    ng new hello-material
    ng serve
    ng add @angular/material

## Displaying components

### Slider
app.module.ts

    import { MatSliderModule } from '@angular/material/slider';
    @NgModule ({
      imports: [
        MatSliderModule,
      ]
    })

your.component.html

    <mat-slider min="1" max="100" step="1" value="1"></mat-slider>

### Autocomplete

app.module.ts

    import {FormsModule, ReactiveFormsModule} from '@angular/forms';
    import {MatAutocompleteModule} from '@angular/material/autocomplete';
    import {MatInputModule} from '@angular/material/input';

    @NgModule ({
      imports: [
        FormsModule, ReactiveFormsModule,
        MatAutocompleteModule,
        MatInputModule
      ]
    })

your.component.html

    <form class="example-form">
      <mat-form-field class="example-full-width">
        <input type="text"
              placeholder="Pick one"
              aria-label="Number"
              matInput
              [formControl]="myControl"
              [matAutocomplete]="auto">
        <mat-autocomplete #auto="matAutocomplete">
          <mat-option *ngFor="let option of options" [value]="option">
            {{option}}
          </mat-option>
        </mat-autocomplete>
      </mat-form-field>
    </form>

your.component.ts

  export class AppComponent {
    myControl = new FormControl();
    options: string[] = ['One', 'Two', 'Three'];
  }


### MatCheckboxModule

app.module.ts

    import {FormsModule, ReactiveFormsModule} from '@angular/forms';
    import {MatCheckboxModule} from '@angular/material/checkbox';
    import {MatRadioModule} from '@angular/material/radio';
    @NgModule ({
      imports: [
        MatCheckboxModule,
        MatRadioModule
      ]
    })

your.component.html

    <mat-checkbox class="example-margin" [(ngModel)]="disabled">Disabled</mat-checkbox>
    <mat-radio-button class="example-margin" value="after">After</mat-radio-button>
    <mat-radio-button class="example-margin" value="before">Before</mat-radio-button>
    <mat-checkbox class="example-margin" [(ngModel)]="checked">Checked</mat-checkbox>
    <mat-checkbox class="example-margin" [(ngModel)]="indeterminate">Indeterminate</mat-checkbox>


your.component.ts

    export class AppComponent {
      title = 'hello-material';
      
      checked = false;
      indeterminate = false;
      labelPosition: 'before' | 'after' = 'after';
      disabled = false;
    }

### Stepper (Wizard)

app.module.ts

    @NgModule ({
      imports: [
        FormsModule, ReactiveFormsModule,
        MatStepperModule,
        MatInputModule,
        MatButtonModule,
      ]
    })

your.component.html

    <button mat-raised-button (click)="isLinear = !isLinear" id="toggle-linear">
      {{!isLinear ? 'Enable linear mode' : 'Disable linear mode'}}
    </button>
    <mat-horizontal-stepper [linear]="isLinear" #stepper>
      <mat-step [stepControl]="firstFormGroup">
        <form [formGroup]="firstFormGroup">
          <ng-template matStepLabel>Fill out your name</ng-template>
          <mat-form-field>
            <mat-label>Name</mat-label>
            <input matInput placeholder="Last name, First name" formControlName="firstCtrl" required>
          </mat-form-field>
          <div>
            <button mat-button matStepperNext>Next</button>
          </div>
        </form>
      </mat-step>
      <mat-step [stepControl]="secondFormGroup">
        <form [formGroup]="secondFormGroup">
          <ng-template matStepLabel>Fill out your address</ng-template>
          <mat-form-field>
            <mat-label>Address</mat-label>
            <input matInput formControlName="secondCtrl" placeholder="Ex. 1 Main St, New York, NY"
                  required>
          </mat-form-field>
          <div>
            <button mat-button matStepperPrevious>Back</button>
            <button mat-button matStepperNext>Next</button>
          </div>
        </form>
      </mat-step>
      <mat-step>
        <ng-template matStepLabel>Done</ng-template>
        <p>You are now done.</p>
        <div>
          <button mat-button matStepperPrevious>Back</button>
          <button mat-button (click)="stepper.reset()">Reset</button>
        </div>
      </mat-step>
    </mat-horizontal-stepper>


your.component.ts

    import {Component, OnInit} from '@angular/core';
    import {FormBuilder, FormGroup, Validators} from '@angular/forms';

    @Component({ })
    export class AppComponent implements OnInit{

      isLinear = false;
      firstFormGroup: FormGroup;
      secondFormGroup: FormGroup;

      constructor(private _formBuilder: FormBuilder) {}

      ngOnInit() {
        this.firstFormGroup = this._formBuilder.group({
          firstCtrl: ['', Validators.required]
        });
        this.secondFormGroup = this._formBuilder.group({
          secondCtrl: ['', Validators.required]
        });
      }
    }

### Popups & Modals

app.module.ts

    import {FormsModule, ReactiveFormsModule} from '@angular/forms';
    import {MatDialogModule} from '@angular/material/dialog';
    import {MatButtonModule} from '@angular/material/button';
    import {MatInputModule} from '@angular/material/input';

    import { AppComponent, DialogOverviewExampleDialog } from './app.component';
    @NgModule({
      declarations: [
        AppComponent,
        DialogOverviewExampleDialog
      ],
      imports: [
        MatDialogModule,
        MatInputModule,
        MatButtonModule,
      ],
      providers: [
        // { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'fill' } },
      ],
      entryComponents: [AppComponent, DialogOverviewExampleDialog],
      bootstrap: [AppComponent]
    })

your.component.html

    <ol>
      <li>
        <mat-form-field>
          <mat-label>What's your name?</mat-label>
          <input matInput [(ngModel)]="name">
        </mat-form-field>
      </li>
      <li>
        <button mat-raised-button (click)="openDialog()">Pick one</button>
      </li>
      <li *ngIf="animal">
        You chose: <i>{{animal}}</i>
      </li>
    </ol>


your.component.ts

    import {Component, Inject} from '@angular/core';
    import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

    export interface DialogData {
      animal: string;
      name: string;
    };

    @Component({ })
    export class AppComponent{

      animal: string;
      name: string;

      constructor(public dialog: MatDialog) {}

      openDialog(): void {
        const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
          width: '250px',
          data: {name: this.name, animal: this.animal}
        });

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
          this.animal = result;
        });
      }
    }

    @Component({
      selector: 'dialog-overview-example-dialog',
      template: "\
      <h1 mat-dialog-title>Hi {{data.name}}</h1>\
      <div mat-dialog-content>\
        <p>What's your favorite animal?</p>\
        <mat-form-field>\
          <mat-label>Favorite Animal</mat-label>\
          <input matInput [(ngModel)]='data.animal'>\
        </mat-form-field>\
      </div>\
      <div mat-dialog-actions>\
        <button mat-button (click)='onNoClick()'>No Thanks</button>\
        <button mat-button [mat-dialog-close]='data.animal' cdkFocusInitial>Ok</button>\
      </div>" 
    })
    export class DialogOverviewExampleDialog {

      constructor(
        public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
        @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

      onNoClick(): void {
        this.dialogRef.close();
      }

    }

## CDK

### Drag and Drop

app.module.ts

    import {DragDropModule} from '@angular/cdk/drag-drop';
    @NgModule ({
      imports: [
        DragDropModule
      ]
    })

your.component.html

    <div class="example-box" cdkDrag> Drag me around </div>

    <div class="example-box" cdkDragLockAxis="y" cdkDrag>
      I can only be dragged up/down
    </div>

    <div class="example-box" cdkDragLockAxis="x" cdkDrag>
      I can only be dragged left/right
    </div>


your.component.ts

    import {Component} from '@angular/core';

    @Component({
      styleUrls: ['cdk-drag-drop-axis-lock-example.css'],
    })
    export class AppComponent{ }

### Drag & Drop with events (Todo List)

app.module.ts

    import {DragDropModule} from '@angular/cdk/drag-drop';
    @NgModule ({
      imports: [
        DragDropModule
      ]
    })

your.component.html

    <div class="example-container">
      <h2>To do</h2>

      <div
        cdkDropList
        #todoList="cdkDropList"
        [cdkDropListData]="todo"
        [cdkDropListConnectedTo]="[doneList]"
        class="example-list"
        (cdkDropListDropped)="drop($event)">
        <div class="example-box" *ngFor="let item of todo" cdkDrag>{{item}}</div>
      </div>
    </div>

    <div class="example-container">
      <h2>Done</h2>

      <div
        cdkDropList
        #doneList="cdkDropList"
        [cdkDropListData]="done"
        [cdkDropListConnectedTo]="[todoList]"
        class="example-list"
        (cdkDropListDropped)="drop($event)">
        <div class="example-box" *ngFor="let item of done" cdkDrag>{{item}}</div>
      </div>
    </div>

your.component.ts

    import {Component} from '@angular/core';
    import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

    @Component({
      selector: 'app-root',
      templateUrl: './app.component.html',
      styleUrls: ['./app.component.less']
    })
    export class AppComponent{
      title = 'hello-material';

      todo = [
        'Get to work',
        'Pick up groceries',
      ];

      done = [
        'Get up',
        'Brush teeth',
        'Take a shower',
      ];

      drop(event: CdkDragDrop<string[]>) {
        if (event.previousContainer === event.container) {
          moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
        } else {
          transferArrayItem(event.previousContainer.data,
                            event.container.data,
                            event.previousIndex,
                            event.currentIndex);
        }
      }

    }