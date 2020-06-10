# Hello humans

## Content 
  - [Basics](#Basics)
  - [Navigate Between Pages](#Navigate-Between-Pages)
  - [Using Shared Components](#NUsing-Shared-Components)
  - [Create Dynamic Pages](#Create-‚Dynamic-Pages)
  - [Fetching Data for Pages](#Fetching-Data-for-Pages)
  - [Styling Components](#Styling-Components)
  - [API Routes](#API-Routes)
  - [Deployment](#Deployment)
  - [TypeScript](#TypeScript)
  - [Advanced Features](#Advanced-Features)
  - [Misc](#Misc)
  - [Back to top](#Content)

## Basic

    npm init next-app
    mkdir hello-next
    cd hello-next
    npm init -y
    npm install --save react react-dom next
    mkdir pages

package.json

    "scripts": {
      "dev": "next",
      "build": "next build",
      "start": "next start"
    }

pages/index.js

    export default () => (
      <div>
        <p>Hello Next.js</p>
      </div>
    );

[http://localhost:3000](http://localhost:3000)

[Back to top](#Content)

## Navigate Between Pages

pages/index.js

    import Link from 'next/link';

    export default () => (
      <div>
        <Link href="/about">
          <a>About Page</a>
        </Link>—
        <p>Hello Next.js</p>
      </div>
    );

pages/about.js

    export default () => (
      <div>
        <p>This is the about page</p>
      </div>
    );

[Back to top](#Content)

### Pre-rendering (Hydration)

next creates minimalistic html serverside, the browser then runs the necessary js scripts.

#### 1 Static Generation (Recommended) html generated at build time and reused for each request

##### Static generation (with no data)

pages/about.js

    export default () => ( <div>About</div> )

##### Static generation (requiring data)

###### A - getStaticProps: Component depends on external data

To fetch data on pre-render, you export an async function called getStaticProps from the same file. This function gets called at build time and lets you pass fetched data to the page's props on pre-render.

pages/blog.js

    import fetch from 'node-fetch'

    // This function gets called at build time
    export async function getStaticProps( context ) {
      const res = await fetch('https://.../posts')
      const posts = await res.json()

      // By returning { props: posts }, the Blog component
      // will receive `posts` as a prop at build time
      return {
        props: {
          posts,
        },
      }
    }

    export default ({ posts }) => (
      <ul>
        {posts.map(post => (
          <li>{post.title}</li>
        ))}
      </ul>
    )

###### B - getStaticPaths: Component page paths depend on external data

pages/posts/[id].js

    import fetch from 'node-fetch'

    export async function getStaticPaths() {
      const res = await fetch('https://.../posts')
      const posts = await res.json()

      const paths = posts.map(post => `/posts/${post.id}`)

      // We'll pre-render only these paths at build time.
      // { fallback: false } means other routes should 404.
      return { paths, fallback: false }
    }

    export async function getStaticProps( context) {

      const res = await fetch(`https://.../posts/${context.params.id}`)
      const post = await res.json()

      return { props: { post } }
    }

    export default ({ post }) => (<></>)

#### 2 Server-side Rendering (SSR or Dynamic Rendering): html re-rendered for each request


    import fetch from 'node-fetch'

    // This gets called on every request
    export async function getServerSideProps() {
      const res = await fetch(`https://.../data`)
      const data = await res.json()

      return { props: { data } }
    }

    export default ({ data }) => (<></>)


## Using Shared Components

### Naiive approach


next.Link transforms all the components in the /pages/* => pages

components/Header.js

    import Link from 'next/link';

    const linkStyle = { marginRight: 15 };

    export default => (
      <div>
        <Link href="/">
          <a style={linkStyle}>Home</a>
        </Link>
        <Link href="/about">
          <a style={linkStyle}>About</a>
        </Link>
      </div>
    );


pages/index.js

    import Header from '../components/Header';

    export default () => {
      return (
        <div>
          <Header />
          <p>Hello Next.js</p>
        </div>
      );
    }

### Rendering Child Components (as HOC )

components/Container.js

    import Header from './Header';

    const layoutStyle = { margin: 20, ... };

    export default () => {
      return () => (
        <div style={layoutStyle}>
          <Header />
          <Page />
        </div>
      );
    };


pages/index.js

    import Container from '../components/Container';

    const Page = () => <p>Hello Next.js</p>;

    export default Container(Page);

pages/about.js

    import Container from '../components/Container';

    const Page = () => <p>Hello Next.js</p>;

    export default Container(Page);

### Rendering Child Components (as props )

components/Container.js

    import Header from './Header';

    const layoutStyle = { margin: 20, ... };

    export default () => (
      <div style={layoutStyle}>
        <Header />
        {props.content}
      </div>
    );


pages/index.js

    import Layout from '../components/Container.js';

    const indexPageContent = <p>Hello Next.js</p>;

    export default () => {
      return <Layout content={indexPageContent} />;
    }

[Back to top](#Content)

## Create Dynamic Pages

pages/index.js.

    import Layout from '../components/MyLayout';
    import Link from 'next/link';

    const PostLink = props => (
      <li>
        <Link href={`/post?title=${props.title}`}>
          <a>{props.title}</a>
        </Link>
      </li>
    );

    export default () => {
      return (
        <Layout>
          <h1>My Blog</h1>
          <ul>
            <PostLink title="Hello Next.js" />
            <PostLink title="Learn Next.js is awesome" />
            <PostLink title="Deploy apps with Zeit" />
          </ul>
        </Layout>
      );
    }

pages/blog.js

  import { useRouter } from 'next/router';
  import Layout from '../components/Layout';

    export default () => {
    
      const router = useRouter();

      return (
        <Layout>
          <h1>{router.query.title}</h1>
          <p>This is the blog post content.</p>
        </Layout>
      );
    };


### [Dynamic Routing](https://nextjs.org/docs/routing/dynamic-routes) (<Link href="/p/[id]" as={`/p/${props.id}`}>)

pages/index.js

    import Layout from '../components/Layout';
    import Link from 'next/link';

    const PostLink = props => (
      <li>
        <Link href="/p/[id]" as={`/p/${props.id}`}>
          <a>{props.id}</a>
        </Link>
      </li>
    );

    export default () => {
      return (
        <Layout>
          <h1>My Blog</h1>
          <ul>
            <PostLink id="hello-nextjs" />
            <PostLink id="learn-nextjs" />
            <PostLink id="deploy-nextjs" />
          </ul>
        </Layout>
      );
    }

pages/p[id].js // dynamic page. will match p/*

    import { useRouter } from 'next/router';
    import Layout from '../../components/Layout';

    export default () => {
      const router = useRouter();

      return (
        <Layout>
          <h1>{router.query.id}</h1>
          <p>This is the blog post content.</p>
        </Layout>
      );
    }

/pages/p/[id].js is supported but /pages/p/post-[id].js is not currently.

[Back to top](#Content)

## Fetching Data for Pages (Component.getInitialProps)

    npm install --save isomorphic-unfetch

pages/index.js 

    import Layout from '../components/Layout';
    import Link from 'next/link';
    import fetch from 'isomorphic-unfetch';

    const Index = props => (
      <Layout>
        <h1>Batman TV Shows</h1>
        <ul>
          {props.shows.map(show => (
            <li key={show.id}>
              <Link href="/p/[id]" as={`/p/${show.id}`}>
                <a>{show.name}</a>
              </Link>
            </li>
          ))}
        </ul>
      </Layout>
    );

    Index.getInitialProps = async function() {
      const res = await fetch('https://api.tvmaze.com/search/shows?q=batman');
      const data = await res.json();

      // logs on server side cuz ...
      // console.log(`Show data fetched. Count: ${data.length}`);

      return {
        shows: data.map(entry => entry.show)
      };
    };

    export default Index;

pages/p/[id].js

    import Layout from '../../components/Layout';
    import fetch from 'isomorphic-unfetch';

    const Post = props => (
      <Layout>
        <h1>{props.show.name}</h1>
        <p>{props.show.summary.replace(/<[/]?[pb]>/g, '')}</p>
        {props.show.image ? <img src={props.show.image.medium} /> : null}
      </Layout>
    );

    Post.getInitialProps = async function(context) {

      const { id } = context.query;
      const res = await fetch(`https://api.tvmaze.com/shows/${id}`);
      const show = await res.json();

      // client side log because wi clicked on the browser
      // console.log(`Fetched show: ${show.name}`);

      return { show };
    };

    export default Post;


[Back to top](#Content)

## Styling Components

    export default () => {
      return (
        <Layout>
          <!-- content-->

          <style jsx>{`
            h1,
            a {
              font-family: 'Arial';
            }
            <!-- css here must be in template string ``-->
          `}
          </style>
        </Layout>
      );
    }

### markdown style ???

    npm install --save react-markdown

    import Markdown from 'react-markdown';

    return (
        <Layout>
          <h1>{router.query.id}</h1>
          <div className="markdown">
            <Markdown
              source={`
    This is our blog post.
    Yes. We can have a [link](/link).
    And we can have a title as well.

    ### This is a title

    And here's the content.
          `}
            />
          </div>
          <style jsx global>{`
            .markdown { }
            .markdown a { }
          `}</style>
        </Layout>

[Back to top](#Content)

## API Routes (pages/api/*)

    // is a React Hook for remote data fetching
    npm install swr 

pages/api/randomQuote.js

    import quotes from '../../quotes.json';

    export default (req, res) => {
      const quote = quotes[Math.floor(Math.random() * quotes.length)];
      res.status(200).json(quote);
    };
  
pages/index.js

    import fetch from 'isomorphic-unfetch';
    import useSWR from 'swr';

    const fetcher = (url) => fetch(url).then(r => r.json());

    export default () => {

      const { data, error } = useSWR('/api/randomQuote', fetcher);
      const author = data?.author;
      let quote = data?.quote;

      if (!data) quote = 'Loading...';
      if (error) quote = 'Failed to fetch the quote.';

      return (
        <main className="center">
          <div className="quote">{quote}</div>
          {author && <span className="author">- {author}</span>}
        </main>
      );
    }

[Back to top](#Content)

## Deployment

    npm i -g now

shell 

    now

[Back to top](#Content)

## TypeScript

    npm install --save-dev typescript @types/react @types/node

rename pages to .tsx

[Back to top](#Content)


## Advanced Features

[Back to top](#Content)

##
[Back to top](#Content)

##
[Back to top](#Content)

##