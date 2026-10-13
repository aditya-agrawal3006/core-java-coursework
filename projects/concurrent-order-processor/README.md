# Concurrent Order Processor

A Java application that reads item and order data, supports sequential or multithreaded processing, and produces per-order and aggregate results using synchronization.

## Technologies
- Java
- Standard JDK libraries

## Code
- Main implementation: [`src/processor/OrdersProcessor.java`](src/processor/OrdersProcessor.java)

## Running locally

Compile and run from the repository root using a JDK:

```bash
javac -d out $(find src -name '*.java')
java -cp out processor.OrdersProcessor
```

The program reads item and order files from the working directory. A small example is provided in `examples/` so you can try it without course datasets.

## Project background

Developed as part of University of Maryland programming coursework. Some scaffolding or interfaces were provided with the assignment; this repository preserves the project structure for review.
