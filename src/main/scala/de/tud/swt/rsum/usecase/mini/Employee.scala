package de.tud.swt.rsum.usecase.mini

class Employee(_firstName: String, _lastName: String, _salary: Double) extends Person(_firstName, _lastName) {

  private var salary: Double = _salary

  def getSalary(): Double = salary

  def setSalary(s: Double): Unit = {
    salary = s
  }

  override def toString(): String = {
    return "Employee: " + getFirstName() + " " + getLastName();
  }
}