package de.tud.swt.rsum.usecase.mini

class Library (_name: String) {
  
  private var name: String = _name

  def getName(): String = name

  def setName(n: String): Unit = {
    name = n
  }
  
  override def toString(): String = {
    return "Library: " + getName();
  }
}