# Grunt

[Getting started](https://gruntjs.com/getting-started)
[API](https://gruntjs.com/api/grunt)
[grunt plug ins](https://gruntjs.com/plugins)

## [Automate: publish ReactComponent to npm](https://github.com/fabrigeas/react-formg-group/blob/master/Gruntfile.js)

```js
module.exports = function (grunt) {
  // https://www.npmjs.com/package/grunt-shell
  const pkg = grunt.file.readJSON("package.json");

  /** Generate a version higher than the current version */
  const generateVersion = () => {
    const [a, b, c] = pkg.version.split(".");
    const major = Number(a);
    const minor = Number(b);
    const patch = Number(c);

    return patch + 1 < 10
      ? `${major}.${minor}.${patch + 1}`
      : minor + 1 < 10
      ? `${major}.${minor + 1}.${0}`
      : `${major + 1}.${0}.${0}`;
  };

  grunt.loadNpmTasks("grunt-shell");

  grunt.initConfig({
    pkg,
    shell: {
      options: {
        stderr: false,
      },
      build: "rollup -c",
      copyDTFile: "cp build/FormGroup/FormGroup.d.ts build/index.d.ts",
      copyMapFile: "cp build/FormGroup/FormGroup.d.ts.map build/index.d.ts.map",
      watchBuild: "rollup -c -w",
      view: "npm view @fabrigeas/react-form-group",
      cleanBuild: "rimraf build",
      cleanDT: "rimraf build/FormGroup",
      commit: "git add --all && git commit -am 'publishing'",
      pushMaster: "git push origin master",
      pushTags: "git push --tags",
      version: "npm view",
      unpublish: `npm unpublish ${pkg.name} -f`,
      publish: "npm publish --access=public",
      copy: {
        command: (src, dest) => `cp ${src} ${dest}`,
      },
      upgradeVersion: `npm version ${generateVersion()}`,
    },
  });

  grunt.registerTask("publish", "Publish the library to npm.", function () {
    grunt.task.run([
      "shell:unpublish",
      "shell:commit",
      "shell:cleanBuild",
      "shell:build",
      "shell:copyDTFile",
      "shell:copyMapFile",
      "shell:cleanDT",
      "shell:upgradeVersion",
      "shell:pushMaster",
      "shell:pushTags",
      "shell:publish",
    ]);
  });

  grunt.registerTask("default", ["shell:version"]);
};
```

## Dive in

```bash
npm install -g grunt-cli
npm install grunt --save-dev

# install plugins that wll be used by grung
npm install --save-dev grunt-contrib-cssmin
npm install --save-dev grunt-contrib-concat
npm install --save-dev grunt-contrib-uglify
npm install --save-dev grunt-contrib-watch
npm install --save-dev grunt-contrib-sass
npm install --save-dev gruntify-eslint
```

```js
// Gruntfile.js

module.exports = function(grunt) {

  grunt.initConfig({

    pkg: grunt.file.readJSON("package.json"),

    //minify css. (optimizes the css files)
    cssmin: {
      build: {
        src: "styles/main.css",
        dest: "styles/main.min.css"
      }
    },

    // this concatanates (merges) all js files into a single file
    concat: {
      options: {
          separator: "\n/*next file*/\n\n"  //this will be put between concatanated files
        },
        dist: {
          src: ["scripts/hello.js", "scripts/main.js", "other-locations/*.js"],
          dest: "scripts/concatanated.js"
      }
    },

    // js minification
    uglify: {
      build: {
        files: {
          "scripts/built.min.js": ["scripts/built.js", "other-locations/*.js"]
        }
      }
    },

    sass: {
      dev: {    // indicates that it will be used only during development
        files: {
          "styles/main.css": ["styles/main.scss", "other-locations/*.scss", "**/*.scss"]
        }
      }
    },

    eslint: {
      nodeFiles: {
        src: ["back-end/routes/*.js", "back-end/app", "front-end/public/javascripts/"],
        options: {
          configFile: "conf/eslint-node.json"
        }
      },

    //runs a unique set of tasks when the file has been saved
    watch: {
      sass: {
        files: "**/*.scss", // ** any directory; * any file
        tasks: ["css"]
      },

      coffee: {
        files: "scripts/*.coffee",
        tasks: ["coffee"]
      },

      concat: {
        files: ["scripts/hello.js","scripts/main.js"],
        tasks: ["concat"]
      },

      uglify: {
        files: "scripts/built.js",
        tasks: ["uglify"]
      },

      eslint: {
        files: ["back-end/routes/*.js", "back-end/app", "front-end/public/javascripts/"],
        tasks: ["eslint"]
      }
    },

    //some custom values
    someValues : {
      Name :"Fabrice Feugang Kemegni",
      Age:"28",
      Weight:"83"
    }

  });

  // Load the plugins
  grunt.loadNpmTasks("grunt-contrib-cssmin");
  grunt.loadNpmTasks("grunt-contrib-concat");
  grunt.loadNpmTasks("grunt-contrib-uglify");
  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-contrib-sass");
  grunt.loadNpmTasks("gruntify-eslint");

  // Custom tasks

  grunt.registerTask("myTask1", "task description...", function() {
    grunt.log.writeln("Currently running task 1");

  });

  grunt.registerTask("myTask3", "task description...", function(fname, lname) {
    if (arguments.length === 0) {
      grunt.log.writeln(this.name + ", no args");
    } else {
      grunt.log.writeln(this.name + ", " + fname + " " + lname);
    }

  });

  // Default tasks.

  // grunt.registerTask("default", ["jshint","cssmin","uglify"...]);
  grunt.registerTask("default", ["myTask1","cssmin","myTask3:Fabrice:Feugang"]);

  //log
  grunt.log.write("Hello Grunt!\n");
};

```

```sh
// Execution: In cmd, run

grunt # runs th default tasks
grunt myTask
grunt mytask3:Fabrice:Feugang
grunt watch
grunt watch:sass
grunt sass
grunt eslint
```
