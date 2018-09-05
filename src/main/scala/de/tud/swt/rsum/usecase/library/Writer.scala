package de.tud.swt.rsum.usecase.library

import de.tud.swt.rsum.usecase.mini.Person

class Writer (cfirstName: String, clastName: String, cname: String) extends Person (cfirstName, clastName) {
  var name: String = cname;
}