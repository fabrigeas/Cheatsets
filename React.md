# React

## Content

- [Init](#Init)
- [Components & Props](#Components)
- [State & Lifecycle](#State-&-Lifecycle)
- [Handling-Events](#Handling-Events)
- [Conditional-Rendering](#Conditional-Rendering)
- [Lists-&-Keys](#Lists-&-Keys)
- [Forms](#Forms)
- [Lifting-State-up](#Lifting-State-up)
- [Composition-VS-Inheritance](#Composition-VS-Inheritance)
- [Accessibility](#Accessibility)
- [Code-Splitting](#Code-Splitting)
- [Context](#Context)
- [Error Boundaries](#Error-Boundaries)
- [Refs & DOM](#Refs-&-DOM)
- [Forwarding Refs](#Forwarding-Refs)
- [Fragments](#Fragments)
- [HOC (Higher Order Components)](<#HOC-(Higher-Order-Components)>)
- [Router](#Router)
- [Profiler](#Profiler)
- [Reconciliation](#Reconciliation)
- [Render Props](#Render-Props)
- [PropTypes](#PropTypes)
- [Web Components](#Web-Components)
- [Hooks](#Hooks)
- [Concurrent Mode](#Concurrent-Mode)
- [React Intl](#React-Intl)
- [Testing](#Testing)
- [Misc](#Misc)
- [Back to top](#Content)

## Init

Online Editors

- [CodeSandbox](https://codesandbox.io/s/new)
- [Glitch](https://glitch.com/edit/#!/remix/starter-react-template)
- [Codepen](https://reactjs.org/redirect-to-codepen/hello-world)

[Create app](https://www.npmjs.com/package/react-create)

```bash
npx create-react-app my-app
cd my-app
npm start

npx create-react-app my-app --template typescript
```

### [Create component](https://www.npmjs.com/package/react-create)

```bash
npm install -g create-components-react
ccr create src/components/ContactUs
```

[Back to top](#Content)

## [Components](https://reactjs.org/docs/react-component.html)

```js

// React.Element
// github.com/fabrigeas/react-form-group/blob/master/src/components/FormGroup/FormGroup.test.tsx
ReactDOM.render(
  React.createElement(
    "div",
    { id: "login-btn" }, // data-name | id | name  | autocomplete | Events |
    ["Login"] // children:
  )
);

function FunctionComponent (props) {
  return <h1>Hello, {props.name}</h1>;
}

const FunctionComponent (props) => (
  <h1>Hello, {props.name}</h1>;
)

class ClassComponent extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}

ReactDOM.render(
  <FunctionComponent name="Function component"/>,
  document.getElementById('root')
);

ReactDOM.render(
  <div>
    <FunctionComponent name="Function component"/>
    <ClassComponent name="class Component"/>
  </div>,
  document.getElementById('root')
);

// React.PureComponent
// Handles the shouldComponentUpdate() by default. by shallow comparing both props and state.
```

[Back to top](#Content)

## [State & Lifecycle](https://reactjs.org/docs/state-and-lifecycle.html)

```js
class Counter extends React.Component {

  state = {
      count: 0,
      other: ?
    }

  addOne = () =>
    this.setState( (previousState) => {
      return {
        count: previousState.count + 1,
      }
    })

  render() {
    return (
      <div>
        <h1>Count: {this.state.count}</h1>
        <button onClick={this.addOne}> +1 </button>
      </div>
    )
  }
}
```

[Back to top](#Content)

## Handling Events

[Handling Events](https://reactjs.org/docs/handling-events.html)\
[Events and Attributes](https://reactjs.org/docs/dom-elements.html)\
[Back to top](#Content)

## Conditional-Rendering

```js

// if
render() {
  return (
    <div>
      The user is <b>{this.state.isLoggedIn ? 'currently' : 'not'}</b> logged in.
    </div>
  );
}

// Conditional operator
render() {
  return (
    <div>
    {
      this.state.isLoggedIn ?
      (<LogoutButton onClick={this.handleLogoutClick} />)
      :
      (<LoginButton onClick={this.handleLoginClick} />)
    }
    </div>
  );
}

// Preventing Component from Rendering
function WarningBanner(props) {
  if (!props.warn) {
    return null;  // Return null to prevent a component from rendering
  }
  return (
    <div className="warning">
      Warning!
    </div>
  );

}
```

[Back to top](#Content)

## Lists-&-Keys

Array elements must have ids(keys), to enable REACT optimise

```javascript
const NumberList = (props) => {
  const listItems = props.numbers.map((number) => (
    <li key={number.toString()}>{number}</li>
  ));
  return <ul>{listItems}</ul>;
};

ReactDOM.render(
  <NumberList numbers={[1, 2, 3, 4, 5]} />,
  document.getElementById("root")
);
```

[Back to top](#Content)

## Forms

```js
const onFormSubmit = (event) => {
  event.preventDefault();
  const { username, password } = event.target.element;

  console.log(username.value);
  console.log(password.value);
};

let content = (
  <form onSubmit={onFormSubmit}>
    <input type="text" name="username"></input>
    <input type="password" name="password"></input>
    <button type="submit">submit</button>
  </form>
);
```

[Back to top](#Content)

## Lifting-State-up

Set the state in the parent instead of in each children.

[Back to top](#Content)

## Composition-VS-Inheritance

```js
// Containment (A Comp containing another component)
const Border = props => (
  <div className={'Border Border-' + props.color}>
    {props.children}
  </div>
);

const WelcomeDialog = () => (
  <Border color="blue">
    <h1 className="Dialog-title">
      Welcome
    </h1>
    <p className="Dialog-message">
      Thank you for visiting our spacecraft!
    </p>
  </Border>
);

function FlexContainer = (props) => (
  <div className='FlexContainer' >
    <div className='FlexContainer-left' >
      { props.left }
    </div>
    <div className="FlexContainer-right">
      {props.right}
    </div>
  </div>
);

function App = () => (
  <FlexContainer
    left={<Contacts />}
    right={<Chat />}
  />
);

// Specialization (A component being a special type of another component)
const Dialog = props => (
  <Border color="blue">
    <h1 className="Dialog-title">
      {props.title}
    </h1>
    <p className="Dialog-message">
      {props.message}
    </p>
  </Border>
);

const WelcomeDialog = () =>
  <Dialog title="Welcome"
    message="Thank you for visiting our spacecraft!" />
);
```

[Back to top](#Content)

## Accessibility

(Web accessibility, a11y. Make the app accessible to everyone)

### [WAI-ARIA](https://www.w3.org/TR/wai-aria/#roles_categorization)

Web Accessibility Initiative - Accessible Rich Internet Applications

React supports all aria-\* HTML attrs, and must be hyphen-cased (kebap-case, lisp-case)

### WCAG (Web Content Accessibility Guidelines)

### Mouse and pointer events

```js
class BlurExample extends React.Component {
  constructor(props) {
    super(props);

    this.state = { isOpen: false };
    this.timeOutId = null;
  }

  onClickHandler = () =>
    this.setState(({ isOpen }) => ({
      isOpen: !isOpen,
    }));

  // We close the popover on the next tick by using setTimeout.
  // This is necessary because we need to first check if
  // another child of the element has received focus as
  // the blur event fires prior to the new focus event.
  onBlurHandler = () =>
    (this.timeOutId = setTimeout(() => {
      this.setState({
        isOpen: false,
      });
    }));

  // If a child receives focus, do not close the popover.
  onFocusHandler() {
    clearTimeout(this.timeOutId);
  }

  render() {
    // React assists us by bubbling the blur and
    // focus events to the parent.
    return (
      <div onBlur={this.onBlurHandler} onFocus={this.onFocusHandler}>
        <button
          onClick={this.onClickHandler}
          aria-haspopup="true"
          aria-expanded={this.state.isOpen}
        >
          Select an option
        </button>
        {this.state.isOpen && (
          <ul>
            <li>Option 1</li>
            <li>Option 2</li>
            <li>Option 3</li>
          </ul>
        )}
      </div>
    );
  }
}
```

[Back to top](#Content)

## Code Splitting

```js

// React.lazy (default exports only)

// returns a promise and must be rendered in a Suspense component
const LazyComponent  = React.lazy(() => import('./LazyComponent'));
const LazyComponent2 = React.lazy(() => import('./LazyComponent2'));

function MyComponent() {
  return (
    <div>
      <Suspense fallback={<div>Loading...</div>}>
        <LazyComponent />
        <LazyComponent2 />
      </Suspense>
    </div>
  );
}

// Route-based code splitting

import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import React, { Suspense, lazy } from 'react';

const Home = lazy(() => import('./routes/Home'));
const About = lazy(() => import('./routes/About'));
import {Spinner} from './spinner';

const App = () => (
  <Router>
    <Suspense fallback={<Spinner/>}>
      <Switch>
        <Route exact path="/" component={Home}/>
        <Route path="/about" component={About}/>
      </Switch>
    </Suspense>
  </Router>
);

// react.lazy with named exports

export const MyComponent = /* ... */;
export const MyUnusedComponent = /* ... */;

// MyComponent.js
export { MyComponent as default } from "./ManyComponents.js";

// MyApp.js
import React, { lazy } from 'react';
const MyComponent = lazy(() => import("./MyComponent.js"));
```

[Back to top](#Content)

## [Context](https://reactjs.org/docs/context.html)

```jsx
import ApplicationContext, {
  ApplicationContextType,
} from "../../ApplicationContext";


import { UserType } from "../User/User.model";

export default () => (
  <ApplicationContext.Consumer>
    {({ user }: ApplicationContextType) => <nav></nav>}
  </ApplicationContext.Consumer>
);

// applicationContext.js
import React from 'react';
export default React.createContext();

// app.component.js
import ApplicationContext from "./ApplicationContext";
render () {
  return (
    <ApplicationContext.Provider value={this.state}>
      <BrowserRouter>
        <Header />
        <div id="content" className="m-auto">
          <Suspense fallback={<Spinner/>}>
            <Switch>
              <Route exact path="/" component={Projects}/>
              <Route path="/sign-up" component={User}/>
              <Route path="/sign-in" component={SignIn}/>
              <Route path="/settings" component={Settings}/>
              <Route path="/projects" exact={true} component={Projects}/>
              <Route path="/projects/:id"  exact={true} component={Project}/>
              <Route path="/kanban/:id"  exact={true} component={Kanban}/>
              <Route path="/task" exact={true} component={Task}/>
            </Switch>
          </Suspense>
        </div>
      </BrowserRouter>
    </ApplicationContext.Provider>
  );
}

// settings.component.js
class Settings extends React.component {
  componentDidMount(){
    const {settings} = this.context;  // get settings from the context
    settings && this.setState({settings});
  }
}

Settings.contextType = ApplicationContext;
export default  Settings;

// Functional components

//  AutenfificationContext
import React from 'react';
export default React.createContext(null);

//  Father.js
import React from 'react';
import AutenfificationContext from './AutenfificationContext';


//  Father
const Father = () => (
  <AutenfificationContext.Provider value={ContextData}">
    <SonA />
    <SonB />
  </AutenfificationContext.Provider>
);

// Grand son
const SonX = () => (
  <AutenfificationContext.Consumer">
    {ContextData => (
      <>
        // use the contextData here
      </>
    )}
  </AutenfificationContext.Consumer>
);
```

[Back to top](#Content)

## Error Boundaries

Error boundaries are React components that catch JavaScript errors in their child component tree, partially handle thatm and display a fallback UI instead of the crashed component. IE EB are Components that define 1 or both of the lifecycle methods. Uncought EB errors will lead to componentUnmount

- static getDerivedStateFromError() // Render fallback UI
- componentDidCatch() // log error information

```js
export default class ErrorBoundary extends React.Component {
  state = {
    hasError: false,
    error: null,

  }
  static getDerivedStateFromError(error) {
    return {
      hasError: true,
      error,
    };
  }
  componentDidCatch(error, errorInfo) {
    console.log({ error, errorInfo });
  }
  render() {
    if (this.state.hasError) {
      return <h1>Something went wrong. </h1>;
    } else {
      return this.props.children;
    }
  }
}

// usage
<ErrorBoundary>
  <MyWidget {new Error("Some Error!")}/> <<-- will render <h1>Something went wrong. </h1> instead
</ErrorBoundary>
```

[Back to top](#Content)

## [Refs & DOM](https://reactjs.org/docs/refs-and-the-dom.html)

refs are selectors that parents in class components use to access their children Components and Doms.
A ref cannot be used in a functional Component

```js
// Component.js
accessAReferencedChild = () => {
  const theDom = this.carouselContainerRef.current;
  const children = theDome.children;
}

render() {
  return (
    <div ref={this.carouselContainerRef}>
  )
}
```

[Back to top](#Content)

## [Forwarding Refs](https://reactjs.org/docs/forwarding-refs.html)

Forwarding refs to children component to be able to access the grand child.

```js
const EmailInput = React.forwardRef((props, ref) => (
  <input ref={ref} {...props} type="email" className="AppEmailInput" />
));

class App extends Component {
  emailRef = React.createRef();

  render() {
    return (
      <div>
        <EmailInput ref={this.emailRef} />
        <button onClick={() => this.onClickButton()}>
          Click me to focus email
        </button>
      </div>
    );
  }

  onClickButton() {
    this.emailRef.current.focus();
  }
}
```

[Back to top](#Content)

## [Fragments](https://reactjs.org/docs/fragments.html)

```js
// Surrounding Single
render() {
  return (
    <React. Fragment>
      <ChildA />
      <ChildB />
      <ChildC />
    </React. Fragment>
  );
}

render() {
  return (
    <>
      <ChildA />
      <ChildB />
      <ChildC />
    </>
  );
}

render() {
  return (
    <dl>
      <ChildA />
      <ChildB />
      <ChildC />
    </dl>
  );
}

// motivation

class Table extends React. Component {
  render() {
    return (
      <table>
        <tr>
          <Columns />
        </tr>
      </table>
    );
  }
}

// Keyed Fragments

// React. Fragment syntax may have keys
function Glossary(props) {
  return (
    <dl>
      {props.items.map(item => (
        // Without the `key` , React will fire a key warning
        <React. Fragment key={item.id}>
          <dt>{item.term}</dt>
          <dd>{item.description}</dd>
        </React. Fragment>
      ))}
    </dl>
  );

}
```

[Back to top](#Content)

## HOC (Higher Order Components)

A function that takes a Component and retuirns a new Component

```js
import React from "react";
import ReactDOM from "react-dom";

const MyComponent = (props) => <div>{props.content}</div>;

const requireAutentication = (ContentToBeDisplayed) => {
    return (props) => (
      <div>
        {props.isAutenticated ? (
          <p>;) Welcome, you are autenticated</p>
        ) : (
          <p>;( Please login to view the content</p>
        )}
        <ContentToBeDisplayed {...props} /> // split operator to forward the props
      </div>
    );
  },
  IsAutenticated = requireAutentication(MyComponent);

ReactDOM.render(
  <IsAutenticated isAutenticated={false} info="Page" />,
  document.getElementById("root")
);
```

[Back to top](#Content)

## [Integration with other Libraries](https://reactjs.org/docs/integrating-with-other-libraries.html)

[Back to top](#Content)

## [Router](https://reacttraining.com/react-router/web/guides/quick-start)

```js
npm install --save react-router-dom

// MyRoutes.js
import React from 'react';
import { BrowserRouter, Route, Switch } from "react-router-dom";
import {DashBoard, Create, Edit, Help, NotFound} from '../components/??'

const AppRouter = () => (
  <BrowserRouter>
    <div>
      <Header/>
      <Switch>
        <Route path="/"       component={DashBoard} exact={true} />
        <Route path="/create" component={Create}/>
        <Route path="/edit"   component={Edit}/>
        <Route path="/help"   component={Help}/>
        <Route component={NotFound}/>
      </Switch>
    </div>
  </BrowserRouter>
);

export default AppRouter;

// App.js
import AppRouter from '/router/AppRouter'
ReactDOM.render(routes , document.getElementById('root'));


// components/Header.js
import React from 'react';
import {NavLink} from 'react-router-dom';

// Link vs NavLink is that NavLink can be beatified
const Header = () => (
  <header>
    <h1>App.title</h1>
    <NavLink activeClassName="is-active" to="/"      exact={true}> Go Home</NavLink>
    <NavLink activeClassName="is-active" to="/create"> Create</NavLink>
    <NavLink activeClassName="is-active" to="/edit"> Edit</NavLink>
    <NavLink activeClassName="is-active" to="/help"> Help</NavLink>
  </header>
)

// componentes/MyComponent.js
import React from 'react';

const DashBoard = (props) => {
  console.log(props)
  props contains {
    history,
    location:
    hash, // in case user baseUrl/contacts#fabrice (the id to be scrolled to)
  }
  return ?
)};

export default DashBoard;
```

[Back to top](#Content)

## Optimizing Performance

[Back to top](#Content)

## Portals

Insert a component into a specific container

```js
render() {
  // React does *not* create a new div. It renders the children into `domNode` .
  // `domNode` is any valid DOM node, regardless of its location in the DOM.
  return ReactDOM.createPortal(
    this.props.children,
    domNode
  );
}
```

[Back to top](#Content)

## Profiler

(Prod mod only)

A component that measures the # and cost of rendering it's child component
Each Profiler must have an id

```js
render(
  <App>
    <Profiler id="Navigation" onRender={onRenderCallback}>
      // id: req, onRender: req, called when component updates
      <Navigation {...props} />
    </Profiler>
    <Main {...props} />
  </App>
);

render(
  <App>
    <Profiler id="Panel" onRender={onRenderCallback}>
      <Panel {...props}>
        <Profiler id="Content" onRender={onRenderCallback}>
          <Content {...props} />
        </Profiler>
        <Profiler id="PreviewPane" onRender={onRenderCallback}>
          <PreviewPane {...props} />
        </Profiler>
      </Panel>
    </Profiler>
  </App>
);

function onRenderCallback(
  id, // the "id" prop of the Profiler tree that has just committed
  phase, // either "mount" (if the tree just mounted) or "update" (if it re-rendered)
  actualDuration, // time spent rendering the committed update
  baseDuration, // estimated time to render the entire subtree without memoization
  startTime, // when React began rendering this update
  commitTime, // when React committed this update
  interactions // the Set of interactions belonging to this update
) {
  // Aggregate or log render timings...
}
```

[Back to top](#Content)

## [Reconciliation](https://reactjs.org/docs/reconciliation.html)

Optimising components update predicitibility

[Back to top](#Content)

## Render Props (Pattern used by a Component to share state/data with it's children)

```js

// Using children
class Parent extends React.Component {
  state = { name: 'Flavio', age: 35 }
  render() {
    return (
      <div>
        {this.props.children(this.state)}
      </div>
    )
  }
}

const ChildA = ({name}) => <p>{name}</p>}
const ChildB = ({age}) => <p>{age}</p>}

const App = () => (
  <Parent>{
    ({name, age}) => (
      <div>
        <ChildA name={name} /> }
        <ChildB age={age} /> }
      </div>
    )
  </Parent>
)

ReactDOM.render(<App />, document.getElementById('app'));


// render props
class Parent extends React.Component {
  state = { name: 'Flavio', age: 35 }

  render() {
    return (
      <div>
        <p>Test</p>
        {this.props.someRandomProp(this.state.name)}
        {this.props.anotherRandomProp(this.state.age)}
      </div>
    )
  }
}

const Children1 = props => (<p>{props.name}</p>)
const Children2 = props => (<p>{props.age}</p>)

const App = () => (
  <Parent
    someRandomProp    ={name => <Children1 name={name} />}
    anotherRandomProp ={age =>  <Children2 age={age} />}
  />
)

ReactDOM.render(<App />, document.getElementById('app'));
```

[Back to top](#Content)

## [Static Type Checking](https://reactjs.org/docs/static-type-checking.html)

Typecheckers (Flow and Typescript) id problems before execution. Use them instead of PropTypes.

### [Flow](https://flow.org/en/docs/getting-started/)

### TypeScript

    npm install --save-dev typescript
    npx create-react-app my-app --typescript

[Back to top](#Content)

## Strict Mode (A tool for highlighting potential problems in the application, development mode only)

[Back to top](#Content)

## [PropTypes](https://reactjs.org/docs/typechecking-with-proptypes.html)

```js
import React from 'react'
import PropTypes from 'prop-types'

class User extends React.Component {
  render() {
    return (
      <>
        <h1>{ `Welcome, ${this.props.name}` }</h1>
        <h2>{ `Age, ${this.props.age}` }</h2>
      </>
    )
  }
}

User.propTypes = {
  name  : PropTypes.string  // value not string will log error,
  age   : PropTypes.number.isRequired,  // make it required
  children: PropTypes.element.isRequired <-- Specify that children must be a single element
};

User.defaultProps = {
  name  : "fabrice",
  age   : 30
}

// or

class User extends React.Component {
  static propTypes = {
    name: PropTypes.string.isRequired,
    age: PropTypes.number.isRequired

  }

  static defaultProps = {
    name: 'stranger'
  }
}
```

[Back to top](#Content)

## Uncontrolled Components (Form data controlled by React, using refs to read the value update state)

```js
// Default Values
render() {
  return (
    <form onSubmit={this.handleSubmit}>
      <label>
        Name:
        <input
          defaultValue="Bob"
          type="text"
          ref={this.input} />
      </label>

      <label>
        Name:
        <input
          type="checkbox"
          defaultChecked={true}
          ref={this.checkbox} />
      </label>

      <label>
        Name:
        <input
          type="radio"
          defaultChecked={true}
          ref={this.radio} />
      </label>
      <input type="submit" value="Submit" />
    </form>
  );

}

// File (type='file' is always uncontrolled cuz the value is set by the user)

class FileInput extends React. Component {
  constructor(props) {
    super(props);
    this.fileInput = React.createRef();
  }

  handleSubmit = event  => {
    event.preventDefault();
    alert(
      `Selected file - ${
        this.fileInput.current.files[0].name
      }`
    );
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Upload file:
          <input type="file" ref={this.fileInput} />
        </label>
        <br />
        <button type="submit">Submit</button>
      </form>
    );
  }
}

ReactDOM.render(
  <FileInput />,
  document.getElementById('root')
);
```

[Back to top](#Content)

## Web Components

## [Hooks](https://reactjs.org/docs/hooks-reference.html) (Addition in React 16.8 that let you use state and other React features without writing a class)

### useState

```js
import React, { useState } from 'react';

function Example() {

  const [count, setCount] = useState(0); // Declare a new state variable (count), as well as it's setter

  // Declare multiple state variables!
  const [age, setAge]     = useState(42);
  const [fruit, setFruit] = useState('banana');
  const [todos, setTodos] = useState([{ text: 'Learn Hooks' });

  return (
    <div>
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        Click me
      </button>
    </div>
  );

}
```

### useEffect ([componentDidMount, componentDidUpdate, componentWillUnmount] = useEffect. Effects are operations that can affect other components and can't be done during rendering)

```js
import React, { useState, useEffect } from "react";

function FriendStatus(props) {
  const [isOnline, setIsOnline] = useState(null);

  function handleStatusChange(status) {
    setIsOnline(status.isOnline);
  }

  // Similar to componentDidMount and componentDidUpdate:
  // runs after every render
  useEffect(() => {
    ChatAPI.subscribeToFriendStatus(props.friend.id, handleStatusChange);

    // The rutun function is the equivalent of componentWillUnmount
    // also runs before each re-render
    return () => {
      ChatAPI.unsubscribeFromFriendStatus(props.friend.id, handleStatusChange);
    };
  });

  // You can have multiple useEffect so as to group similar operations,
  // above, you subscribe and unsubscribe, here you update the title
  useEffect(() => {
    document.title = `You clicked ${count} times`;
  });

  if (isOnline === null) {
    return "Loading...";
  }

  return isOnline ? "Online" : "Offline";
}
```

### useContext

### Todo using functional components

```js
import React, {useState} from 'react'
import ReactDOM from 'react-dom'

// componentDidMount + componentDidUpdate for Stateless function
useEffect( () => {
  console.log("tiggers each time any state variable changes")
});

useEffect( () => {
  console.log("tiggered only when: count", count)
}, [count);

useEffect( () => {
  console.log("triggered like componentDidMount")
}, [);

// componentDidMount + componentDidUpdate for Stateless function
useEffect( () => {
  return () => {
    // Here you do the cleanup, which will be triggered when the component is unmounted
  }
});

const NoteApp = () => {

  const [notes, setNotes] = useState([),
        [title, setTitle] = useState(''),
        [body, setBody]    = useState('');

  const addNote = (e) => {
    e.preventDefault();
    const title = e.target.title.value,
          body = e.target.body.value;

    setNotes([
      ...notes,
      {title, body}
    );
    setTitle('');
    setBody('');
  }

  const removeNote = (title) => {
    setNotes(notes.filter( note => title !== note.title))

  }

  return (
    <div>
      <h1>Hello</h1>
      <p>Add Note</p>
      <form onSubmit={addNote}>
        <input name='title' value={title} onChange={ e => setTitle(e.target.value)}
        />
        <textarea name='body' value={body} onChange={ e => setBody(e.target.value)} >
        </textarea>
        <button type='submit'>Add</button>
      </form>
      <ul>
        {notes.map((note, i) => (
          <div key={i}>
            <h3>{note.title}</h3>
            <p>{note.body}</p>
            <button onClick={() => removeNote(note.title)}>remove</button>
          </div>
        ))}
      </ul>
    </div>
  )
}

ReactDOM.render( <NoteApp  /> , document.getElementById('root'));
```

### [Building Your Own Hooks](https://reactjs.org/docs/hooks-custom.html)

[Back to top](#Content)

## Concurrent Mode

## [React Intl](https://github.com/formatjs/react-intl/blob/master/docs/API.md)

## [Testing]

### enzymeJs

[jest-enzyme](https://github.com/FormidableLabs/enzyme-matchers/tree/master/packages/jest-enzyme)

```sh
npm i --save-dev enzyme enzyme-adapter-react-16 chai

# src/setupTests.js

import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
configure({ adapter: new Adapter() });
```

[Example](https://github.com/fabrigeas/react-formg-group)

## Misc

### Data binding

Here is a manual databinding,
Which implements databinding by manually reloading the whole view on event.
However the Virtual Dom ... do not rerender everything but instead ids what element changhed.

```js
let count = 0;
const renderCounterAPP = () => {
  const addOne = () => {
    count += 1;
    renderCounterAPP();
  };
  const reset = () => {
    count = 0;
    renderCounterAPP();
  };

  let temp = (
    <div>
      <h1>Count: {count} </h1>
      <button className="button" onClick={reset}>
        Reset
      </button>
      <button className="button" onClick={addOne}>
        increment
      </button>
      <button
        className="button"
        onClick={() => {
          count -= 1;
          renderCounterAPP();
        }}
      >
        decrement
      </button>
    </div>
  );

  console.log({ temp });
  ReactDOM.render(temp, document.getElementById("root"));
};

renderCounterAPP();
```

### Using scss

```bash
npm install node-sass --save
# nname style files to .scss
```

### Resources

- Hello world
- [regex101](https://regex101.com/)
- [react-dates](https://github.com/airbnb/react-dates)
- [react-addons-shallow-compare](https://www.npmjs.com/package/react-addons-shallow-compare)
- [ES6](http://es6-features.org)
- [Thinking in React](https://reactjs.org/docs/thinking-in-react.html)

[Back to top](#Content)

## publish component as npm package

```bash
npm i -D rollup \
rollup-plugin-typescript2 \
rollup-plugin-sass \
@rollup/plugin-commonjs \
@rollup/plugin-node-resolve \
rollup-plugin-peer-deps-external
```

tsconfig.js

```js
{
  "compilerOptions": {
    "target": "es5",
    "lib": [
      "dom",
      "dom.iterable",
      "esnext"
    ],
    "allowJs": true,
    "skipLibCheck": true,
    "esModuleInterop": true,
    "allowSyntheticDefaultImports": true,
    "strict": true,
    "forceConsistentCasingInFileNames": true,
    "module": "esnext",
    "moduleResolution": "node",
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "react",
    "declaration": true,
    "declarationDir": "build",
    "declarationMap": true
  },
  "include": [
    "src/components/FormGroup/",
  ],
  "exclude": [
    "src/components/FormGroup/FormGroup.test.tsx",
    "src/components/index.tsx",
  ]

}
```

rollup.config.js

```js
import peerDepsExternal from "rollup-plugin-peer-deps-external";
import resolve from "rollup-plugin-node-resolve";
import typescript from "rollup-plugin-typescript2";
import sass from "rollup-plugin-sass";
import commonjs from "rollup-plugin-commonjs";

import packageJson from "./package.json";

export default {
  input: "src/components/index.tsx",
  output: [
    {
      file: packageJson.main,
      format: "cjs",
      sourcemap: true,
    },
    {
      file: packageJson.module,
      format: "esm",
      sourcemap: true,
    },
  ],
  plugins: [
    peerDepsExternal(),
    resolve(),
    commonjs(),
    typescript({ useTsconfigDeclarationDir: true }),
    sass({ insert: true }),
  ],
};
```

package.json

```json
"private": false
"scripts": {
  "build:lib": "npm run build:clean && npm run rollup && npm run copyDTFile && npm run copyMapFile && npm run clanDT",
  "rollup": "rollup -c",
  "copyDTFile": "cp build/FormGroup/FormGroup.d.ts build/index.d.ts",
  "copyMapFile": "cp build/FormGroup/FormGroup.d.ts.map build/index.d.ts.map",
  "clanDT": "rimraf build/FormGroup",
  "build:clean": "rimraf build",
  "build:watch": "rollup -c -w"
},
"main":   "build/index.js",
"types":  "build/index.d.ts",
"module": "build/index.es.js",
"files": [
  "build"
],

npm run build:lib
npm login || npm whoamI
npm version 2.0.1
npm publish --access=public
```

## Typescript

### Context

[ApplicationContext.tsx`](https://gitlab.com/fabrigeas1/tasks-frontend-react.tsx/-/blob/master/src/ApplicationContext.tsx)

[ClassComponent.receiver.tsx](https://gitlab.com/fabrigeas1/tasks-frontend-react.tsx/-/blob/master/src/components/Project/Project.tsx)

[FunctionalComponent.receiver](https://gitlab.com/fabrigeas1/tasks-frontend-react.tsx/-/blob/master/src/components/Template/Template.tsx)

[ErrorBoundary](https://gitlab.com/fabrigeas1/tasks-frontend-react.tsx/-/blob/master/src/components/ErrorBoundary/ErrorBoundary.tsx)

### [Redux](https://gitlab.com/fabrigeas1/tasks-frontend-react.tsx/-/tree/master/src/redux)
