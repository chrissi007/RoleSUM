package de.tud.swt.rsum.compartments

trait IComposition extends IDirectComposition {
  trait ICompositionSource extends IDirectCompositionSource {    
  }
  
  trait ICompositionTarget extends IDirectCompositionTarget {    
    def getSource (): ISource = IComposition.this.source
  }
}