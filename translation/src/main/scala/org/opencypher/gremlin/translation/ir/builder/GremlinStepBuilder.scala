/*
 * Copyright (c) 2018 "Neo4j, Inc." [https://neo4j.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencypher.gremlin.translation.ir.builder

import org.apache.tinkerpop.gremlin.process.traversal.{Scope, Order => TraversalOrder}
import org.apache.tinkerpop.gremlin.structure.Column
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import org.opencypher.gremlin.translation.ir.model._
import org.opencypher.gremlin.traversal.CustomFunction

import scala.language.implicitConversions

//noinspection TypeAnnotation
object GremlinStepBuilder {
  def __ = Seq.empty[GremlinStep]

  implicit def steps2builder(steps: Seq[GremlinStep]) = new {
    def V() = steps :+ Vertex
    def E() = steps :+ Edge
    def addE(edgeLabel: String) = steps :+ AddE(edgeLabel)
    def addV() = steps :+ AddV
    def addV(vertexLabel: String) = steps :+ AddV(vertexLabel)
    def aggregate(sideEffectKey: String) = steps :+ Aggregate(sideEffectKey)
    def and(andTraversals: Seq[GremlinStep]*) = steps :+ And(andTraversals: _*)
    def as(stepLabel: String) = steps :+ As(stepLabel)
    def barrier() = steps :+ Barrier
    def bothE(edgeLabels: String*) = steps :+ BothE(edgeLabels: _*)
    def by(traversal: Seq[GremlinStep]) = steps :+ By(traversal, None)
    def by(traversal: Seq[GremlinStep], order: TraversalOrder) = steps :+ By(traversal, Some(order))
    def cap(sideEffectKey: String) = steps :+ Cap(sideEffectKey)
    def choose(traversalPredicate: Seq[GremlinStep], trueChoice: Seq[GremlinStep], falseChoice: Seq[GremlinStep]) =
      steps :+ ChooseT(traversalPredicate, trueChoice, falseChoice)
    def choose(predicate: GremlinPredicate, trueChoice: Seq[GremlinStep], falseChoice: Seq[GremlinStep]) =
      steps :+ ChooseP(predicate, trueChoice, falseChoice)
    def choose(predicate: GremlinPredicate, trueChoice: Seq[GremlinStep]) = steps :+ ChooseP(predicate, trueChoice, Nil)
    def coalesce(coalesceTraversals: Seq[GremlinStep]*) = steps :+ Coalesce(coalesceTraversals: _*)
    def constant(e: Any) = steps :+ Constant(e)
    def count() = steps :+ Count
    def count(scope: Scope) = steps :+ CountS(scope)
    def dedup(dedupLabels: String*) = steps :+ Dedup(dedupLabels: _*)
    def drop() = steps :+ Drop
    def emit() = steps :+ Emit
    def flatMap(traversal: Seq[GremlinStep]) = steps :+ FlatMapT(traversal)
    def fold() = steps :+ Fold
    def from(fromStepLabel: String) = steps :+ From(fromStepLabel)
    def group() = steps :+ Group
    def has(propertyKey: String) = steps :+ Has(propertyKey)
    def has(propertyKey: String, predicate: GremlinPredicate) = steps :+ HasP(propertyKey, predicate)
    def hasKey(labels: String*) = steps :+ HasKey(labels: _*)
    def hasLabel(labels: String*) = steps :+ HasLabel(labels: _*)
    def hasNot(propertyKey: String) = steps :+ HasNot(propertyKey)
    def id() = steps :+ Id
    def identity() = steps :+ Identity
    def inE(edgeLabels: String*) = steps :+ InE(edgeLabels: _*)
    def inV() = steps :+ InV
    def inject(injections: Any*) = steps :+ Inject(injections)
    def is(predicate: GremlinPredicate) = steps :+ Is(predicate)
    def key() = steps :+ Key
    def label() = steps :+ Label
    def limit(limit: Long) = steps :+ Limit(limit)
    def local(traversal: Seq[GremlinStep]) = steps :+ Local(traversal)
    def loops() = steps :+ Loops
    def map(function: CustomFunction) = steps :+ MapF(function)
    def map(traversal: Seq[GremlinStep]) = steps :+ MapT(traversal)
    def math(expression: String) = steps :+ Math(expression)
    def max() = steps :+ Max
    def mean() = steps :+ Mean
    def min() = steps :+ Min
    def not(notTraversal: Seq[GremlinStep]) = steps :+ Not(notTraversal)
    def optional(optionalTraversal: Seq[GremlinStep]) = steps :+ Optional(optionalTraversal)
    def or(orTraversals: Seq[GremlinStep]*) = steps :+ Or(orTraversals: _*)
    def order() = steps :+ Order
    def otherV() = steps :+ OtherV
    def outE(edgeLabels: String*) = steps :+ OutE(edgeLabels: _*)
    def outV() = steps :+ OutV
    def path() = steps :+ Path
    def properties(propertyKeys: String*) = steps :+ Properties(propertyKeys: _*)
    def property(key: String, value: Any) = steps :+ PropertyV(key, value)
    def property(cardinality: Cardinality, key: String, value: Any) = steps :+ PropertyVC(cardinality, key, value)
    def property(key: String, traversal: Seq[GremlinStep]) = steps :+ PropertyT(key, traversal)
    def property(cardinality: Cardinality, key: String, traversal: Seq[GremlinStep]) =
      steps :+ PropertyTC(cardinality, key, traversal)
    def project(keys: String*) = steps :+ Project(keys: _*)
    def range(scope: Scope, low: Long, high: Long) = steps :+ Range(scope, low, high)
    def repeat(repeatTraversal: Seq[GremlinStep]) = steps :+ Repeat(repeatTraversal)
    def select(selectKeys: String*) = steps :+ SelectK(selectKeys: _*)
    def select(column: Column) = steps :+ SelectC(column)
    def sideEffect(sideEffectTraversal: Seq[GremlinStep]) = steps :+ SideEffect(sideEffectTraversal)
    def simplePath() = steps :+ SimplePath
    def skip(skip: Long) = steps :+ Skip(skip)
    def sum() = steps :+ Sum
    def times(maxLoops: Int) = steps :+ Times(maxLoops)
    def to(toStepLabel: String) = steps :+ To(toStepLabel)
    def unfold() = steps :+ Unfold
    def union(unionTraversals: Seq[GremlinStep]*) = steps :+ Union(unionTraversals: _*)
    def until(untilTraversal: Seq[GremlinStep]) = steps :+ Until(untilTraversal)
    def value() = steps :+ Value
    def valueMap() = steps :+ ValueMap
    def valueMap(includeTokens: Boolean) = steps :+ ValueMap(includeTokens)
    def values(propertyKeys: String*) = steps :+ Values(propertyKeys: _*)
    def where(whereTraversal: Seq[GremlinStep]) = steps :+ WhereT(whereTraversal)
    def where(predicate: GremlinPredicate) = steps :+ WhereP(predicate)
  }
}
