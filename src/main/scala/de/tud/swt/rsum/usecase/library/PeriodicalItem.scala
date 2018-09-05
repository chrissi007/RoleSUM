package de.tud.swt.rsum.usecase.library

import java.util.Date

class PeriodicalItem (cpublicationDate: Date, ctitle: String, cissuesPerYear: Int) extends Item (cpublicationDate, ctitle) {
  var issuesPerYear: Int = cissuesPerYear;
}