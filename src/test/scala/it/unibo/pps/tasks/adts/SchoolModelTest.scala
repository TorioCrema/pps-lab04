package it.unibo.pps.tasks.adts

import it.unibo.pps.tasks.adts.SchoolModel.BasicSchoolModule.*
import it.unibo.pps.u03.extensionmethods.Sequences.Sequence.*
import org.junit.*
import org.junit.Assert.*

class SchoolModelTest:

  val school: School = emptySchool

  @Test def testTeacherFactory(): Unit =
    val john = teacher("John")

  @Test def testCourseFactory(): Unit =
    val math = course("Math")

  @Test def testEmptySchoolFactory(): Unit =
    assertEquals(Nil(), emptySchool.courses)

  @Test def testSetTeacherToCourse(): Unit =
    val oneCourseSchool = school.setTeacherToCourse(teacher("John"), course("Math"))
    assertEquals(Cons("Math", Nil()), oneCourseSchool.courses)
    val twoCourseSchool = oneCourseSchool.setTeacherToCourse(teacher("John"), course("English"))
    assertEquals(Cons("English", Cons("Math", Nil())), twoCourseSchool.courses)
    val duplicateCourseSchool = oneCourseSchool.setTeacherToCourse(teacher("Mark"), course("Math"))
    assertEquals(Cons("Math", Nil()), duplicateCourseSchool.courses)

  @Test def testCoursesOfATeacher(): Unit =
    val oneCourseSchool = school.setTeacherToCourse(teacher("John"), course("Math"))
    assertEquals(Cons("Math", Nil()), oneCourseSchool.coursesOfATeacher("John"))
    val twoCourseSchool = oneCourseSchool.setTeacherToCourse(teacher("John"), course("English"))
    assertEquals(Cons("English", Cons("Math", Nil())), twoCourseSchool.coursesOfATeacher("John"))

  @Test def testHasTeacher(): Unit =
    assertFalse(school.hasTeacher("John"))
    val oneCourseSchool = school.setTeacherToCourse(teacher("John"), course("Math"))
    assertTrue(oneCourseSchool.hasTeacher("John"))

  @Test def testHasCourse(): Unit =
    assertFalse(school.hasCourse("Math"))
    val oneCourseSchool = school.setTeacherToCourse(teacher("John"), course("Math"))
    assertTrue(oneCourseSchool.hasCourse("Math"))