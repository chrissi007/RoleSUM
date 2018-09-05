package de.tud.swt.rsum

import scroll.internal.Compartment
import de.tud.swt.rsum.usecase.mini.Library
import de.tud.swt.rsum.usecase.mini.Employee
import de.tud.swt.rsum.views.CompleteView
import de.tud.swt.rsum.views.EmployeesManagerView
import de.tud.swt.rsum.views.LibrariesEmployeesView

object ExampleRSUM extends App {

  new Compartment {

    var completeName = "CompleteView"
    var libraryName = "LibrariesEmployeesView"
    var employeeName = "EmployeesManagerView"

    println("%%%%%%%%%%%%%%%%%%%%%% Add views to lists %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    RsumCompartment.addViewToList(new CompleteView)
    RsumCompartment.addViewToList(new EmployeesManagerView)
    RsumCompartment.addViewToList(new LibrariesEmployeesView)

    println("%%%%%%%%%%%%%%%%%%%%%% Create a view with all data %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    var view: CompleteView = RsumCompartment.createViewWithName(completeName).asInstanceOf[CompleteView]

    println("%%%%%%%%%%%%%%%%%%%%%% Create elements in the view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    var libRole = view.createLibrary("City Library")
    var empRole = view.createEmployee("Bill", "Smith", 45000)
    var manRole = view.createEmployee("Bob", "Jones", 60000)
    var hasMan = view.createHasManager(empRole, manRole)
    var hasEmp = view.createHasEmployee(libRole, empRole)
    
    view.printViewRoles()

    println("%%%%%%%%%%%%%%%%%%%%%% Activate other views %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    var view2: EmployeesManagerView = RsumCompartment.createViewWithName(employeeName).asInstanceOf[EmployeesManagerView]
    var view3: LibrariesEmployeesView = RsumCompartment.createViewWithName(libraryName).asInstanceOf[LibrariesEmployeesView]

    view.printViewRoles()
    view2.printViewRoles()
    view3.printViewRoles()

    println("%%%%%%%%%%%%%%%%%%%%%% Create a new relation %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    var hasEmp2 = view.createHasEmployee(libRole, manRole)

    println("%%%%%%%%%%%%%%%%%%%%%% Change the name of an employee in a view %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    empRole.setFirstNameView("Max")

    view.printViewRoles()
    view2.printViewRoles()
    view3.printViewRoles()

    //println("%%%%%%%%%%%%%%%%%%%%%% Print status %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
    //RsumCompartment.printStatus()

    println("%%%%%%%%%%%%%%%%%%%%%% Remove an employee %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")

    //libRole.deleteElement()
    empRole.deleteElement()
    //manRole.deleteElement()
    //hasMan.deleteElement()
    //hasEmp2.deleteElement()

    //println("%%%%%%%%%%%%%%%%%%%%%% Print status %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
    //RsumCompartment.printStatus()

    view.printViewRoles()
    view2.printViewRoles()
    view3.printViewRoles()
  }
}