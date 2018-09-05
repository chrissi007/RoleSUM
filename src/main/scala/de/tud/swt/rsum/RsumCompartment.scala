package de.tud.swt.rsum

import scroll.internal.MultiCompartment
import scala.collection.mutable.ListBuffer
import de.tud.swt.rsum.roles.IRelationRole
import scroll.internal.Compartment
import de.tud.swt.rsum.compartments.IRelationCompartment

object RsumCompartment extends MultiCompartment {

  protected var extensions = ListBuffer[IExtensionCompartment]()
  protected var activeViews = ListBuffer[IViewCompartment]()
  
  //all existing relations also non active ones
  protected var allViews = ListBuffer[IViewCompartment]()

  //elements in the RSUM
  protected var allRelations = ListBuffer[IRelationCompartment]()
  protected var allNaturals = ListBuffer[Object]()
  
  def printStatus (): Unit = {
    println("****************************************")
    println("Naturals: " + allNaturals.size + " N: " + allNaturals)
    println("Relations: " + allRelations.size + " R: " + allRelations)
    println("****************************************")
  }
  
  def containsRunningView(view: IViewCompartment): Boolean = activeViews.contains(view)

  def createViewWithName(name: String): IViewCompartment = {
    //schauen ob es überhaupt ansicht mit diesem Namen gibt
    allViews.foreach { v =>
      if (v.getViewName().equals(name)) {
        return this.createView(v)
      }
    }
    println("View with name %s is not known!", name)
    return null
  }

  def addViewToList(view: IViewCompartment): IViewCompartment = {
    //schauen ob es überhaupt ansicht mit diesem Namen gibt
    allViews.foreach { v =>
      if (v.getViewName().equals(view.getViewName())) {
        println("View with name is already known!")
        return v
      }
    }
    allViews = allViews :+ view
    return view
  }

  def addAndCreateView(view: IViewCompartment): IViewCompartment = {
    return this.createView(this.addViewToList(view))
  }

  def deleteView(view: IViewCompartment): Boolean = {
    if (view == null)
      return false    
    view.deleteAllRoles()
    activeViews -= view
    return true
  }

  private def createView(view: IViewCompartment): IViewCompartment = {
    //schaue ob schon Ansicht mit gleichem Namen aktiv dann nicht mehr erstellen
    //TODO: should be possible to create 2 views with same name
    activeViews.foreach { v =>
      if (v.getViewName().equals(view.getViewName())) {
        println("Two views with the same name can not be created")
        return null
      }
    }
    var newView: IViewCompartment = view.getNewInstance()
    activeViews = activeViews :+ newView
    //Implement view creation with newView    
    this combine newView
    allRelations.foreach { r =>
      var role: IViewRole = newView.getViewRole(r)
      //TODO: make this in view
      if (role != null) {
        r play role
      }
    }
    allNaturals.foreach { n =>
      //TODO: make this in view
      var role: IViewRole = newView.getViewRole(n)
      if (role != null) {
        n play role
      }
    }
    return newView
  }

  def addExtension(comp: IExtensionCompartment): Boolean = {
    extensions.foreach { e =>
      //Erweiterung schon integriert also ignorieren
      if (e.getExtensionName().equals(comp.getExtensionName())) {
        println("Their already exists an extension compartment with this name")
        return false
      }
    }
    extensions = extensions :+ comp
    //iteriere über alle Elemente des RSUMs und füge Extension an
    this combine comp
    allRelations.foreach { r =>
      //TODO: make this in extension
      var role: IExtensionRole = comp.getExtensionRole(r)
      if (role != null) {
        r play role
      }
    }
    allNaturals.foreach { n =>
      //TODO: make this in extension
      var role: IExtensionRole = comp.getExtensionRole(n)
      if (role != null) {
        n play role
      }
    }
    return true
  }

  def removeExtension(name: String): Boolean = {
    extensions.foreach { e =>
      //Erweiterung schon integriert also ignorieren
      if (e.getExtensionName().equals(name)) {
        e.deleteAllRoles()
        return true
      }
    }
    println("No extension with such a name!")
    return false
  }

  class RsumManager() {

    def manageRsum(incommingPlayer: Object): Unit = {

      if (incommingPlayer == null)
        return

      InstancesMiniUsecase.addElement(incommingPlayer)

      if (incommingPlayer.isInstanceOf[IRelationCompartment]) {
        //combine this and incomming one
        var relation = incommingPlayer.asInstanceOf[IRelationCompartment]
        relation.initialize()
        allRelations = allRelations :+ relation
        RsumCompartment.this combine relation
      } else {
        //Add to Natural List
        allNaturals = allNaturals :+ incommingPlayer
      }

      //Add all roles from extension compartments
      extensions.foreach { e =>
        var role: IExtensionRole = e.getExtensionRole(incommingPlayer)
        if (role != null) {
          this play role
        }
      }

      //Create new Elements in other views
      activeViews.foreach { v =>
        var role: IViewRole = v.getViewRole(incommingPlayer)
        if (role != null) {
          incommingPlayer play role
        }
      }      
    }

    def deleteEverything(): Unit = {
      var playerObj = this.player.right.get
      var roles = playerObj.roles()
      //Iterriere über alle Rollen falls es Rolle von Relationscompartment ist lösche dieses
      //println("**Deletion Roles Start: " + roles + " Delete: " + playerObj + " Count: " + roles.size)
      //println("All Count: " + plays.allPlayers.size)
      roles.foreach { r =>
        r.remove()
        if (r.isInstanceOf[IViewRole]) {
          //println("Delete these Roles first: " + r)
          var relationRole = r.asInstanceOf[IViewRole]
          relationRole.removeRole()
        }        
        if (r.isInstanceOf[IRelationRole]) {
          //println("Relation Role: " + r)
          var relationRole = r.asInstanceOf[IRelationRole]
          relationRole.deleteRelations()
        }
      }
      
      if (playerObj.isInstanceOf[IRelationCompartment]) {
        var relation = playerObj.asInstanceOf[IRelationCompartment]
        relation.deleteCompartment()
        allRelations -= relation
      } else {
        allNaturals -= playerObj
      }
      //println("All Count: " + plays.allPlayers.size)
    }

    def changeSomething(): Unit = {
      +this runExtension ()
    }
  }
}