package de.tud.swt.rsum

import scroll.internal.Compartment
import scala.collection.mutable.ListBuffer
import de.tud.swt.rsum.RsumCompartment.RsumManager
import de.tud.swt.rsum.roles.IRelationRole
import de.tud.swt.rsum.compartments.IRelationCompartment

trait IViewCompartment extends Compartment {

  private var inInitiProcess: Boolean = false
  private var initRelationRole: Boolean = false
  protected var roles = ListBuffer[IViewRole]()

  def getViewRole(classname: Object): IViewRole = {
    if (!RsumCompartment.containsRunningView(this) || inInitiProcess) {
      return null
    }

    if (!isViewable(classname)) {
      return null
    }

    //schauen ob es schon eine Rolle dieses Elements in dieser Ansicht gibt
    var role: IViewRole = getRoleFromList(classname)
    if (role == null) {
      //Element noch nicht in Liste        
      if (classname.isInstanceOf[IRelationCompartment]) {
        var comp: IRelationCompartment = classname.asInstanceOf[IRelationCompartment]
        if (getRoleFromList(comp) != null) {
          return null
        }
        var source = comp.getSource()
        var target = comp.getTarget()
        var sourcePlayer = source.player.right.get
        var targetPlayer = target.player.right.get
        var sourceRole: IViewRole = getRoleFromList(source)
        var targetRole: IViewRole = getRoleFromList(target)        
        initRelationRole = true
        if (sourceRole == null) {
          sourceRole = getNaturalRole(sourcePlayer)
          roles = roles :+ sourceRole
          sourcePlayer play sourceRole
        }
        if (targetRole == null) {
          targetRole = getNaturalRole(targetPlayer)
          roles = roles :+ targetRole
          targetPlayer play targetRole
        }
        role = getRelationalRole(classname, sourceRole, targetRole)
        initRelationRole = false
      } else {
        initRelationRole = true
        role = getNaturalRole(classname)
        initRelationRole = false
      }
      //Falls neue Rolle erzeugt wurde füge sie hier hinzu
      if (role != null && !roles.contains(role))
        roles = roles :+ role
    }
    return role
  }

  protected def isViewable(classname: Object): Boolean
  protected def getNaturalRole(classname: Object): IViewRole
  protected def getRelationalRole(classname: Object, sourceRole: IViewRole, targetRole: IViewRole): IViewRole

  protected def getRoleFromList(classname: Object): IViewRole = {
    roles.foreach { r =>
      if (+r == +classname) {
        return r
      }
    }
    return null
  }

  def printViewRoles(): Unit = {
    println("-------------------------------------------------------------------")
    println("Number of roles in the view: " + roles.size)
    roles.foreach { r =>
      var player = r.player.right.get
      var roles = player.roles()
      println("**Player: " + player + " --- Role: " + r + + " Size: " + roles.size)
    }
    println("-------------------------------------------------------------------")
  }

  def getNewInstance(): IViewCompartment

  def getViewName(): String

  /**
   * Should be only possible to call from RsumCompartment object
   */
  def deleteAllRoles(): Unit = {
    roles.foreach { r =>
      r.remove()
    }
  }

  protected def removeRoleFromList(role: IViewRole): Unit = {
    roles -= role
  }

  protected def initElement(element: Object, role: IViewRole): Unit = {
    //proof if the actual view is in
    if (!initRelationRole) {
      if (!RsumCompartment.containsRunningView(this)) {
        return
      }
      inInitiProcess = true
      var rsumManager: RsumManager = new RsumManager()
      element play rsumManager
      rsumManager.manageRsum(element)
      roles = roles :+ role
      element play role
      inInitiProcess = false
    }
  }

  /*protected def rolePlayerEquality(role1: Object, role2: Object): Boolean = {
    //return +role1 == +role2 
    //get the players
    var player1 = role1.player.right.get
    var player2 = role2.player.right.get
    println("E1: " + player1.equals(player2) + " E2: " + (+role1 == +role2) + " E3: " + (+role1 == role2) + " E4: " + (role1 == +role2) + " || P1: " + player1 + " P2: " + player2 + " R1: " + role1 + " R2: " + role2)
    return player1.equals(player2)
  }*/

  /*def deleteRole(role: IViewRole): Unit = {
    var roles = plays.getRoles(role)
    //Iterriere über alle Rollen falls es Rolle von Relationscompartment ist lösche dieses
    roles.foreach { r =>
      plays.removePlayer(r)
      if (r.isInstanceOf[IRelationRole] || r.isInstanceOf[IRelationRole]) {
        var relationRole = r.asInstanceOf[IRelationRole]
        relationRole.deleteRelations()
      }
    }
  }*/
}