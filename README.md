# Model Synchronization with the Role-oriented Single Underlying Model (Running Example)

Supplementary material for the [MRT Workshop 2018](http://modelsconference.org/) publication *"Model Synchronization with the Role-oriented Single Underlying Model"* by Christopher Werner and Uwe Aßmann.

The repository described the synchronization of 3 related models, which are derived from a single underlying model. Therefore, we use a small Libary and Employee example.

## Installation

Needs the [SCROLL Scala Library](https://github.com/max-leuthaeuser/SCROLL/) as dependency and named in the build.sbt document.

### Prerequisites

* Java SE Development Kit 8 or 9
* SBT (Scala Build Tool)
   * Version 0.13.* only with Java 1.8
   * from Version 1.* with Java 9
   * SBT sets its version in project/build.properties. Remove it if neccessary.

### Get a Copy of this Repository

git `clone https://github.com/st-tu-dresden/RoleSUM.git`

### Setup your favorite IDE

 - **IntelliJ**: use the built-in SBT importer.
 - **Eclipse**: use the sbteclipse SBT plugin.

## Running example

The [Rsum](src/main/scala/de/tud/swt/rsum) folder contains the source code of the running example:

* `ExampleRSUM.scala` as start class that show the creation of elements in the underlying model and the creation of views and changing of elements in views and the related views with console output.
* `RsumCompartment.scala` as global management object.
* Implemented views in the [Views](src/main/scala/de/tud/swt/rsum/views) folder.
