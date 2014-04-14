Draw Engine
===========

Cloudbees Jenkins Status

[![Build Status](https://peter-tackage.ci.cloudbees.com/buildStatus/icon?job=draw-engine)](https://peter-tackage.ci.cloudbees.com/job/draw-engine/)

This is a Java library with an accompanying set of tests to perform Secret Santa / Kris Kringle draws.

The library allows for a multiple implementations of the draw mechanism via the DrawEngine interface.

There are currently two implementations:

  1.  BasicDrawEngine
  2.  RecursiveDrawEngine

All DrawEngine implementations should at least fulfil the tests in the AbstractDrawEngineTest class.

Refer to BasicDrawEngineTest as an example for testing a specific implementation.
