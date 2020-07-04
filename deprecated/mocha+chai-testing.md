# Mocha testing

[Chai Assertion Library](http://www.chaijs.com/api/bdd/)

Install

    npm install --save mocha chai

package.json

    "scripts": {
        "test" : "mocha tests.js"
    },

main.js

    function a(a,b){
        return a + b;
    }
    function b(a,b){
        return a + b;
    }

    module.exports = {
        a: a,
        b: b
    };

tests.js

    const assert = require('assert'),
          expect = require('chai').expect;
          main   = require('./path/main.js');

    describe('testing function a', function() {

        it('tast case 1: should return 3 when parameters are 1,2', function() {
            assert.equal( main.a(1,2), 3);
        });

        it('tast case 1: should not return 4 when parameters are 1,2', function() {
            assert.equal( main.a(1,2), 4);
        });
    });
    
    
    describe('Testing function b ', function() {

        it('tast case 1: should return 3 when parameters are 1,2', function() {
            assert.equal( main.b(1,2), 3);
        });

        it('tast case 2: should not return 4 when parameters are 1,2', function() {
            assert.equal( main.b(1,2), 4);
        });
    });

compare objects

    describe('_updateTime', function() {
  
      it('should return 09:30 when parameters 09:00, duration: 30 Mins', function() {
        let input    = { Hour:9, Minutes: 00 }, 
            duration = 30,
            output   = { Hour:9, Minutes: 30 };
    
        expect( main._updateTime(input,duration) ).to.deep.equal(output);
      });
  
  });