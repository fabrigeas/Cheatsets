# Ember Cheat set

```sh
ember new super-rentals
cd super-rentals

# start
npm start
ember server
http://localhost:4200/

# test
npm test
ember test
```

## Routes

```js
// app/router.js
import EmberRouter from "@ember/routing/router";
import config from "./config/environment";

export default class Router extends EmberRouter {
  location = config.locationType;
  rootURL = config.rootURL;
}

Router.map(function () {
  // http://localhost:4200/about => app/templates/about.hbs
  this.route("about");

  //http://localhost:4200/getting-in-touch => app/templates/contact.hbs
  this.route("contact", { path: "/getting-in-touch" });
});
```

```hbs
  <LinkTo @route="about" class="button">About Us</LinkTo>
  <LinkTo @route="contact" class="button">Contact Us</LinkTo>
  <LinkTo @route="about" class="button">About</LinkTo>
```

## Automated Testing

```sh
ember generate acceptance-test super-rentals

# running tests
ember test --server
#or
ember t -s

# http://localhost:7357
super
```

```js
// tests/acceptance/super-rentals-test.js;
// https://guides.emberjs.com/release/tutorial/part-1/automated-testing/
```

## Components

### state(tracked), actions(methods)

```sh
ember generate component-class rental/image
```

```js
// app/components/rental/image.js
import Component from "@glimmer/component";
import { tracked } from "@glimmer/tracking";
import { action } from "@ember/object";

export default class RentalImageComponent extends Component {
  @tracked isLarge = false;
  @action toggleSize() {
    this.isLarge = !this.isLarge;
  }
}
```

```hbs
<!-- app/components/rental/image.hbs -->
<button type="button" class="image {{if this.isLarge "large"}}" {{on "click" this.toggleSize}}>
  <img ...attributes>
  <small>View {{if this.isLarge "Smaller" "Larger"}}</small>
</button>

<!-- app/templates/index.hbs -->
<div class='content'>
  <Rental::Image
    src="https://upload.wikimedia.org/wikipedia/commons/c/cb/Crane_estate_(5).jpg"
    alt="A picture of Grand Old Mansion"
  />
</div>
```

### params, interpolation

```sh
ember generate component map --with-component-class
```

```js
// app/components/map.js
import Component from "@glimmer/component";
import ENV from "super-rentals/config/environment";

export default class MapComponent extends Component {
  get src() {
    let { lng, lat, width, height, zoom } = this.args;
    let coordinates = `${lng},${lat},${zoom}`;
    let dimensions = `${width}x${height}`;
    let accessToken = `access_token=${this.token}`;

    return `http://${coordinates}/${dimensions}@2x?${accessToken}`;
  }

  get token() {
    return encodeURIComponent(ENV.MAPBOX_ACCESS_TOKEN);
  }
}
```

```hbs
<!-- app/components/map.hbs -->
<div class="map">
  <img alt="Map image at coordinates {{@lat}},{{@lng}}"
    ...attributes
    src={{this.src}} width={{@width}} height={{@height}}>
</div>

<!-- app/components/any.hbs -->
<div >
  <Map
    @lat="37.7749"
    @lng="-122.4194"
    @zoom="9"
    @width="150"
    @height="150"
    alt="A map of Grand Old Mansion"
  />
</div>
```

## Working with Data

```js
// routes/index.js
import Route from "@ember/routing/route";

export default class IndexRoute extends Route {
  // returns an array of rentals
  async model() {
    let response = await fetch("/api/rentals.json");
    return await response.json();
  }
}
```

```hbs
<!-- app/templates/index.hbs -->
<ul class="results">
  {{#each @model as |rental|}}
    <li> <Rental @rental={{rental}} /> </li>
  {{/each}}
</ul>

<!-- app/components/rental.jbs -->
<article class="rental">
  <Rental::Image src={{@rental.image}} alt="A picture of {{@rental.title}}" />
  <div class="details">
    <h3>{{@rental.title}}</h3>
  </div>
  <Map @lat={{@rental.location.lat}} @lng={{@rental.location.lng}} @zoom="9" @width="150" @height="150"
    alt="A map of {{@rental.title}}" />
</article>
```

## Route Params, dynamic segments

```sh
ember generate component rental/detailed
```

```js
// app/router.js
Router.map(function () {
  this.route("rental", { path: "/rentals/:rental_id" });
});

// routes/rental.js
import Route from "@ember/routing/route";
import { inject as service } from "@ember/service";

export default class RentalRoute extends Route {
  @service store;
  async model(params) {
    return this.store.find("rental", params.rental_id);
  }
}
```

```hbs
<LinkTo
  @route="rental"
  @model={{@rental}}> <!-- pass a model argument for dynamic segments-->
  {{@rental.title}}
</LinkTo>

<!-- app/templates/rental.hbs -->
<Rental::Detailed @rental={{@model}} />

<!-- app/components/rental/detailed.hbs -->
<!-- renders a rental -->
```

## Service Injection

### Shopping cart service

```sh
ember generate service shopping-cart
```

```js
// app/services/shopping-cart.js
import { A } from "@ember/array";
import Service from "@ember/service";

export default class ShoppingCartService extends Service {
  items = A([]);

  add(item) {
    this.items.pushObject(item);
  }

  remove(item) {
    this.items.removeObject(item);
  }

  empty() {
    this.items.clear();
  }
}

// app/components/cart-contents.js
import Component from "@glimmer/component";
import { inject as service } from "@ember/service";
import { action } from "@ember/object";

export default class CartContentsComponent extends Component {
  @service("shopping-cart") cart;

  @action
  remove(item) {
    this.cart.remove(item);
  }
}
```

```hbs
<!-- app/components/cart-contents.hbs -->
<ul>
  {{#each this.cart.items as |item|}}
    <li>
      {{item.name}}
      <button type="button" {{on "click" (fn this.remove item)}}>Remove</button>
    </li>
  {{/each}}
</ul>
```

### Default

```sh
ember generate component share-button --with-component-class
```

```hbs
<!-- app/components/share-button.hbs -->
<a
  ...attributes
  href={{this.shareURL}}
  target="_blank"
  rel="external nofollow noopener noreferrer"
  class="share button"
>
  {{yield}}
</a>
```

```js
import { inject as service } from "@ember/service";
import Component from "@glimmer/component";

const TWEET_INTENT = "https://twitter.com/intent/tweet";

export default class ShareButtonComponent extends Component {
  @service router;

  get currentURL() {
    return new URL(this.router.currentURL, window.location.origin);
  }

  get shareURL() {
    let url = new URL(TWEET_INTENT);

    url.searchParams.set("url", this.currentURL);
    ...
    return url;
  }
}
```

## Ember Data

```sh
ember generate model-test rental
```

```js
// app/models/rental.js
import Model, { attr } from "@ember-data/model";

const COMMUNITY_CATEGORIES = ["Condo", "Townhouse", "Apartment"];

export default class RentalModel extends Model {
  @attr title;
  @attr owner;
  @attr city;
  @attr location;
  @attr category;
  @attr image;
  @attr bedrooms;
  @attr description;

  get type() {
    return COMMUNITY_CATEGORIES.includes(this.category)
      ? "Community"
      : "Standalone";
  }
}

// app/routes/index.js
import Route from "@ember/routing/route";
import { inject as service } from "@ember/service";

export default class IndexRoute extends Route {
  @service store;
  async model() {
    return this.store.findAll("rental");
  }
}

// app/routes/rental.js
import Route from "@ember/routing/route";
import { inject as service } from "@ember/service";

export default class RentalRoute extends Route {
  @service store;
  async model(params) {
    return this.store.find("rental", params.rental_id);
  }
}

// app/adapters/application.js
import JSONAPIAdapter from "@ember-data/adapter/json-api";

export default class ApplicationAdapter extends JSONAPIAdapter {
  namespace = "api";

  buildURL(...args) {
    return `${super.buildURL(...args)}.json`;
  }
}

// app/serializers/application.js
import JSONAPISerializer from "@ember-data/serializer/json-api";

export default class ApplicationSerializer extends JSONAPISerializer {}
```

## Provider Components

```sh
generate component-class rentals rentals/filter
```

```js
// app/components/rentals.js
import Component from "@glimmer/component";
import { tracked } from "@glimmer/tracking";

export default class RentalsComponent extends Component {
  @tracked query = "";
}

// app/components/rentals/filter.js
import Component from "@glimmer/component";

export default class RentalsFilterComponent extends Component {
  get results() {
    let { rentals, query } = this.args;

    if (query) {
      rentals = rentals.filter((rental) => rental.title.includes(query));
    }

    return rentals;
  }
}
```

```hbs
<!-- app/components/rentals.hbs -->
<div class="rentals">
  <label>
    <span>Where would you like to stay?</span>
    <Input @value={{this.query}} class="light" />
  </label>

  <ul class="results">
    <Rentals::Filter @rentals={{@rentals}} @query={{this.query}} as |results|>
      {{#each results as |rental|}}
        <li> <Rental @rental={{rental}} /> </li>
      {{/each}}
    </Rentals::Filter>
  </ul>
</div>

<!-- app/components/rentals/filter.hbs -->
{{yield this.results}}
```

## Concepts

### Component

#### Component Arguments and HTML Attributes

```hbs
<!-- any.hbs -->
<Avatar
  <!-- params -->
  @title="Zoey's avatar"
  @initial="Z"

  <!-- attributes -->
  class="current-user"
  disabled=true
/>

<!-- avatar.hbs -->
<div ...attributes>
  <div class="avatar" title={{@title}}>
    {{@initial}}
  </div>
</div>
```

#### Conditional Content

```hbs
{{#if this.thingIsTrue}}
  Content for the block form of "if"
{{/if}}

<div class={{if this.thingIsTrue "value-if-true" "value-if-false"}}>
  This div used the inline "if" to calculate the class to use.
</div>

{{#if condition}}
  {{!-- some content --}}
{{else}}
  {{!-- some other content --}}
{{/if}}

{{#if condition1}}
  ...
{{else if condition2}}
  ...
{{else if condition3}}
  ...
{{else}}
  ...
{{/if}}
```

#### Helper Functions

```js
import Helper from "@ember/component/helper";

export default class Substring extends Helper {
  compute([string], { start, end }) {
    return string.substring(start || 0, end);
  }
}
```

```hbs
<Message::Avatar @initial={{substring @username start=0 length=1}} />

<!-- nested helpers -->
{{sum (multiply 2 4) 2}}

<MyComponent
  <!-- Built-in Helpers.array: pass an array directly from template -->
  @people={{array
    'Tom Dale'
    'Yehuda Katz'
    this.myOtherPerson
  }}

<!-- Built-in Helpers.hash: pass object directly from template -->
  @person={{hash
    firstName='Jen'
    lastName='Weber'
  }}
/>
```

#### State, Actions, Computed, events

```js
// app/components/counter.js
import Component from "@glimmer/component";
import { tracked } from "@glimmer/tracking";
import { action } from "@ember/object";

export default class CounterComponent extends Component {
  @tracked count = 0;

  get total() {
    return this.count * this.args.multiple;
  }

  @action
  change(amount) {
    this.count = this.count + amount;
  }

  @action
  double() {
    this.args.updateMultiple(this.args.multiple * 2);
  }
}

// app/components/double-it.js
import Component from "@glimmer/component";
import { tracked } from "@glimmer/tracking";
import { action } from "@ember/object";

export default class DoubleItComponent extends Component {
  @tracked multiple = 1;

  @action
  updateMultiple(newMultiple) {
    this.multiple = newMultiple;
  }
}
```

```hbs
<!-- app/components/counter.hbs -->
<p>{{this.count}}</p>
<p>× {{@multiple}}</p>
<p>= {{this.total}}</p>

<button type="button" {{on "click" (fn this.change 1)}}>+1</button>
<button type="button" {{on "click" (fn this.change -1)}}>-1</button>

<button type="button" {{on "click" this.double}}>Double It</button>

<!-- app/components/double-it.hbs -->
<Counter @multiple={{this.multiple}} @updateMultiple={{this.updateMultiple}}
```

#### Looping Through Lists

```hbs
<!-- app/components/messages.hbs -->
<div class="messages">
  {{#each this.messages as |message index|}}
    <Message
      @username={{message.username}}
      @userIsActive={{message.active}}
      @userLocaltime={{message.localTime}}
    >
      {{{message.content}}}
    </Message>
  {{/each}}

  <NewMessageInput @onCreate={{this.addMessage}} />
</div>

<!-- loop through object -->
{{#each-in this.categories as |key value|}}
```

```js
// app/components/messages.js
import Component from "@glimmer/component";
import { action } from "@ember/object";
import { A } from "@ember/array";

export default class MessagesComponent extends Component {
  username = "Zoey";

  @action
  addMessage(messageText) {
    this.messages.pushObject({
      username: this.username,
      active: true,
      content: `<p>${messageText}</p>`,
    });
  }

  messages = A([]);
}
//
```

#### Template Lifecycle, DOM, and Modifiers

```hbs
<!-- Conditional Attributes -->
<div class={{if @isAdmin "superuser" "standard"}}>
  Welcome to my app.
</div>
```

```hbs
<form>
  <input {{did-insert this.focus}}>
</form>
```

#### ember modifier

```sh
ember install ember-modifier
ember generate modifier autofocus
```

```js
// app/modifiers/autofocus.js
import { modifier } from "ember-modifier";

export default modifier((element) => element.focus());
```

```hbs
<!-- app/components/edit-form.hbs -->
<form>
  <input {{autofocus}}>
</form>
```

#### Communicating Between Elements in a Component (refs)

```hbs
<!-- app/components/audio-player.hbs -->
<audio src={{@srcURL}} {{ref this "audioElement"}} />

<button type="button" {{on "click" this.play}}>Play</button>
<button type="button">Pause</button>
```

```js
// app/components/audio-player.js
import Component from "@glimmer/component";
import { action } from "@ember/object";

export default class AudioPlayerComponent extends Component {
  @action
  play() {
    this.audioElement.play();
  }
}
```

#### Out-of-Component Modifications (modal)

```sh
ember install ember-modifier
ember generate modifier on-click-outside
```

```js
// app/modifiers/on-click-outside.js
import { modifier } from "ember-modifier";

export default modifier((element, [callback]) => {
  function handleClick(event) {
    if (!element.contains(event.target)) {
      callback();
    }
  }

  document.addEventListener("click", handleClick);

  return () => {
    document.removeEventListener("click", handleClick);
  };
});
```

```hbs
<!-- app/components/modal.hbs -->
<div class="modal" {{on-click-outside @clickedOutside}}>
  {{yield}}
</div>
```

```hbs
<!-- app/components/sidebar.hbs -->
<p class="help-icon" {{on "click" this.showHelp}}>?</p>

{{#if this.showingHelp}}
  <Modal @clickedOutside={{this.hideHelp}}>
    Here's some interesting facts about the sidebar that you can learn.
  </Modal>
{{/if}}
```

```js
// app/components/sidebar.js
import Component from "@glimmer/component";
import { tracked } from "@glimmer/tracking";
import { action } from "@ember/object";

export default class SidebarComponent extends Component {
  @tracked showingHelp = false;

  @action
  showHelp() {
    this.showingHelp = true;
  }

  @action
  hideHelp() {
    this.showingHelp = false;
  }
}
```

### Routing

#### Routes Model

```js
// app/routes/favorite-posts.js
import Route from "@ember/routing/route";

export default class FavoritePostsRoute extends Route {
  model() {
    return [
      { title: "Ember Roadmap" },
      { title: "Accessibility in Ember" },
      { title: "EmberConf Recap" },
    ];
  }
}
```

```js
// app/routes/favorite-posts.js using ember data
import Route from "@ember/routing/route";
import { inject as service } from "@ember/service";

export default class FavoritePostsRoute extends Route {
  @service store;
  model() {
    return this.store.findAll("posts");
  }
}
```

```hbs
<!-- app/templates/favorite-posts.hbs -->
{{#each @model as |post|}}
  <div>
    {{post.title}}
  </div>
{{/each}}
```

#### Multiple models

```js
import Route from "@ember/routing/route";
import RSVP from "rsvp";

export default class SongsRoute extends Route {
  model() {
    return RSVP.hash({
      songs: this.store.findAll("song"),
      albums: this.store.findAll("album"),
    });
  }
}
```

#### Dynamic Models (dynamic segments)

```js
// app/router.js
Router.map(function () {
  this.route("post", { path: "/post/:post_id" });
});

// app/routes/post.js
import Route from "@ember/routing/route";

export default class PhotoRoute extends Route {
  model(params) {
    return this.store.findRecord("post", params.post_id);
  }
}
```

```hbs
{{#each @model as |photo|}}
  <LinkTo @route="posts" @model={{post.id}}>
    link text to display
  </LinkTo>
{{/each}}

<!-- /photos/4/comments/18 -->
<LinkTo @route="photos.photo.comments.comment" @models={{array 4 18}}>
  link text to display
</LinkTo>
```

### Data

```sh
ember generate model person

# custom type dollar
ember generate transform dollars
```

```js
// app/models/person.js
import Model, { attr } from "@ember-data/model";
export default class PersonModel extends Model {
  @attr("string") firstName;
  @attr("date") birthday;
  @attr tags; // a read-only array
  @attr("date", {
    defaultValue() {
      return new Date();
    },
  })
  createdAt;
  get fullName() {
    return `${this.firstName} ${this.lastName}`;
  }
}

// app/transforms/dollars.js
// a custom type
import Transform from "@ember-data/serializer/transform";
export default class DollarsTransform extends Transform {
  deserialize(serialized) {
    return serialized / 100; // returns dollars
  }
  serialize(deserialized) {
    return deserialized * 100; // returns cents
  }
}

// app/models/product.js
import Model, { attr } from "@ember-data/model";
export default class ProductModel extends Model {
  @attr("dollars") spent;
}
```

#### Finding Records

```js
this.store
  .findRecord("blog-post", 1) // => GET /blog-posts/1
  .then(function (blogPost) {});

// GET to /persons?filter[name]=Peter
this.store
  .query("person", {
    filter: {
      name: "Peter",
    },
  })
  .then(function (peters) {});

let blogPost = this.store.peekRecord("blog-post", 1); // => no network request

// GET /blog-posts
this.store
  .findAll("blog-post") // => GET /blog-posts
  .then(function (blogPosts) {});

let blogPosts = this.store.peekAll("blog-post"); // => no network request
```

#### Creating Records

```js
store.createRecord("post", {
  title: "Rails is Omakase",
  body: "Lorem ipsum",
});

// update record
this.store.findRecord("person", 1).then(function (tyrion) {
  tyrion.firstName = "Yollo";
});

// Persisting Records
let post = store.createRecord("post", {
  title: "Rails is Omakase",
  body: "Lorem ipsum",
});

post.save(); // => POST to '/posts'

store.findRecord("post", 1).then(function (post) {
  post.title = "A new post";
  post.save(); // => PATCH to '/posts/1'
});

// DELETE RECORDS
let post = store.peekRecord("post", 1);
post.deleteRecord();
post.isDeleted; // => true
post.save(); // => DELETE to /posts/1

// OR
post = store.peekRecord("post", 2);
post.destroyRecord(); // => DELETE to /posts/2
```
