package de.tud.swt.rsum.views

import de.tud.swt.rsum.IViewCompartment
import de.tud.swt.rsum.usecase.mini.EmployeeHasManager
import de.tud.swt.rsum.usecase.mini.Employee
import de.tud.swt.rsum.IViewRole
import scala.collection.mutable.ListBuffer
import de.tud.swt.rsum.RsumCompartment

class EmployeesManagerView extends IViewCompartment {
  
  protected def isViewable(classname: Object): Boolean = {
    if (classname.isInstanceOf[Employee] || classname.isInstanceOf[EmployeeHasManager])
      return true
    return false
  }

  protected def getNaturalRole(classname: Object): IViewRole = {
    var result: IViewRole = null
    if (classname.isInstanceOf[Employee]) {
      result = new EmployeeRole(null, null, 0.0)
    }
    return result
  }

  protected def getRelationalRole(classname: Object, sourceRole: IViewRole, targetRole: IViewRole): IViewRole = {
    var result: IViewRole = null
    if (classname.isInstanceOf[EmployeeHasManager]) {
      result = new HasManagerRole(sourceRole.asInstanceOf[EmployeeRole], targetRole.asInstanceOf[EmployeeRole])
    }
    return result
  }  

  def getNewInstance(): IViewCompartment = new EmployeesManagerView()

  def getViewName(): String = "EmployeesManagerView"
  
  def createEmployee(firstName: String, lastName: String, salary: Double): EmployeeRole = {
    return new EmployeeRole(firstName, lastName, salary)
  }

  def createHasManager(emp: EmployeesManagerView#EmployeeRole, man: EmployeesManagerView#EmployeeRole): HasManagerRole = {
    //Rollen dürfen nicht doppelt sein
    roles.foreach { r =>
      if (r.isInstanceOf[HasManagerRole]) {
        var relation: HasManagerRole = r.asInstanceOf[HasManagerRole]
        if (relation.getManager().equals(man) && relation.getEmployee().equals(emp)) {
          return null
        }
      }
    }
    if (roles.contains(man) && roles.contains(emp)) {
      return new HasManagerRole(emp, man)
    }
    return null
  }

  class EmployeeRole(firstName: String, lastName: String, salary: Double) extends IViewRole {

    var manager: HasManagerRole = null

    initialize()

    override def initialize(): Unit = {
      var emp: Employee = new Employee(firstName, lastName, salary)
      initElement(emp, this)
    }

    def setManager(h: HasManagerRole): Unit = {
      manager = h
    }

    def getSalaryView(): Double = {
      return +this getSalary ()
    }

    def setSalaryView(s: Double): Unit = {
      +this setSalary (s)
      +this changeSomething ()
    }

    def getFirstNameView(): String = {
      return +this getFirstName ()
    }

    def setFirstNameView(s: String): Unit = {
      +this setFirstName (s)
      +this changeSomething ()
    }

    def getLastNameView(): String = {
      return +this getLastName ()
    }

    def setLastNameView(s: String): Unit = {
      +this setLastName (s)
      +this changeSomething ()
    }

    def deleteElement(): Unit = {
      +this deleteEverything ()
    }
    
    override def printViewRoles(): Unit = {
      EmployeesManagerView.this.printViewRoles()
    }
    
    override def toString(): String = {
      return getViewName() + " REmp: " + getFirstNameView() + " " + getLastNameView();
    }
    
    override def removeRole(): Unit = {
      EmployeesManagerView.this.removeRoleFromList(this)
    }
  }

  class HasManagerRole(employee: EmployeesManagerView#EmployeeRole, manager: EmployeesManagerView#EmployeeRole) extends IViewRole {

    initialize()

    override def initialize(): Unit = {
      //prüfen ob beide ein Natural haben
      var natMan: Employee = manager.player.right.get.asInstanceOf[Employee]
      var natEmp: Employee = employee.player.right.get.asInstanceOf[Employee]
      //Starte Erstellung
      var empManRel: EmployeeHasManager = new EmployeeHasManager(natEmp, natMan)
      initElement(empManRel, this)
    }

    def getEmployee(): EmployeesManagerView#EmployeeRole = employee

    def getManager(): EmployeesManagerView#EmployeeRole = manager

    def deleteElement(): Unit = {
      +this deleteEverything ()
    }
    
    override def printViewRoles(): Unit = {
      EmployeesManagerView.this.printViewRoles()
    }

    override def toString(): String = {
      return getViewName() + " REmpMan";
    }
    
    override def removeRole(): Unit = {
      EmployeesManagerView.this.removeRoleFromList(this)
    }
  }

}