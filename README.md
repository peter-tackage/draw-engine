Draw Engine
===========

A Java library with an accompanying set of tests to perform Secret Santa / Kris Kringle draws.

[![Build Status](https://travis-ci.org/peter-tackage/draw-engine.svg?branch=master)](https://travis-ci.org/peter-tackage/draw-engine)

The library allows for a multiple implementations of the draw mechanism via the `DrawEngine` interface.

There are currently two implementations:

  1.  BasicDrawEngine
  2.  RecursiveDrawEngine

All DrawEngine implementations should at least fulfil the tests in the `AbstractDrawEngineTest` class.

Refer to `BasicDrawEngineTest` as an example for testing a specific implementation.
