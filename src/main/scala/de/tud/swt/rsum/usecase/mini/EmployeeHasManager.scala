package de.tud.swt.rsum.usecase.mini

import de.tud.swt.rsum.compartments.IDirectAssoziation

class EmployeeHasManager(_emp: Employee, _man: Employee) extends IDirectAssoziation {
  
  override def internalInitialize(): Unit = {
    this.source = new Source()
    this.target = new Target()

    _emp play this.source
    _man play this.target
  }

  class Source() extends IDirectAssoziationSource {

  }

  class Target() extends IDirectAssoziationTarget {

  }
  
  override def toString(): String = {
    return "++RC++ EmployeeHasManager " + source + " | " + target;
  }
}