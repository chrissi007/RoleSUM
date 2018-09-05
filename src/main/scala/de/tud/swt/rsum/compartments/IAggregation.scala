package de.tud.swt.rsum.compartments

trait IAggregation extends IDirectAggregation {
  trait IAggregationSource extends IDirectAggregationSource {
  }
  
  trait IAggregationTarget extends IDirectAggregationTarget {
    def getSource (): ISource = IAggregation.this.source
  }
}