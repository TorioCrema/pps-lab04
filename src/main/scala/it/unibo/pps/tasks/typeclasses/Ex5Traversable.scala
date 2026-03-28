package it.unibo.pps.tasks.typeclasses

import it.unibo.pps.u03.Sequences.Sequence, Sequence.*
import it.unibo.pps.u03.Optionals.Optional, Optional.*

/*  Exercise 5: 
 *  - Generalize by ad-hoc polymorphism logAll, such that:
 *  -- it can be called on Sequences but also on Optional, or others... 
 *  -- it does not necessarily call log, but any function with analogous type
 *  - Hint: introduce a type class Traversable[T[_]], capturing the ability of calling a
 *    "consumer function" on all elements (with type A) of a datastructure T[A] 
 *    Note Traversable is a 2-kinded trait (similar to Filterable, or Monad)
 *  - Write givens for Traversable[Optional] and Traversable[Sequence]
 *  - Show you can use the generalization of logAll to:
 *  -- log all elements of an Optional, or of a Traversable
 *  -- println(_) all elements of an Optional, or of a Traversable
 */

object Ex5Traversable:

  def log[A](a: A): Unit = println("The next element is: "+a)

  trait Traversable[T[_]]:
    def traverse[A](consume: A => Unit)(t: T[A]): Unit

  given Traversable[Optional] with
    def traverse[A](consume: A => Unit)(t: Optional[A]): Unit = t match
      case Just(a) => consume(a)
      case _ => ()

  given Traversable[Sequence] with
    def traverse[A](consume: A => Unit)(t: Sequence[A]): Unit = t match
      case Cons(h, tail) => consume(h); traverse(consume)(tail)
      case _ => ()

  def logAll[T[_]: Traversable, A](consume: A => Unit)(seq: T[A]): Unit =
    val traversable = summon[Traversable[T]]
    traversable.traverse(consume)(seq)

  @main def tryTraversable(): Unit =
    val seq: Sequence[Int] = Cons(10, Cons(20, Nil()))
    logAll(println)(seq)
    val option: Optional[String] = Just("Hello")
    logAll[Optional, String](log)(option)