package de.tud.swt.rsum

import de.tud.swt.rsum.usecase.mini.Employee
import de.tud.swt.rsum.usecase.mini.Library
import de.tud.swt.rsum.usecase.mini.EmployeeHasManager
import de.tud.swt.rsum.usecase.mini.LibraryHasEmployees

object InstancesMiniUsecase {
  
  var employees = List[Employee]()
  var libraries = List[Library]()
  var managerRelation = List[EmployeeHasManager]()
  var employeeRelation = List[LibraryHasEmployees]()
    
  def addElement(obj: Object) {
    if (obj == null)
      return
    if (obj.isInstanceOf[Employee])
      this.addEmployee(obj.asInstanceOf[Employee])
    if (obj.isInstanceOf[Library])
      this.addLibrary(obj.asInstanceOf[Library])
    if (obj.isInstanceOf[EmployeeHasManager])
      this.addManagerRelation(obj.asInstanceOf[EmployeeHasManager])
    if (obj.isInstanceOf[LibraryHasEmployees])
      this.addEmployeeRelation(obj.asInstanceOf[LibraryHasEmployees])
  }
  
  private def addEmployee(v: Employee): Unit = {
    if (!employees.contains(v))
      employees = employees :+ v
  }
  
  private def addLibrary(v: Library): Unit = {
    if (!libraries.contains(v))
      libraries = libraries :+ v
  }
  
  private def addManagerRelation(v: EmployeeHasManager): Unit = {
    if (!managerRelation.contains(v))
       managerRelation = managerRelation :+ v
  }
  
  private def addEmployeeRelation(v: LibraryHasEmployees): Unit = {
    if (!employeeRelation.contains(v))
      employeeRelation = employeeRelation :+ v
  }
}