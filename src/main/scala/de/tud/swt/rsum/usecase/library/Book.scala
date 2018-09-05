package de.tud.swt.rsum.usecase.library

import java.util.Date

class Book (cpublicationDate: Date, ctitle: String, cpages: Int, ccategory: String) extends CirculatingItem (cpublicationDate, ctitle) {
  var pages: Int = cpages;
  var category: String = ccategory;
}