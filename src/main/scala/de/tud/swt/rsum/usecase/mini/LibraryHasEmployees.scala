package de.tud.swt.rsum.usecase.mini

import de.tud.swt.rsum.compartments.IDirectComposition
import de.tud.swt.rsum.RsumCompartment.RsumManager

class LibraryHasEmployees(_lib: Library, _emp: Employee) extends IDirectComposition {
  
  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()

    _lib play this.source
    _emp play this.target
  }

  class Source() extends IDirectCompositionSource {

  }

  class Target() extends IDirectCompositionTarget {

  }
  
  override def toString(): String = {
    return "++RC++ LibraryHasEmployees " + source + " | " + target;
  }
}