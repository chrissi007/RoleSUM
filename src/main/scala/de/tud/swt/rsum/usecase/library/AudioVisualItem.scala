package de.tud.swt.rsum.usecase.library

import java.util.Date

class AudioVisualItem (cpublicationDate: Date, ctitle: String, cminutesLength: Int, cdamaged: Boolean) extends CirculatingItem (cpublicationDate, ctitle) {
  var minutesLength: Int = cminutesLength;
  var damaged: Boolean = cdamaged;
}