  # [VueJs Tutorials](https://vuejs.org/v2/guide/)

    npm install -g @vue/cli

## Content 

  + [Create new Project](#Create-new-Project)
  + [Hello](#Hello)
  + [The Vue Instance](#The-Vue-Instance)
  + [Template Syntax](#Template-Syntax)
  + [Computed Properties and Watchers](#Computed-Properties-and-Watchers)
  + [Class and Style Bindings](#Class-and-Style-Bindings)
  + [Conditional Rendering](#Conditional-Rendering)
  + [List Rendering](#List-Rendering)
  + [Event Handling](#Event-Handling)
  + [Form Input Bindings](#Form-Input-Bindings)
  + [Components Basics](#Components-Basics)
  + [Components In-Depth](#Components-In-Depth)
  + [Handling Edge Cases](#Handling-Edge-Cases)
  + [Reusability & Composition](#Reusability-and-Composition)
  + [Tooling](#Tooling)
  + [](#)
  + [Slot](#Slot)
  + [State Management with Vuex](#Vuex)
  + [Vue Router](#Vue-Router)
  + [Vue Publish package](#Publish-Package)

## Hello

    npm install --global @vue/cli
    vue create hello-world
    npm run seve

[Vue](https://vuejs.org/)
[Vue CLI](https://cli.vuejs.org/)
[Vue Router](https://router.vuejs.org/)
[Vuex](https://vuex.vuejs.org/)
[awesome-vue](https://github.com/vuejs/awesome-vue)
[Vue API](https://vuejs.org/v2/api/)
[Vue Test Utils](https://vue-test-utils.vuejs.org/)
[Vue Test Utils](https://vue-test-utils.vuejs.org/)
[Vue with Preprocessors](https://vue-loader.vuejs.org/guide/pre-processors.html#sass)

[Back to top](#Content)

## The Vue Instance

creation 

    var vm = new Vue({

    });

[Back to top](#Content)

### Data and methods 

Properties init in data become reative (view is re-rendered when they change)

    var data = { a: 1 }

    var vm = new Vue({

      data, 
    })

    vm.a == data.a // => true

    vm.a = 2
    data.a // => 2

    data.a = 3
    vm.a // => 3

    vm.b = 'hi' will not re-render because 'b' is not part of the initial state

Freezing (makind vm.data unreactive)

    var data = {
      foo: 'bar'
    }

    Object.freeze(data); // data is not reactive anymore

    new Vue({
      el: '#app',
      data,
    })

Instance properties ($)

    var data = { a: 1 }

    var vm = new Vue({

      el: '#example', 
      data: data
    })

    vm.$data === data // => true
    vm.$el === document.getElementById('example') // => true

    // $watch is an instance method
    vm.$watch('a', function (newValue, oldValue) {
      // This callback will be called when `vm.a` changes
    })

[Back to top](#Content)

### Instance Lifecycle Hooks

    new Vue({
      created: function () { },
      mounted: function () { },
      updated: function () { },
      destroyed: function () { },
    })

[Back to top](#Content)

## Template Syntax

Moustache {{msg}}

    <span>Message: {{ msg }}</span>

    # one-time interpolations
    <span v-once>This will never change: {{ msg }}</span>

Raw HTML (to bind textContent)

    <p>Using mustaches: {{ rawHtml }}</p>
    <p>Using v-html directive: <span v-html="rawHtml"></span></p>

### Attributes

### Directives (v-)

Arguments

### Conditionals and Loops

v-for

    <template>
      <ol>
        <li v-for="todo in todos"> {{ todo.text }} </li>
      </ol>
    </template>

    <script>

    export default {

        data: () => ({
            message: "This is a message",
        }),
        data: () => ({
            todos: [
                { text: 'Learn JavaScript' },
                { text: 'Learn Vue' },
                { text: 'Build something awesome' }
            ]
      })
    }

    </script>

v-if

    {{#if ok}}
        <h1>Yes</h1>
    {{/if}}

    // if else if
    <div v-if="type === 'A'"> A </div>
    <div v-else-if="type === 'B'"> B </div>
    <div v-else-if="type === 'C'"> C </div>
    <div v-else> Not A/B/C </div>

    // Controlling Reusable Elements with key
    <template v-if="loginType === 'username'">
        <label>Username</label>
        <input placeholder="Enter your username" key="username-input">
    </template>
    <template v-else>
        <label>Email</label>
        <input placeholder="Enter your email address" key="email-input">
    </template>

    # v-show
    <h1 v-show="ok">Hello!</h1>

### Handling User Input ( v-on )

    <template>
        <div id="app-5">
            <p>{{ message }}</p>
            <button v-on:click="reverseMessage">Reverse Message</button>
    </template>

    <script>

    export default{
      data: () =>({
        message: 'Hello Vue.js!'
      }), 
      methods: {
        reverseMessage: function () {
          this.message = this.message.split('').reverse().join('')
        }

      }
    })

    </script>

### v-model (2-way binding)

    <div id="app-6">
      <p>{{ message }}</p>
      <input v-model="message"> # model is a data().message
    </div>

### Composing with Components

Summary

    <div id="app">
      <app-nav></app-nav>
      <app-view>
        <app-sidebar></app-sidebar>
        <app-content></app-content>
      </app-view>
    </div>

 parent-element.vue

    <template>
      <div id="app">
        <child-element msg="Hello world"/>
        <h2>{{title}}</h2>
      </div>

    <script>

    import child-element from './components/child-element.vue'

    export default {

      name: 'app',
      components: {
        child-element
      },
      data: function() {
        return {
          title: "This has functionned"
        }

      }
    }

    <style></style>

child-element.vue

    <template>
      <div class="hello">
        <h1>{{ msg }}</h1>
      </div>

    <script>

    export default {

      name: 'child-element',
      props: {
        msg: String

      }
    }

    <style></style>

### Template Syntax

    #v-once
    <span v-once>This will never change: {{ msg }}</span>

    #rawHtml
    <p>Using mustaches: {{ rawHtml }}</p>
    <p>Using v-html directive: <span v-html="rawHtml"></span></p>

Attributes

    <div v-bind:id="dynamicId"></div>
    <button v-bind:disabled="isDisabled">Button</button>

    {{ number + 1 }}
    {{ ok ? 'YES' : 'NO' }}
    {{ message.split('').reverse().join('') }}
    <div v-bind:id="'list-' + id"></div>

    <!-- this is a statement, not an expression: -->
    {{ var a = 1 }}

    <!-- flow control won't work either, use ternary expressions -->
    {{ if (ok) { return message } }}

#### Directives

    <p v-if="seen">Now you see me</p>
    # arguments
    <a v-bind:href="url"> ... </a>
    <a v-on:click="doSomething"> ... </a>

    # Dynamic Arguments
    // eventName/attributeName = focus, click, href, ..., null will remove the binding
    <a v-bind:[attributeName]="url"> ... </a>
    <a v-on:[eventName]="doSomething"> ... </a>

#### [Modifiers](https://vuejs.org/v2/guide/events.html#Event-Modifiers)

    <form v-on:submit.prevent="onSubmit"> ... </form>

#### Shorthands

    <a v-bind:href="url"> ... </a>
    <a :href="url"> ... </a>
    <a :[key]="url"> ... </a>
    <!-- full syntax -->

v-on Shorthand

    <a v-on:click="doSomething"> ... </a>
    <a @click="doSomething"> ... </a>
    <a @[event]="doSomething"> ... </a>

### [Computed Properties and Watchers](https://vuejs.org/v2/guide/computed.html)

    <div id="example">
      <p>Original message: "{{ message }}"</p>
      <p>Computed reversed message: "{{ reversedMessage }}"</p>
    </div>

    <script>

      export default{
        data: () =>({}), 
        methods: {
          reverseMessage: function () {
            this.message = this.message.split('').reverse().join('')
          }
      })

Computed Caching vs Methods

    <p>Reversed message: "{{ reverseMessage() }}"</p>

    computed: {
        fullname: function () {
            return this.firstname  + ' ' + this.lastname
        }
    }

### [Class and Style Bindings](https://vuejs.org/v2/guide/class-and-style.html)

Object Syntax

    <div v-bind:class="{ active: isActive, 'text-danger': hasError }"></div>
    <div v-bind:class="classes"></div>
    <div v-bind:class="magicClasses"></div>

    data: ()=>({
        isActive: true,
        hasError: false,
        classes: {isActive, hasError}
    }),
    computed: {
        magicClasses: ()=> ({
            active: this.isActive && !this.error,
            'text-danger': this.error && this.error.type === 'fatal'
        })
    }

Array Syntax

    # active text-danger
    <div v-bind:class="[activeClass, errorClass]"></div>
    <div v-bind:class="[isActive ? activeClass : '', errorClass]"></div>

    # equivalent to above
    <div v-bind:class="[{ active: isActive }, errorClass]"></div>

    data: ()=> ({
        activeClass: 'active',
        errorClass: 'text-danger'
    })

With Components

    # appends baz boo to component
    <child-component class="baz boo"></child-component>

Binding Inline Styles

    <div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>
    <div v-bind:style="style"></div>
    <div v-bind:style="[baseStyles, overridingStyles]"></div>

    <div v-bind:style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div>

    data: ()=> ({
        style: {
            color: 'red',
            fontSize: '13px'
        },
        activeColor: 'red',
        fontSize: 30
    })

### List Rendering

#### Simple todo

    <div id="todo-list-example">
        <form v-on:submit.prevent="addNewTodo">
            <label for="new-todo">Add a todo</label>
            <input v-model="newTodoText" id="new-todo" placeholder="E.g. Feed the cat">
            <button>Add</button>
        </form>
        <ul>
            <li
                is="todo-item"
                v-for="(todo, index) in todos"
                v-bind:key="todo.id"
                v-bind:title="todo.title"
                v-on:remove="todos.splice(index, 1)"
            ></li>
        </ul>
    </div>

    <script>

     data: {
        newTodoText: '', 
            todos: [], 
            nextTodoId: todos.length
        }, 
        methods: {
            addNewTodo: function () {
                this.todos.push({
                    id: this.nextTodoId++, 
                    title: this.newTodoText
                })
                this.newTodoText = ''
            }
        }

#### Summary

    <my-component
        v-for="(item, index) in items"
        v-bind:item="item"
        v-bind:index="index"
        v-bind:key="item.id"
    ></my-component>

v-for

    <li v-for="item in items">{{ item.message }} </li>

    // index
    <ul>
        <li v-for="(item, index) in items"> {{ parentMessage }} - {{ index }} - {{ item.message }} </li>
    </ul>

     data: ()=> ({
        items: [
            { message: 'Foo' },
            { message: 'Bar' }
        ]
    })

v-for with an Object //Object's attributes as list items

    <ul id="v-for-object" >
        <li v-for="value in object"> {{ value }} </li>

        // key
        <div v-for="(value, key) in object">
             {{ key }}: {{ value }}
        </div>

        // key and index
        <div v-for="(value, key, index) in object">
            {{ index }}. {{ key }}: {{ value }}
        </div>
    </ul>

    data: ()=> ({
        object: {
            firstName: 'John',
            lastName: 'Doe',
            age: 30
        }
    })

key (tracks ids)

    <div v-for="item in items" :key="item.id"> </div>

[Array Change Detection](https://vuejs.org/v2/guide/list.html#Array-Change-Detection)

Displaying Filtered/Sorted Results

    <li v-for="n in evenNumbers">{{ n }}</li>

    data: {
        numbers: [ ]
    },
    computed: {
        evenNumbers: function () {
            return this.numbers.filter(function (number) {
                return number % 2 === 0
            })
        }
    }

v-for with a Range

    <div>
        <span v-for="n in 10">{{ n }} </span>
    </div>

### [Event Handling](https://vuejs.org/v2/guide/events.html)

    <div id="example-1">
        <button v-on:click="counter += 1">Add 1</button>
        <button v-on:click="incrementCounter()">Add 1</button>
        <button v-on:click="methodWithEvent('valiue, $event)">Add 1</button>
        <p>The button above has been clicked {{ counter }} times.</p>
    </div>

    data: {
        counter: 1
    },
    methods: {
        incrementCounter = () =>{},
        methodWithEvent = (msg, event) => {}
    }

Event Modifiers : .preventDefault(), stopPropagation()

    <!-- the click event's propagation will be stopped -->
    <a v-on:click.stop="doThis"></a>

    <!-- the submit event will no longer reload the page -->
    <form v-on:submit.prevent="onSubmit"></form>

    <!-- modifiers can be chained -->
    <a v-on:click.stop.prevent="doThat"></a>

    <!-- just the modifier -->
    <form v-on:submit.prevent></form>

    <!-- use capture mode when adding the event listener -->
    <!-- i.e. an event targeting an inner element is handled here before being handled by that element -->
    <div v-on:click.capture="doThis">...</div>

    <!-- only trigger handler if event.target is the element itself -->
    <!-- i.e. not from a child element -->
    <div v-on:click.self="doThat">...</div>

    <!-- the click event will be triggered at most once -->
    <a v-on:click.once="doThis"></a>

    <!-- in case it contains `event.preventDefault()` -->
    <div v-on:scroll.passive="onScroll">...</div>

[Key Modifiers](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key/Key_Values)

    <!-- only call `vm.submit()` when the `key` is `Enter` -->
    <input v-on:keyup.enter="submit">

    <input v-on:keyup.page-down="onPageDown">
    <input v-on:keyup.13="submit">

[Form Input Bindings](https://vuejs.org/v2/guide/forms.html)

[Components](https://vuejs.org/v2/guide/components.html)

Passing Data to Child Components with Props

    // child-component.vue

    <script>

        props: ['post'], 
        template: '<h3>{{ post.title }}</h3>'

    // parent-component.js
    <child-component v-for="post in posts" v-bind:key="post.id" v-bind:post="post" ></child-component>

    <script>

    import child-component from './components/child-component.vue'; 
      components: {
        child-component
    }, 
    data: () => ({posts: []})

Listening to Child Components Events

    //parent
    data: () => ({
        posts: [],
        postFontSize: 1
    }),
    methods: {
        enlargeText = (enlargeAmount) =>{
            this.postFontSize += enlargeAmount;
        }
    }
    <div :style="{ fontSize: postFontSize + 'em' }">
        <blog-post v-for="post in posts"
            v-bind:key="post.id"
            v-bind:post="post"
            v-on:enlarge-text="enlargeText()">
        </blog-post>
    </div>

    // blog-post.vue
    data: () => ({
        props: ['post'],
    })

    <div class="blog-post">
        <h3>{{ post.title }}</h3>
        <button v-on:click="$emit('enlarge-text', 0.1)"> Enlarge text </button>
        <div v-html="post.content"></div>
    </div>

Using v-model on Components

    //parent
    <custom-input v-bind:value="searchText" v-on:input="searchText = $event" ></custom-input>
    # better
    <custom-input v-model="searchText"></custom-input>

    //custom-input
    <input v-bind:value="value" v-on:input="$emit('input', $event.target.value)" >

Content Distribution with Slots

    // parent.vue.js
    <alert-box> Something bad happened. </alert-box>

    //alert-box - Something bad happened fill the <slot/>
    <div class="demo-alert-box">
      <strong>Error!</strong>
      <slot></slot>
    </div>

[Dynamic Components - is](https://vuejs.org/v2/guide/components.html#Dynamic-Components)

### [Props](https://vuejs.org/v2/guide/components-props.html)

    <!-- Kebab-case -->
    <blog-post post-title="hello!"></blog-post>

prop types

    # Without tyepes
    props: ['title', 'likes', 'isPublished', 'commentIds', 'author']

    # With types
    props: {
        title: String,
        likes: Number,
        isPublished: Boolean,
        commentIds: Array,
        author: Object
    }

Passing Static or Dynamic Props

    <blog-post title="My journey with Vue"></blog-post>

    # Dynamically assign the value of a variable
    <blog-post v-bind:title="post.title"></blog-post>

    # Dynamically assign the value of a complex expression
    <blog-post v-bind:title="post.title + ' by ' + post.author.name" ></blog-post>

    # Passing nunbers
    <blog-post v-bind:likes="42"></blog-post>
    <blog-post v-bind:likes="post.likes"></blog-post>

    # Passing booleans
    <blog-post is-published></blog-post>
    <blog-post v-bind:is-published="false"></blog-post>
    <blog-post v-bind:is-published="post.isPublished"></blog-post>

    # Passing Arrays
    <blog-post v-bind:comment-ids="[234, 266, 273]"></blog-post>
    <blog-post v-bind:comment-ids="post.commentIds"></blog-post>

    # Passing Objects
    <blog-post v-bind:author="{ name: 'Veronica', company: 'Veridian Dynamics' }"> </blog-post>
    <blog-post v-bind:author="post.author"></blog-post>

    # Passing the Properties of an Object where post ={id, title}
    <blog-post v-bind="post"></blog-post>
    <blog-post v-bind:id="post.id" v-bind:title="post.title" ></blog-post>

### [Custom Events](https://vuejs.org/v2/guide/components-custom-events.html)

Event names- better use lowercase

    //better use lowercase for functions
    # child-component.vue
    this.$emit('myevent')

    # parent-component.vue
    <child-component v-on:myevent="doSomething"></child-component>

Customizing Component v-model

    # base-checkbox

    <script>

    model: {
        prop: 'checked', 
        event: 'change'
    }, 
    props: {checked: Boolean}, 

    <template>
    <input
        type="checkbox"
        v-bind:checked="checked"
        v-on:change="$emit('change', $event.target.checked)">

    # parent-component-vue
    <base-checkbox v-model="lovingVue"></base-checkbox

Slot

    <navigation-link url="/profile">
        <font-awesome-icon name="user"></font-awesome-icon>
        Your Profile
    </navigation-link>

    # navigation-link-component.vue
    <a v-bind:href="url" class="nav-link">
        <slot></slot> // <slot> will be replaced by the content fa, text
        <slot>Submit</slot> //fallback content in case the parent doesnt send the slot
    </a>

[Back to top](#Content)

## Computed Properties and Watchers

### Computed Properties (computed caching)

* Use computed properties to avoid doing computation on the template.

    <div id="example">
      <p>Original message: "{{ message }}"</p>
      <p>Reversed message: "{{ reversedMessage }}"</p>
    </div>

    var vm = new Vue({

      computed: {
        /*is reactive, will only render if message changes*/
        reversedMessage: function () {
          return this.message.split('').reverse().join('')
        }, 

      }
    })

    #reversedMessage wil cache the result and  only re-render if the value of message changed

    // vm.reversedMessage
    // vm.reversedMessage

### Computed Caching vs Methods

    <div id="example">
      <p>Using method, time is: "{{ now }}"</p>
      <p>Using caching, time is: "{{ computedNow }}"</p>
    </div>

    var vm = new Vue({

      methods: {
        /*Works because methods is called for each render*/
        now: function {
          return Date.now(); 
        }
      }, 

      computed: {
        /*Is a bad Idea, because Date is not reactive*/
        now: function () {
          return Date().now()
        },

      }
    })

### Computed vs Watched Property

It is tempting to use watchers to react to changes, but is it optimal?

    <div id="demo">{{ fullName }}</div>

    var vm = new Vue({

      el: '#demo', 
      data: {
        firstName: 'Foo', 
        lastName: 'Bar', 
        fullName: 'Foo Bar'
      }, 
      watch: {
        firstName: function (val) {
          this.fullName = val + ' ' + this.lastName
        }, 
        lastName: function (val) {
          this.fullName = this.firstName + ' ' + val
        }

      }
    })

    The above code is imperative and repetitive. Compare it with a computed property version:

    var vm = new Vue({

      el: '#demo', 
      data: {
        firstName: 'Foo', 
        lastName: 'Bar'
      }, 
      computed: {
        fullName: function () {
          return this.firstName + ' ' + this.lastName
        }

      }
    })

### Computed Setter

    computed: {
      fullName: {
        // getter
        get: function () {
          return this.firstName + ' ' + this.lastName
        },
        // setter
        set: function (newValue) {
          var names = newValue.split(' ')
          this.firstName = names[0]
          this.lastName = names[names.length - 1]
        }

      }
    }

### Watchers

    <div id="watch-example">
      <p>
        Ask a yes/no question:
        <input v-model="question">
      </p>
      <p>{{ answer }}</p>
    </div>

    <!-- Since there is already a rich ecosystem of ajax libraries    -->
    <!-- and collections of general-purpose utility methods, Vue core -->
    <!-- is able to remain small by not reinventing them. This also   -->
    <!-- gives you the freedom to use what you're familiar with.      -->

    <script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>

    <script>

    var watchExampleVM = new Vue({
      el: '#watch-example', 
      data: {
        question: '', 
        answer: 'I cannot give you an answer until you ask a question!'
      }, 
      watch: {
        // whenever question changes, this function will run
        question: function (newQuestion, oldQuestion) {
          this.answer = 'Waiting for you to stop typing...'
          this.debouncedGetAnswer()
        }
      }, 
      created: function () {
        // _.debounce is a function provided by lodash to limit how
        // often a particularly expensive operation can be run.
        // In this case, we want to limit how often we access
        // yesno.wtf/api, waiting until the user has completely
        // finished typing before making the ajax request. To learn
        // more about the _.debounce function (and its cousin
        // _.throttle), visit: https://lodash.com/docs#debounce
        this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
      }, 
      methods: {
        getAnswer: function () {
          if (this.question.indexOf('?') === -1) {
            this.answer = 'Questions usually contain a question mark. ; -)'
            return
          }
          this.answer = 'Thinking...'
          var vm = this
          axios.get('https://yesno.wtf/api')
            .then(function (response) {
              vm.answer = _.capitalize(response.data.answer)
            })
            .catch(function (error) {
              vm.answer = 'Error! Could not reach the API. ' + error
            })
        }

      }
    })

    </script>

[Back to top](#Content)

## Class and Style Bindings

[Back to top](#Content)

### Binding HTML Classes

Object Syntax

    <div v-bind:class="{ active: isActive }"></div>

    <div
      class="static"
      v-bind:class="{ active: isActive, 'text-danger': hasError }"
    ></div>

    data: {
      isActive: true,
      hasError: false
    }

Computed class

    <div v-bind:class="classObject"></div>

    data: {
      isActive: true,
      error: null
    },
    computed: {
      classObject: function () {
        return {
          active: this.isActive && !this.error,
          'text-danger': this.error && this.error.type === 'fatal'
        }

      }
    }

Array Syntax

    <div v-bind:class="[activeClass, errorClass]"></div>
    <div v-bind:class="[isActive ? activeClass : '', errorClass]"></div>
    data: {
      activeClass: 'active',
      errorClass: 'text-danger'
    }

With Components

    Vue.component('my-component', {
      template: '<p class="foo bar">Hi</p>'
    })

    <my-component class="baz boo"></my-component>
    <my-component v-bind:class="{ active: isActive }"></my-component>

[Back to top](#Content)

### Binding Inline Styles

    <div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>
    data: {
      activeColor: 'red',
      fontSize: 30
    }

Computed

    <div v-bind:style="styleObject"></div>
    data: {
      styleObject: {
        color: 'red',
        fontSize: '13px'

      }
    }

Array Syntax

    <div v-bind:style="[baseStyles, overridingStyles]"></div>

    <div v-bind:style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div>

[Back to top](#Content)

## Conditional Rendering

### v-if (Render the block iff)

    <h1 v-if="awesome">Vue is awesome!</h1>
    <h1 v-else>Oh no 😢</h1>

Conditional Groups with v-if on <template>

    <template v-if="ok">
      <h1>Title</h1>
      <p>Paragraph 1</p>
      <p>Paragraph 2</p>
    </template>

### v-else-if

    <div v-if="type === 'A'"> A </div>
    <div v-else-if="type === 'B'"> B </div>
    <div v-else-if="type === 'C'"> C </div>
    <div v-else> Not A/B/C </div>

### Controlling Reusable Elements with key 

Add a ke so that the template is completely re-rendered rather than cached

    <template v-if="loginType === 'username'">
      <label>Username</label>
      <input placeholder="Enter your username" key="username-input">
    </template>
    <template v-else>
      <label>Email</label>
      <input placeholder="Enter your email address" key="email-input">
    </template>

The inputs have no keys so the placeholder will always be "Enter your username"

    <template v-if="loginType === 'username'">
      <label>Username</label>
      <input placeholder="Enter your username">
    </template>
    <template v-else>
      <label>Email</label>
      <input placeholder="Enter your email address">
    </template>

### v-show element will always be rendered but will be toggled

* v-if only renders if condition is true and re-renders if condition changes
* v-if also destroys and re-initializes all event listeners on re-render
* v-is is lazy

v-for has higher precedence over v-if

[Back to top](#Content)

## List Rendering

    <ul id="example-1">
      <li v-for="item in items" :key="item.message">
        {{ item.message }}
      </li>
    </ul>
    var example1 = new Vue({
      el: '#example-1', 
      data: {
        items: [
          { message: 'Foo' }, 
          { message: 'Bar' }
        ]

      }
    })

    <li v-for="(item, index) in items">
    <div v-for="item of items"></div>
    <div v-for="item in items" v-bind:key="item.id">  // <-- key
      <!-- content -->
    </div>

### v-for with an Object

    <ul id="v-for-object" class="demo">
      <li v-for="value in object">
        {{ value }}
      </li>
    </ul>
    new Vue({
      el: '#v-for-object', 
      data: {
        object: {
          title: 'How to do lists in Vue', 
          author: 'Jane Doe', 
          publishedAt: '2016-04-10'
        }

      }
    })

    <div v-for="(value, name) in object">
      {{ name }}: {{ value }}
    </div>

    <div v-for="(value, name, index) in object">
      {{ index }}. {{ name }}: {{ value }}
    </div>

### Displaying Filtered/Sorted Results (Using computed)

    <li v-for="n in evenNumbers">{{ n }}</li>

    data: {
      numbers: [ 1, 2, 3, 4, 5 ]
    }, 
    computed: {
      evenNumbers: function () {
        return this.numbers.filter(function (number) {
          return number % 2 === 0
        })

      }
    }

In situations where computed properties are not feasible (e.g. inside nested v-for loops), you can use a method:

    <ul v-for="set in sets">
      <li v-for="n in even(set)">{{ n }}</li>
    </ul>
    data: {
      sets: [[ 1, 2, 3, 4, 5 ], [6, 7, 8, 9, 10]]
    },
    methods: {
      even: function (numbers) {
        return numbers.filter(function (number) {
          return number % 2 === 0
        })

      }
    }

[Back to top](#Content)

## Event Handling

    <div id="example-1">
      <button @:click="counter += 1">Add 1</button>
      <p>The button above has been clicked {{ counter }} times.</p>
    </div>
    var example1 = new Vue({
      el: '#example-1', 
      data: {
        counter: 0

      }
    })

    <div id="example-2">
      <button v-on:click="greet">Greet</button>
    </div>

    var example2 = new Vue({
      el: '#example-2', 
      methods: {
        greet: function (event) {
        }

      }
    })

    <div id="example-3">
      <button v-on:click="say('hi')">Say hi</button>
      <button v-on:click="say('what')">Say what</button>
    </div>
    new Vue({
      el: '#example-3', 
      methods: {
        say: function (message) {
          alert(message)
        }

      }
    })

$event variable

    <button v-on:click="warn('Form cannot be submitted yet.', $event)">
      Submit
    </button>

### Event Modifiers

.stop
.prevent
.capture
.self
.once
.passive

    <!-- the click event's propagation will be stopped -->
    <a v-on:click.stop="doThis"></a>

    <!-- the submit event will no longer reload the page -->
    <form v-on:submit.prevent="onSubmit"></form>

    <!-- modifiers can be chained -->
    <a v-on:click.stop.prevent="doThat"></a>

    <!-- just the modifier -->
    <form v-on:submit.prevent></form>

    <!-- use capture mode when adding the event listener -->
    <!-- i.e. an event targeting an inner element is handled here before being handled by that element -->
    <div v-on:click.capture="doThis">...</div>

    <!-- only trigger handler if event.target is the element itself -->
    <!-- i.e. not from a child element -->
    <div v-on:click.self="doThat">...</div>

    <!-- the scroll event's default behavior (scrolling) will happen -->
    <!-- immediately, instead of waiting for `onScroll` to complete  -->
    <!-- in case it contains `event.preventDefault()` -->
    <!-- The .passive modifier is especially useful for improving performance on mobile devices. -->
    <div v-on:scroll.passive="onScroll">...</div>

### Key Modifiers

    <!-- only call `vm.submit()` when the `key` is `Enter` -->
    <input v-on:keyup.enter="submit">

    <!-- KeyboardEvent.key -->
    <input v-on:keyup.page-down="onPageDown">

Key Codes

    .enter
    .tab
    .delete (captures both “Delete” and “Backspace” keys)
    .esc
    .space
    .up
    .down
    .left
    .right

    .ctrl
    .alt
    .shift
    .meta

    <!-- Alt + C -->
    <input v-on:keyup.alt.67="clear">

    <!-- Ctrl + Click -->
    <div v-on:click.ctrl="doSomething">Do something</div>

.exact

    <!-- this will fire even if Alt or Shift is also pressed -->
    <button v-on:click.ctrl="onClick">A</button>

    <!-- this will only fire when Ctrl and no other keys are pressed -->
    <button v-on:click.ctrl.exact="onCtrlClick">A</button>

    <!-- this will only fire when no system modifiers are pressed -->
    <button v-on:click.exact="onClick">A</button>

### Mouse Button Modifiers

    .left
    .right
    .middle

[Back to top](#Content)

## Form Input Bindings

v-model to create 2-way data binding

Text

    <input v-model="message" placeholder="edit me">
    <p>Message is: {{ message }}</p>

Multiline text (Textarea)

    <span>Multiline message is:</span>
    <p style="white-space: pre-line;">{{ message }}</p>
    <br>
    <textarea v-model="message" placeholder="add multiple lines"></tex

Checkbox

    Single checkbox, boolean value:
    <input type="checkbox" id="checkbox" v-model="checked">
    <label for="checkbox">{{ checked }}</label>

Multiple checkboxes, bound to the same Array:

    <div id='example-3'>
      <input type="checkbox" id="jack" value="Jack" v-model="checkedNames">
      <label for="jack">Jack</label>
      <input type="checkbox" id="john" value="John" v-model="checkedNames">
      <label for="john">John</label>
      <input type="checkbox" id="mike" value="Mike" v-model="checkedNames">
      <label for="mike">Mike</label>
      <br>
      <span>Checked names: {{ checkedNames }}</span>
    </div>

    new Vue({
      el: '#example-3', 
      data: {
        checkedNames: []

      }
    })

### Modifiers

    .lazy To react only onchange rather than keyup
    <input v-model.lazy="msg">

    If you want user input to be automatically typecast as a Number, you can add the number modifier to your v-model managed inputs:

    <input v-model.number="age" type="number">

[Back to top](#Content)

## Components Basics

Reusable Vue instances with a name.
they accept the same options as new Vue, such as data, computed, watch, methods, and lifecycle hooks. 
The only exceptions are a few root-specific options like el.

    // Define a new component called button-counter
    Vue.component('button-counter', {

      /* component's data must be function 
      so as to each receive their copies
      because simple objects are passed by reference*/
      data: function () {
        return {
          count: 0
        }
      },
      template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
    })

    <div id="components-demo">
      <button-counter></button-counter>
      <button-counter></button-counter>
      <button-counter></button-counter>
    </div>

    new Vue({ 
      el: '#components-demo' 
    })

// components must be registered to the vue instance

Passing Data to Child Components with Props

    Vue.component('blog-post', {
      <!-- props are accessed like data -->
      props: ['title'],
      template: '<h3>{{ title }}</h3>'
    })

    <blog-post title="My journey with Vue"></blog-post>

    <blog-post title="Blogging with Vue"></blog-post>
    <blog-post title="Why Vue is so fun"></blog-post>

### Listening to Child Components Events

    new Vue({
      el: `<div id="blog-posts-events-demo">
        <div :style="{ fontSize: postFontSize + 'em' }">
          <blog-post
            v-for="post in posts"
            v-bind:key="post.id"
            v-bind:post="post"
            v-on:enlarge-text="postFontSize += 0.1"  <-- await messages from 
          ></blog-post>
        </div>
      </div>`, 
      data: {
        posts: [/* ... */], 
        postFontSize: 1

      }
    })

    Vue.component('blog-post', {
      props: ['post'],
      template: `
        <div class="blog-post">
          <h3>{{ post.title }}</h3>
          <button v-on:click="$emit('enlarge-text')">  <-- communicato to parent
            Enlarge text
          </button>
          <div v-html="post.content"></div>
        </div>
      `
    })

Emitting a Value With an Event

    # component
    <button v-on:click="$emit('enlarge-text', 0.1)">
      Enlarge text
    </button>

    # parent
    <blog-post
      ...
      v-on:enlarge-text="postFontSize += $event"
    ></blog-post>

Using v-model on Components

    <custom-input v-model="searchText"></custom-input>

### Dynamic Components (is)

    <div id="dynamic-component-demo" class="demo">
      <button
        v-for="tab in tabs"
        v-bind:key="tab"
        v-bind:class="['tab-button', { active: currentTab === tab }]"
        v-on:click="currentTab = tab"
      >
        {{ tab }}
      </button>

      <component v-bind:is="currentTabComponent" class="tab"></component>
    </div>

    <script>

      Vue.component("tab-home", {
        template: "<div>Home component</div>"
      }); 
      Vue.component("tab-posts", {
        template: "<div>Posts component</div>"
      }); 
      Vue.component("tab-archive", {
        template: "<div>Archive component</div>"
      }); 

      new Vue({
        el: "#dynamic-component-demo",
        data: {
          currentTab: "Home",
          tabs: ["Home", "Posts", "Archive"]
        },
        computed: {
          currentTabComponent: function() {
            return "tab-" + this.currentTab.toLowerCase();
          }
        }
      });

      // currentTabComponent is the name of a registered component

### DOM Template Parsing Caveats

    <table>
      <tr is="blog-post-row"></tr>
    </table>

[Back to top](#Content)

## Components In-Depth

### Component Registration

Global registration (Components have the scope of the Vue Instance)

    Vue.component('component-a', { /* ... */ })
    Vue.component('component-b', { /* ... */ })
    Vue.component('component-c', { /* ... */ })

Local Registration (Avoid global registration because it increases bundle size with redundant components)

    var ComponentA = { /* ... */ }
    var ComponentB = {
      // because locally registered components are not also available in subcomponents
      components: {
        'component-a': ComponentA
      },
    }
    var ComponentC = { /* ... */ }

    new Vue({
      el: '#app', 
      components: {
        'component-a': ComponentA, 
        ComponentC, // is also the name of the component

      }
    })

#### Local Registration

Method A

    import ComponentA from './ComponentA'
    import ComponentC from './ComponentC'

    export default {

      components: {
        ComponentA,
        ComponentC
      },
    }

Local Registration in a Module System

    import Vue from 'vue'
    import upperFirst from 'lodash/upperFirst'
    import camelCase from 'lodash/camelCase'

    const requireComponent = require.context(
      // The relative path of the components folder
      './components',
      // Whether or not to look in subfolders
      false,
      // The regular expression used to match base component filenames
      /Base[A-Z]\w+\.(vue|js)$/
    )

    requireComponent.keys().forEach(fileName => {
      // Get component config
      const componentConfig = requireComponent(fileName)

      // Get PascalCase name of component
      const componentName = upperFirst(
        camelCase(
          // Gets the file name regardless of folder depth
          fileName
            .split('/')
            .pop()
            .replace(/\.\w+$/, '')
        )
      )

      // Register component globally
      Vue.component(
        componentName,
        // Look for the component options on `.default` , which will
        // exist if the component was exported with `export default` ,
        // otherwise fall back to module's root.
        componentConfig.default || componentConfig
      )
    })

### Props

    Vue.component('blog-post', {
      // camelCase in JavaScript
      props: ['postTitle'],
      template: '<h3>{{ postTitle }}</h3>'
    })
    <!-- kebab-case in HTML -->
    <blog-post post-title="hello!"></blog-post>

Prop Types

    <!-- props with no types -->
    props: ['title', 'likes', 'isPublished', 'commentIds', 'author']

    props: {
      title: String,
      likes: Number,
      isPublished: Boolean,
      commentIds: Array,
      author: Object,
      callback: Function,
      contactsPromise: Promise // or any other constructor
    }

Passing Static or Dynamic Props

    <blog-post title="My journey with Vue"></blog-post>

    You’ve also seen props assigned dynamically with v-bind, such as in:

    <!-- Dynamically assign the value of a variable -->
    <blog-post v-bind:title="post.title"></blog-post>

    <!-- Dynamically assign the value of a complex expression -->
    <blog-post
      v-bind:title="post.title + ' by ' + post.author.name"
    ></blog-post>

Prop Validation

    Vue.component('my-component', {
      props: {
        // Basic type check ( `null` and `undefined` values will pass any type validation)
        propA: Number, 
        // Multiple possible types
        propB: [String, Number], 
        // Required string
        propC: {
          type: String, 
          required: true
        }, 
        // Number with a default value
        propD: {
          type: Number, 
          default: 100
        }, 
        // Object with a default value
        propE: {
          type: Object, 
          // Object or array defaults must be returned from
          // a factory function
          default: function () {
            return { message: 'hello' }
          }
        }, 
        // Custom validator function
        propF: {
          validator: function (value) {
            // The value must match one of these strings
            return ['success', 'warning', 'danger'].indexOf(value) !== -1
          }
        }

      }
    })

Non-Prop Attributes 

These are attrs passed toi the component but not defined in the component's props.
They will be appended to the component's template

Replacing/Merging with Existing Attributes

    Vue.component('bootstrap-date-input', {
      template: '<input type="date" class="form-control">'
    })

    <bootstrap-date-input
      data-date-picker="activated"
      class="date-picker-theme-dark"  <-- this will overwrite class="form-control" in the component
    ></bootstrap-date-input>

[Disabling Attribute Inheritance(https://vuejs.org/v2/guide/components-props.html#Disabling-Attribute-Inheritance)
]

    Vue.component('base-input', {
      inheritAttrs: false,
      props: ['label', 'value'],
      template: `
        <label>
          {{ label }}
          <input
            v-bind="$attrs"
            v-bind:value="value"
            v-on:input="$emit('input', $event.target.value)"
          >
        </label>
      `
    })

    <base-input
      v-model="username"
      required
      placeholder="Enter your username"
    ></base-input>

### Custom Events

### Dynamic & Async Components

<keep-alive> to cache ctabbed components when using :is

    <keep-alive>
      <component v-bind:is="currentTabComponent"></component>
    </keep-alive>

### Async Components

    Vue.component('async-webpack-example', function (resolve) {
      // This special require syntax will instruct Webpack to
      // automatically split your built code into bundles which
      // are loaded over Ajax requests.
      require(['./my-async-component'], resolve)
    })

    Vue.component(
      'async-webpack-example',
      // The `import` function returns a Promise.
      () => import('./my-async-component')
    )

    new Vue({
      // ...
      components: {
        'my-component': () => import('./my-async-component')

      }
    })

    const AsyncComponent = () => ({
      // The component to load (should be a Promise)
      component: import('./MyComponent.vue'),
      // A component to use while the async component is loading
      loading: LoadingComponent,
      // A component to use if the load fails
      error: ErrorComponent,
      // Delay before showing the loading component. Default: 200ms.
      delay: 200,
      // The error component will be displayed if a timeout is
      // provided and exceeded. Default: Infinity.
      timeout: 3000
    })

[Back to top](#Content)

## [Handling Edge Cases](https://vuejs.org/v2/guide/components-edge-cases.html)

### Accessing the Root Instance ($root) Use [Vuex](#Vuex) instead

Each registered component can access the root istance 

    new Vue({
      data: {
        foo: 1
      }, 
      computed: {
        bar: function () { /* ... */ }
      }, 
      methods: {
        baz: function () { /* ... */ }

      }
    })

any registered component

    // Get root data
    this.$root.foo

    // Set root data
    this.$root.foo = 2

    // Access root computed properties
    this.$root.bar

    // Call root methods
    this.$root.baz()

### Accessing the Parent Component Instance ($parent)

    Vue.component("google-map", {
      data: function() {
        return {
          map: null
        }; 
      }, 
      mounted: function() {
        this.map = new google.maps. Map(this.$el, {
          center: { lat: 0, lng: 0 }, 
          zoom: 1
        }); 
      }, 
      methods: {
        getMap: function(found) {
          var vm = this; 
          function checkForMap() {
            if (vm.map) {
              found(vm.map); 
            } else {
              setTimeout(checkForMap, 50); 
            }
          }
          checkForMap(); 
        }
      }, 
      template: '<div class="map"><slot></slot></div>'

    });

    Vue.component("google-map-marker", {
      props: ["places"], 
      created: function() {
        var vm = this; 
        vm.$parent.getMap(function(map) {
          vm.places.forEach(function(place) {
            new google.maps. Marker({
              position: place.position, 
              map: map
            }); 
          }); 
        }); 
      }, 
      render(h) {
        return null; 
      }

    });

    new Vue({
      el: "#app", 
      data: {
        vueConfCities: [
          {
            name: "Wrocław", 
            position: {
              lat: 51.107885, 
              lng: 17.038538
            }
          }, 
          {
            name: "New Orleans", 
            position: {
              lat: 29.951066, 
              lng: -90.071532
            }
          }
        ]
      }

    });

### Accessing Child Component Instances & Child Elements (ref)

    Vue.component('app-component', {
      template: `
        ...
        <base-input ref="usernameInput"></base-input>
        ...
        `, 
      methods: {
        foo() {
          this.$refs.usernameInput
          this.$refs.usernameInput.focus()
          this.$refs.usernameInput.value
        }

      }
    })

    Vue.component('app-component', {
      template: `
        ...
          <ul> 
            <li v-for="i of items" $ref="items">
          </ul>
        ...`, 
      methods: {
        foo() {
          this.$refs.items.fofEach
        }

      }
    })

    // refs are created after render. avoid using them in templates, computed properties

### Dependency Injection

    Vue.component("google-map", {
      provide: function() {          // <-- inject the following objects to sub components
        return {
          getMap: this.getMap
        }; 
      }, 
      data: function() {
        return {
          map: null
        }; 
      }, 
      mounted: function() {
        this.map = new google.maps. Map(this.$el, {
          center: { lat: 0, lng: 0 }, 
          zoom: 1
        }); 
      }, 
      methods: {
        getMap: function(found) {
          var vm = this; 
          function checkForMap() {
            if (vm.map) {
              found(vm.map); 
            } else {
              setTimeout(checkForMap, 50); 
            }
          }
          checkForMap(); 
        }
      }, 
      template: '
        <div class="map">
          <slot></slot>            // <-- These components  are provided with the 
        </div>'

    });

    Vue.component("google-map-marker", {
      inject: ["getMap"], // <-- retrive the provided props (from parent)
      props: ["places"], 
      created: function() {
        var vm = this; 
        vm.getMap(function(map) {
          vm.places.forEach(function(place) {
            new google.maps. Marker({
              position: place.position, 
              map: map
            }); 
          }); 
        }); 
      }, 
      render(h) {
        return null; 
      }

    });

    new Vue({
      el: "#app", 
      data: {
        vueConfCities: [
          {
            name: "Wrocław", 
            position: {
              lat: 51.107885, 
              lng: 17.038538
            }
          }, 
          {
            name: "New Orleans", 
            position: {
              lat: 29.951066, 
              lng: -90.071532
            }
          }
        ]
      }

    });

### [Programmatic Event Listeners](https://vuejs.org/v2/api/#Instance-Methods-Events)

A problem

    // Attach the datepicker to an input once
    // it's mounted to the DOM.
    mounted: function () {
      // Pikaday is a 3rd-party datepicker library
      this.picker = new Pikaday({
        field: this.$refs.input,
        format: 'YYYY-MM-DD'
      })
    },

    // Right before the component is destroyed,
    // also destroy the datepicker.
    beforeDestroy: function () {
      this.picker.destroy()
    }

A solution (better code organisation)

    mounted: function () {
      const picker = new Pikaday({
        field: this.$refs.input,
        format: 'YYYY-MM-DD'
      })

      this.$once('hook:beforeDestroy', function () {
        picker.destroy()
      })
    }

Event better

    mounted: function () {
      this.attachDatepicker('startDateInput')
      this.attachDatepicker('endDateInput')
    },
    methods: {
      attachDatepicker: function (refName) {
        const picker = new Pikaday({
          field: this.$refs[refName],
          format: 'YYYY-MM-DD'
        })

        this.$once('hook:beforeDestroy', function () {
          picker.destroy()
        })

      }
    }

Complete code

    <div id="app">
      <input ref="dateInput" v-model="date" type="date" />
    </div>

    new Vue({
      el: "#app",
      data: {
        date: null
      },
      mounted: function() {
        var picker = new Pikaday({
          field: this.$refs.dateInput,
          format: "YYYY-MM-DD"

        });

        this.$once("hook:beforeDestroy", function() {
          picker.destroy(); 
        }); 
      }

    });

### [Circular References](https://vuejs.org/v2/guide/components-edge-cases.html#Circular-References)

Recursive Components

### Alternative Template Definition

inline-template

    <my-component inline-template>  <-- component begins with the first child
      <div>
        <p>These are compiled as the component's own template.</p>
        <p>Not parent's transclusion content.</p>
      </div>
    </my-component>

X-Templates

    <script type="text/x-template" id="hello-world-template">
      <p>Hello hello hello</p>

    </script>

    Vue.component('hello-world', {
      template: '#hello-world-template'
    })

### Controlling Updates

v-once (render once and cache)

    Vue.component('terms-of-service', {
      template: `
        <div v-once>
          <h1>Terms of Service</h1>
          ... a lot of static content ...
        </div>
      `
    })

[Back to top](#Content)

## Reusability and Composition

### Mixins

    var myMixin = {
      created: function () {
        this.hello()
      },
      methods: {
        hello: function () {
          console.log('hello from mixin!')
        }

      }
    }

    // define a component that uses this mixin
    var Component = Vue.extend({
      mixins: [myMixin],
      data: // component's data overwrite mixin's data
      hooks: // overlapping hooks will be duplicated and mixins's preceed component's
    })

    var component = new Component() // => "hello from mixin!"

Global Mixin

    // inject a handler for `myOption` custom option
    Vue.mixin({
      created: function () {
        var myOption = this.$options.myOption
        if (myOption) {
          console.log(myOption)
        }

      }
    })

    new Vue({
      myOption: 'hello!'
    })
    // => "hello!"

### Custom Directives

// Register a global custom directive called `v-focus` 

    Vue.directive('focus', {
      // When the bound element is inserted into the DOM...
      inserted: function (el) {
        // Focus the element
        el.focus()

      }
    })

// local registration

    vue.component('app-bla', {
      directives: {
        focus: {
          // directive definition
          inserted: function (el) {
            el.focus()
          }
        }

      }
    })

    <app-bla v-focus>

### [Hook Functions](https://vuejs.org/v2/guide/custom-directive.html#Hook-Functions)

### [Directive Hook Arguments](https://vuejs.org/v2/guide/custom-directive.html#Directive-Hook-Arguments)

### [Render Functions & JSX](https://vuejs.org/v2/guide/render-function.html)

Create components using document.createElement 

Motivation 

    <script type="text/x-template" id="anchored-heading-template">
      <h1 v-if="level === 1">
        <slot></slot>
      </h1>
      <h2 v-else-if="level === 2">
        <slot></slot>
      </h2>
      <h3 v-else-if="level === 3">
        <slot></slot>
      </h3>
      ...

    </script>

    Vue.component('anchored-heading', {
      template: '#anchored-heading-template', 
      props: {
        level: {
          type: Number, 
          required: true
        }

      }
    })

The solution using render function

    Vue.component('anchored-heading', {
      render: function (createElement) {
        return createElement(
          'h' + this.level, // tag name
          this.$slots.default // array of children
        )
      }, 
      props: {
        level: {
          type: Number, 
          required: true
        }

      }
    })

### Plugins

add global-level functionality to Vue.
Similar but better than plugins

    #plugin.js
    

    export default {

      // The install method will be called with the Vue constructor as         
      // the first argument, along with possible options
      install(Vue, options) {

        Vue.mixin({

          created() {

          },

          data: () => ({
          }),

          methods: {

          },

        });

        Vue.component('component', component: import('./MyComponent.vue') );

        Vue.wtf = function () {
          console.warn("this is wtf", this)
        }

        // Add Vue instance methods by attaching them to Vue.prototype.
        Vue.prototype.$someMethod = () => ??;
        Vue.prototype.$someOtherMethod = () => (props) => ??; // NB arrow functions have no `this` 

      }
    }

    import MyPlugin from './plugin.js'

    // calls `MyPlugin.install(Vue)` 
    Vue.use(MyPlugin)

    //with optional parameters
    Vue.use(MyPlugin, { someOption: true })

    new Vue({
      el: '#app',
      render: h => h(App)
    })

    Registered components now inherit from the Plugin methods

### Filters (Pipes, converters, computers)

local filters (overwrite global ones)

    Vue.component('app-my-component', {
      filters: {
        capitalize: function (value) {
          if (!value) return ''
          value = value.toString()
          return value.charAt(0).toUpperCase() + value.slice(1)
        }

      }
    })

global filter

    Vue.filter('capitalize', function (value) {
      if (!value) return ''
      value = value.toString()
      return value.charAt(0).toUpperCase() + value.slice(1)
    })

    new Vue({
      // ...
    })

    <!-- in mustaches -->
    {{ message | capitalize }}

    <!-- in v-bind -->
    <div v-bind:id="rawId | formatId"></div>

    <!-- Can be chained -->
    {{ message | filterA | filterB }}

    <!-- filterA takes 3 params  -->
    {{ message | filterA('arg1', arg2) }}

[Back to top](#Content)

## Tooling

[Back to top](#Content)

### Single File Component

[Back to top](#Content)

### Unit Testing

    vue add @vue/unit-jest

package.json

    {

      "scripts": {
        "test": "jest --watch --config=./jest.config.js --coverage",
        "test:unit": "vue-cli-service test:unit",
      }, 
    }

jest.config.js

    module.exports = {
      preset: '@vue/cli-plugin-unit-jest',
      verbose: true
    }

/tests/unit/formgroup.spec.js

    import { mount } from "@vue/test-utils";
    import FormGroup from "@/components/formGroup";

$bash

  npm test

[Back to top](#Content)

### Typescript Support

tsconfig.json

    {

      "compilerOptions": {
        // this aligns with Vue's browser support
        "target": "es5", 
        // this enables stricter inference for data properties on `this` 
        "strict": true, 
        // if using webpack 2+ or rollup, to leverage tree shaking:
        "module": "es2015", 
        "moduleResolution": "node"

      }
    }

    npm install --global @vue/cli
    vue create my-project-name

my-component

    const Component = Vue.extend({
      // type inference enabled
    })

    const Component = {
      // this will NOT have type inference,
      // because TypeScript can't tell this is options for a Vue component.
    }

Class-Style Vue Components

    If you prefer a class-based API when declaring components, you can use the officially maintained vue-class-component decorator:

    import Vue from 'vue'
    import Component from 'vue-class-component'

    // The @Component decorator indicates the class is a Vue component
    @Component({
      // All component options are allowed in here
      template: '<button @click="onClick">Click!</button>'
    })
    export default class MyComponent extends Vue {
      // Initial data can be declared as instance properties
      message: string = 'Hello!'

      // Component methods can be declared as instance methods
      onClick (): void {
        window.alert(this.message)

      }
    }

[Back to top](#Content)

### Deployment

[Back to top](#Content)

## [Slot](https://vuejs.org/v2/guide/components-slots.html)

### compilation scope and fallback content

app.modal.js

    Vue.component("app-modal", {
    template:`
      <div class="modal" :style="{display: open ? 'block' : 'none'}">
        <div class="modal-content">
          <span class="close" @click="$emit('modal-closed')">&times;</span>
          <div>
            <slot>
              This is the fallback content that will be displayed in case you used this component without content(children)
            </slot>
          </div>
        </div>
      </div>
    `,

    name: "app-modal",
    props: [ 'open' ],

  }); 

app.home.js

    Vue.component("app-home", {
    template:`
    <app-modal :open="showModal" @modal-closed="()=>this.showModal=false">
      <h6 class="text-center">Create/Edit a balance sheet</h6>
      textContent here
    </app-modal>
    `,

    name: "app-home",
    props: [ 'open' ],

  }); 

### Named slots

app.modal.js

    Vue.component("app-modal", {
    template:`
      <div class="modal" :style="{display: open ? 'block' : 'none'}">
        <div class="modal-content">
          <span class="close" @click="$emit('modal-closed')">&times;</span>
          <div class="modal-header">
            <slot name="header"> </slot>
          </div>
          <div class="modal-body">
            <slot name="body"></slot>
          </div>
          <div class="modal-footer">
            <slot name="footer"> </slot>
          </div>
        </div>
      </div>
    `,

    name: "app-modal",
    props: [ 'open' ],

  }); 

app.home.js

    Vue.component("app-home", {
    template:`
      <app-modal :open="showModal" @modal-closed="closeModal">
        <template v-slot:header >
          <h6 class="text-center">C reate/Edit a balance sheet</h6>
        </template>

        <template  v-slot:body>
          <form id="sheetForm" v-on:submit.prevent="submitProject">
          </form>
        </template>

        <template  v-slot:default>
          same as not having a v-slot:
        </template>

        <template v-slot:footer>
          <button form="sheetForm" type="reset" class="btn btn-outline-warning mx-1" @click="resetForm" >cancel</button>
          <button form="sheetForm" type="submit" class="btn btn-outline-primary">Save</button>
        </template>

      </app-modal>
    </div>
    </layout>
    `,

    name: "app-home",
    props: [ 'open' ],

  }); 

### Scoped slots (pass props to slot)

parent(HOC)

    <span>
      <slot name="content" v-bind:user="user">
        {{ user.lastName }} # fallback content !!
      </slot>
    </span>

child

    <current-user>
      <template v-slot:content="slotProps">
        {{ slotProps.user.firstName }} # actual content
      </template>
    </current-user>

[Back to top](#Content)

## Vuex

store.js

    import Vue from 'vue'
    import Vuex from 'vuex'

    Vue.use(Vuex)

    export default new Vuex.Store({
      state: {
        count: 0,
        user: null,
        settings: null,
      },

      mutations: {
        increment: state => state.count++,
        incrementBy: (state, increment) => state.user = state.count += increment,
        setUser: (state, user) => state.user = {...state.user, ...user},
        setSettings: (state, settings) => state.settings = {...state.settings, ...settings},
      },

      getters: {
        getUser: state => state.user,
        getSetting: state => (key) => state.settings[key],
        get: state => (key) => state[key],
      },

      // asynchronous mutations
      actions: {
        incrementAsync ({ commit }) {
          setTimeout(() => {
            commit('increment')
          }, 1000)
        },

      actionA ({ commit }) {
          return new Promise((resolve, reject) => {
            setTimeout(() => {
              commit('someMutation')
              resolve()
            }, 1000)
          })
        };

      actionB ({ dispatch, commit }) {
        return dispatch('actionA').then(() => {
          commit('someOtherMutation')
        })
        
      }

    });

main.js

  new Vue({

    store,

  }); 

any-component.js

    import { mapGetters, mapState, mapMutations; mapActions } from 'vuex'

    {

      methods: {
        SetUser(){
          this.$store.comit("setUser", value); // call an action
        }, 

        usingGetters() {
          console.log( this.$store.getters.getUser() )
          console.log( store.getters.getSetting("color") )
        },

         ...mapMutations([
          'setUser', // map `this.setUser()` to `this.$store.commit('setUser')` 
          'incrementBy' // map `this.incrementBy(amount)` to `this.$store.commit('incrementBy', amount)` 
        ]),

        ...mapMutations({
          add: 'increment' // map `this.add()` to `this.$store.commit('increment')` 
        }),

        ...mapActions([
          'increment', // map `this.increment()` to `this.$store.dispatch('increment')` 

          // `mapActions` also supports payloads:
          'incrementBy' // map `this.incrementBy(amount)` to `this.$store.dispatch('incrementBy', amount)` 
        ]),

        ...mapActions({
          add: 'increment' // map `this.add()` to `this.$store.dispatch('increment')` 
        })
        
      },

      computed: {
        ...mapState({
          count: state => state.count,
          countAlias: 'count',// extract and rename
          countPlusLocalState (state) {
            return state.count + this.localCount
          }
        })

        ...mapState([
          'count' // map this.count to store.state.count
        ]),

        ...mapGetters([
          'getUser', // this.getUser ==> this.$store.getters.getUser
          'getSettings',
          'get': 'renamedTo'
        ])

      }
    }

[Back to top](#Content)

## [Vue Router](https://router.vuejs.org/guide/#html)

    import Vue from 'vue'
    import VueRouter from 'vue-router'

    import App from './App.vue';
    import Home from './components/Home';
    import Detail from './components/Detail';

    Vue.use(VueRouter);

    const router = new VueRouter({
      mode: 'history', // backend must redierect to index.html for 404

      // base: __dirname,

      routes: [
        { path: '/balancesheet', component: Home },
        { path: '/sign-in', component: SignIn },
        { path: '*sign*in*', component: SignIn },
        { path: '/sign-up', component: SignUp },
        { path: '/*sign*up*', component: SignUp },
        { path: '/my-account', component: MyAccount },
        { path: '/*account*', component: MyAccount },
        { path: '/sheets/:id', component: Detail, alias: "/dtaill" },
        { path: '*', redirect:  "/balancesheet"},
      ]

    });

    #app.html
    <div id="app">
      <router-view> </router-view>
    </>

    new Vue({
      router,
      render: h => h(App),
    }).$mount('#app')

    // Detail.vue

    export default {

      computed: {
        username() {
          return this.$route.params.username     // ?username=bla
          return this.$route.params.id     // path/ObjectId
        }
      },
      methods: {

        getData(id) {
          fetch({ url: `sheets/${id}` })
          .then( result => result.json() )
          .then(result => {
            this.sheet = result;
          });
        },

        goBack() {
          window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/')
        }
      },

      // use this or watch $route to catch routing param changes
      beforeRouteUpdate (to, from, next) {
        this.getData(to.params.id);
        next()
      }

      watch: {
        // update the component when routing params change
        $route(to, from) {
          this.getData(to.params.id);
        }
      },

      mounted() {
        this.getData(this.$route.params.id); 

      }
    }

  

### Nested Routes

The problem

    /user/foo/profile                     
    /user/foo/posts

The solution

    <div id="app">
      <router-view></router-view>
    </div>

    const User = {
      template: `
        <div class="user">
          <h2>User {{ $route.params.id }}</h2>
          <router-view></router-view>
        </div>
      `
    }

    const router = new VueRouter({
      routes: [
        { path: '/user/:id', component: User,
          children: [
            { path: '', component: UserHome },
            {
              // UserProfile will be rendered inside User's <router-view>
              // when /user/:id/profile is matched
              path: 'profile',
              component: UserProfile
            },
            {
              // UserPosts will be rendered inside User's <router-view>
              // when /user/:id/posts is matched
              path: 'posts',
              component: UserPosts
            }
          ]
        }
      ]
    })

### Programmatic Navigation

    // literal string path
    this.$router.push('home')

    // object
    this.$router.push({ path: 'home' })

    // named route
    this.$router.push({ name: 'user', params: { userId: '123' } })

    // with query, resulting in /register?plan=private
    this.$router.push({ path: 'register', query: { plan: 'private' } })

Note: params are ignored if a path is provided, which is not the case for query, as shown in the example above. Instead, you need to provide the name of the route or manually specify the whole path with any parameter:

    const userId = '123'
    this.$router.push({ name: 'user', params: { userId } }) // -> /user/123
    this.$router.push({ path: `/user/${userId}` }) // -> /user/123
    // This will NOT work
    this.$router.push({ path: '/user', params: { userId } }) // -> /user

router.replace(location, onComplete?, onAbort?)

    // go forward by one record, the same as history.forward()
    router.go(1)

    // go back by one record, the same as history.back()
    router.go(-1)

    // go forward by 3 records
    router.go(3)

    // fails silently if there aren't that many records.
    router.go(-100)
    router.go(100)

[Back to top](#Content)

## Misc

### Using pug

    npm install -D pug pug-plain-loader

    webpack.config.js

    export default {

    module: {
      rules: {
          test: /\.pug$/, 
          loader: 'pug-plain-loader'
        }

      }
    }

    <template lang="pug">  
      div 
        | Hello world
    </template> 

### Using less

    npm install -D less less-loader

    // webpack.config.js -> module.rules
    
    {
      test: /\.less$/,
      use: [
        'vue-style-loader',
        'css-loader',
        'less-loader'
      ]
    }

## Publish Package

Here is how to publish a vue component to npm

    vue create formgroup

package.json

    {

      "scripts": {
        "build-bundle": "vue-cli-service build --target lib --name formgroup ./src/components/app-form-group.vue"

      }, 
      "name": "@formgroup/vue-components",
      "main": "./build/formGroup.common.js",
      "files": [
        "build/*",
        "src/*",
        "public/*",
        "*.json",
        "*.js"
      ],
    }

#bash

    npm whoami
    npm adduser # ??
    npm login
    npm run build-bundle
    npm publish --access public

    npm install --save [myLibName]

## 
