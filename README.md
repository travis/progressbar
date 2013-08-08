# progressbar

Easy progress bars in Clojure.

## Installation

progressbar artifacts are published on [Clojars](https://clojars.org/progressbar):

Add progressbar to your `project.clj`:

```clj
   [progressbar "0.0.1"]
```

## Usage

Progressbar transparently wraps any `seq`able to print feedback to
standard out as items in the seq are processed.

```clojure
user> (require '[progressbar.core :refer [progressbar]])
user> (doall (map identity (progressbar (range 10) :print-every 2)))
[====) # this is animated from [) to [====) using \r
(0 1 2 3 4 5 6 7 8 9)
```

The default progressbar looks like this:

```
[===)
```

and will grow infinitely large. By default, it will print an `=` for
every 10 items in the seq that are processed.

### Options

<dl>
<dt>:print-every</dt>
<dd>The number of items each `=` represents</dd>
<dt>:progress-seq</dt>
<dd>A custom progress seq. An element from this seq will be printed every time an element from the wrapped seq is processed, unless the element from the progress seq is `nil`.</dd>
<dt>:count</dt>
<dd>If given, the progress bar will be bounded, indicating how close to completion we are</dd>
<dt>:width</dt>
<dd>If a count is given, this controls how wide the progress bar will be.</dd>
</dl>

### Custom progress seqs

Clients can pass a seq to `progressbar` as `:progress-seq`. The given
seq will be used as progress output:

```clojure
user> (doall (map identity (progressbar (range 10)
                                        :progress-seq [nil "two\n" nil "four\n" nil "six\n" nil "eight\n" nil "ten\n"])))
two
four
six
eight
ten
(0 1 2 3 4 5 6 7 8 9)
```

```clojure
user> (doall (map identity (progressbar (range 10 20)
                                        :progress-seq (iterate inc 0))))
0
1
2
3
4
5
6
7
8
9
(10 11 12 13 14 15 16 17 18 19)
```

**Warning: if the progress seq is shorter than the seq you are processing, some elements from the seq you are processing may not be processed!**

## TODO

- build in bounded progressbar
- add a `:count` option that will trigger the bounded progressbar
- use a bounded progressbar by default if the input seq is actually a constant time
  `count`able Collection

## License

Copyright © 2013 Travis Vachon

Distributed under the Eclipse Public License, the same as Clojure.
