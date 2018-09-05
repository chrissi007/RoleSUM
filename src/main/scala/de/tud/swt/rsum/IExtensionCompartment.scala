package de.tud.swt.rsum

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer

trait IExtensionCompartment extends Compartment {
  
  protected var roles = ListBuffer[IExtensionRole]()
  
  protected def getRole(classname: Object) : IExtensionRole
  
  def getExtensionRole(classname: Object) : IExtensionRole = {
    var role: IExtensionRole = this.getRole(classname)
    if (role != null)
      roles = roles :+ role
    return role
  }
  
  def getNewInstance() : IExtensionCompartment
  
  def getExtensionName() : String
  
  def deleteAllRoles() : Unit = {
    roles.foreach { r =>
      r.remove()
    }
  }
}