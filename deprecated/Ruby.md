[rubylearning.com](http://rubylearning.com/satishtalim/writing_our_own_class_in_ruby.html)

[Ruby Tutorial: Tutorials point](https://www.tutorialspoint.com/ruby/ruby_classes.htm)

[Install](https://rubyinstaller.org/downloads/)

execute

    ruby filename.rb

<< Here Document
  
	print <<EOF
       This is the first way of creating
       here document ie. multiple line string.
    EOF

BEGIN | END

			#!/usr/bin/ruby
            puts "This is main Ruby Program"
            BEGIN {
               puts "Initializing Ruby Program"
            }
            --------------------------------------------
            Initializing Ruby Program
            This is main Ruby Program
######
            puts "This is main Ruby Program"
            END {
               puts "Terminating Ruby Program"
            }
            BEGIN {
               # this is a comment
               puts "Initializing Ruby Program"
            }
            --------------------------------------------
            Initializing Ruby Program
            This is main Ruby Program
            Terminating Ruby Program
#### Class
