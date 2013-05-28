Draw Engine
=============================

This is a Java library with an accompanying set of tests to perform Secret Santa / Kris Kringle draws.

The library allows for a multiple implementations of the draw mechanism via the DrawEngine interface.

The BasicDrawEngine class provides an example implementation. It's certainly not efficient or "good", but it works well enough.

All DrawEngine implementations should at least fullfil the tests in the AbstractDrawEngineTest class.

Refer to BasicDrawEngineTest as an example for testing a specific implementation.
