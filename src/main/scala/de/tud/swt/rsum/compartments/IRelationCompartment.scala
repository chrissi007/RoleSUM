package de.tud.swt.rsum.compartments

import scroll.internal.Compartment
import de.tud.swt.rsum.roles.IRelationRole

trait IRelationCompartment extends Compartment {
  
  protected var source: ISource = null;
  protected var target: ITarget = null;
  
  protected def internalInitialize(): Unit
  
  def getTarget():ITarget = target
  
  def getSource(): ISource = source
  
  def initialize(): Unit = {
    if (source == null && target == null)
      internalInitialize()
  }
  
  def deleteCompartment () : Unit = {    
    if (source != null)
    {
      source.remove()
      source = null;
    }
    if (target != null)
    {
      target.remove()
      target = null;
    }
    //+this deleteEverything()
  }
  
  trait ISource extends IRelationRole {    
    def getTarget (): ITarget = IRelationCompartment.this.target
    def deleteRelations (): Unit = +IRelationCompartment.this deleteEverything()
  }
  
  trait ITarget extends IRelationRole {    
    def deleteRelations (): Unit = +IRelationCompartment.this deleteEverything()
  }
}