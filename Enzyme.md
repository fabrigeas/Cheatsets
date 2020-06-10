# Hello humans

## Content 
  - [Basic Usage](#Basics)
  - [Back to top](#Content)

## Basic Usage

[jest-enzyme](https://github.com/FormidableLabs/enzyme-matchers/tree/master/packages/jest-enzyme)

    npm i --save-dev enzyme enzyme-adapter-react-16 chai

src/setupTests.js

    import { configure } from 'enzyme';
    import Adapter from 'enzyme-adapter-react-16';
    configure({ adapter: new Adapter() });

    