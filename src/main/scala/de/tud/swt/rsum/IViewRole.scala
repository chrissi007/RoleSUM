package de.tud.swt.rsum

trait IViewRole {
  protected def initialize(): Unit
  
  def printViewRoles(): Unit
  
  def removeRole(): Unit
}