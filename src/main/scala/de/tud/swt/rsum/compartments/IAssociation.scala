package de.tud.swt.rsum.compartments

trait IAssociation extends IDirectAssoziation {
  trait IAssociationSource extends IDirectAssoziationSource {
  }
  
  trait IAssociationTarget extends IDirectAssoziationTarget {    
    def getSource (): ISource = IAssociation.this.source
  }
}