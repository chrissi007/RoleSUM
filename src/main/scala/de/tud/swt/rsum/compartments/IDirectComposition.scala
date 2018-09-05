package de.tud.swt.rsum.compartments

trait IDirectComposition extends IRelationCompartment {
  
  override def deleteCompartment () : Unit = {
    //println("DeleteCompartment")
    if (source != null)
    {
      source.remove()
      source = null;
    }
    if (target != null)
    {
      var player = target.player
      if (player.isRight) {
        var realPlayer = player.right.get
        target.remove()
        target = null;
        +realPlayer deleteEverything()
      }      
    }
    //+this deleteEverything()
  }
  
  trait IDirectCompositionSource extends ISource {
    override def deleteRelations (): Unit = +IDirectComposition.this deleteEverything()
  }
  
  trait IDirectCompositionTarget extends ITarget {
    override def deleteRelations (): Unit = +IDirectComposition.this deleteEverything()
    //override def deleteRelations (): Unit = IDirectComposition.this.deleteCompartment()
  }
}