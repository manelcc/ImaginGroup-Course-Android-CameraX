package utils


import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Assert
import org.mockito.Mockito
import utils.rest.ResponseMockJson
import java.io.FileNotFoundException
import org.hamcrest.Matchers.`is` as isA

val emptyString = ""


infix fun List<*>.assertSize(size: Int) {
    assertThat(this.size, CoreMatchers.`is`(size))
}

infix fun List<*>.contain(any: Any) {
    assertThat(this, CoreMatchers.hasItem(any))
}

infix fun List<*>.hasProperty(any: String) {
    assertThat(this, Matchers.everyItem(Matchers.hasProperty(any)))
}

infix fun List<*>.hasPropertyNotNullValue(any: String) {
    assertThat(this, Matchers.everyItem(Matchers.hasProperty(any, CoreMatchers.notNullValue())))
}

infix fun List<*>.notContain(any: Any) {
    assertThat(this, CoreMatchers.not(CoreMatchers.hasItem(any)))
}

infix fun Any.assertValue(any: Any) = assertThat(this, CoreMatchers.`is`(any))


infix fun Any.equalsTo(any: Any) = assertThat(this, CoreMatchers.equalTo(any))

infix fun String.containValue(any: String) = assertThat(this, CoreMatchers.containsStringIgnoringCase(any))

fun String.notNullValue() = assertThat(this, CoreMatchers.notNullValue())


infix fun Double.isGreaterThan(any: Double) = assertThat(this, isA(Matchers.greaterThan(any)))


infix fun Int.isGreaterThan(any: Int) {
    assertThat(this, isA(Matchers.greaterThan(any)))
}

infix fun Double.isGreaterThanEqualTo(any: Double) {
    assertThat(this, isA(Matchers.greaterThanOrEqualTo(any)))
}

infix fun Int.isGreaterThanEqualTo(any: Int) {
    assertThat(this, isA(Matchers.greaterThanOrEqualTo(any)))
}

infix fun Double.isLessThanEqualTo(any: Double) {
    assertThat(this, isA(Matchers.lessThanOrEqualTo(any)))
}

infix fun Int.isLessThanEqualTo(any: Int) {
    assertThat(this, isA(Matchers.lessThanOrEqualTo(any)))
}

infix fun Double.isLessThan(any: Double) {
    assertThat(this, isA(Matchers.lessThan(any)))
}

infix fun Int.isLessThan(any: Int) {
    assertThat(this, isA(Matchers.lessThan(any)))
}

infix fun Any.isInstanceOf(any: Any) {
    assertThat(this, CoreMatchers.instanceOf(this::class.java))
}

infix fun Any.sameInstance(any: Any) {
    Assert.assertSame(this, any)
}

inline fun <reified T : Any> givenMock(): T = Mockito.mock(T::class.java)

@Throws(FileNotFoundException::class)
inline fun <reified T : Any> givenJsonMock(file: String, body: Class<T>): T = ResponseMockJson().responseMock(file, body);

@Throws(FileNotFoundException::class)
inline fun <reified T : Any> givenJsonMockList(file: String, body: Class<T>): List<T> = ResponseMockJson().responseMockList(file, body)

