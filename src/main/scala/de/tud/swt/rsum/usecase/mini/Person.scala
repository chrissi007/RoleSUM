package de.tud.swt.rsum.usecase.mini

class Person(_firstName: String, _lastName: String) {
  
  private var firstName: String = _firstName
  private var lastName: String = _lastName

  def getFirstName(): String = firstName

  def getLastName(): String = lastName

  def setFirstName(f: String): Unit = {
    firstName = f
  }

  def setLastName(l: String): Unit = {
    lastName = l
  }
}