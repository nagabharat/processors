package org.clulab.odin.impl

import org.clulab.processors.Document
import org.clulab.struct.DirectedGraph

trait Dependencies {
  def dependencies(sent: Int, doc: Document): DirectedGraph[String] =
    // NOTE: the .dependencies method first tries collapsed dependencies. If not available, it backs off to basic deps
    doc.sentences(sent).dependencies match {
      case None => sys.error("sentence has no dependencies")
      case Some(deps) => deps
    }

  def incomingEdges(sent: Int, doc: Document): Array[Array[(Int, String)]] =
    dependencies(sent, doc).incomingEdges

  def outgoingEdges(sent: Int, doc: Document): Array[Array[(Int, String)]] =
    dependencies(sent, doc).outgoingEdges

  def incoming(tok: Int, sent: Int, doc: Document): Array[String] = {
    val edges = incomingEdges(sent, doc)
    if (edges isDefinedAt tok) edges(tok).map(_._2) else Array.empty
  }

  def outgoing(tok: Int, sent: Int, doc: Document): Array[String] = {
    val edges = outgoingEdges(sent, doc)
    if (edges isDefinedAt tok) edges(tok).map(_._2) else Array.empty
  }
}
