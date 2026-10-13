# Concurrent Order Processor

A Java application that processes customer order files and produces itemized receipts plus an overall sales summary. It can run orders sequentially or process them concurrently, making it useful for exploring threading and shared-state management.

## Features

- Load item prices from a catalog and quantities from individual order files.
- Process an adjustable number of orders in **single-threaded or multi-threaded mode**.
- Use `Runnable` workers, Java `Thread` objects, and `join()` to coordinate completion.
- Synchronize updates to shared quantity/revenue totals and order-result collections.
- Write per-customer totals and aggregate item/revenue summaries to an output file.
- Print elapsed processing time for the selected execution mode.

## Code structure

- [`OrdersProcessor.java`](src/processor/OrdersProcessor.java) — command-line prompts, thread setup, coordination, and report writing.
- [`OrderTask.java`](src/processor/OrderTask.java) — per-file order processing and synchronized aggregate updates.
- [`ItemCatalog.java`](src/processor/ItemCatalog.java) — item catalog and price lookups.
- [`OrderResult.java`](src/processor/OrderResult.java) — per-customer order information.

## Build and try it

Requires a JDK. From this project directory on Linux/macOS/WSL:

```bash
javac -d out $(find src -name '*.java')
cd examples
java -cp ../out processor.OrdersProcessor
```

For the included two-order example, respond to the prompts with:

```text
Items file: items.txt
Multiple threads: y
Number of orders: 2
Order base filename: sampleOrder
Results filename: results.txt
```

The program reads `sampleOrder1.txt` and `sampleOrder2.txt` and writes `results.txt` in `examples/`. Choose `n` instead of `y` to try sequential execution.

**Technical focus:** Java OOP, collections, file I/O, concurrency, locks, and deterministic report formatting.

> University of Maryland Java coursework. Assignment specifications or starter interfaces may have been provided by the course.
