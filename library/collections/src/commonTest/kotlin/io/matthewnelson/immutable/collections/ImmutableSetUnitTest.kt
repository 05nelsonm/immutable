/*
 * Copyright (c) 2024 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package io.matthewnelson.immutable.collections

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ImmutableSetUnitTest {

    private val set = setOf("Hello", "World")

    @Test
    fun givenList_whenToImmutableSet_thenReturnsImmutableSet() {
        assertNotEquals("ImmutableSet", set::class.simpleName)
        assertEquals("ImmutableSet", set.toImmutableSet()::class.simpleName)
        assertEquals("ImmutableSet", immutableSetOf("Hello", "World")::class.simpleName)
    }

    @Test
    fun givenEmpty_whenToImmutableSet_thenReturnsEmptyList() {
        assertEquals("EmptySet", setOf<String>().toImmutableSet()::class.simpleName)
        assertEquals("EmptySet", immutableSetOf<String>()::class.simpleName)
    }

    @Test
    fun givenList_whenToImmutableList_thenInitialListIsCopied() {
        val mutable = set.toMutableSet()
        val immutable = mutable.toImmutableSet()
        assertEquals(mutable, immutable)

        mutable.add("Something")
        assertNotEquals(mutable, immutable)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableSet_whenIterator_thenReturnsImmutableIterator() {
        assertEquals("ImmutableIterator", set.toImmutableSet().iterator()::class.simpleName)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableSet_whenEqualsHashCodeToString_thenIsSameAsUnderlying() {
        assertEquals(set, set.toImmutableSet())
        assertEquals(set.hashCode(), set.toImmutableSet().hashCode())
        assertEquals(set.toString(), set.toImmutableSet().toString())
    }

    // ImmutableCollection
    @Test
    fun givenImmutableSet_whenSize_thenIsSameAsUnderlying() {
        assertEquals(set.size, set.toImmutableSet().size)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableSet_whenContainsAll_thenIsSameAsUnderlying() {
        assertEquals(set.containsAll(set), set.toImmutableSet().containsAll(set))
        assertEquals(set.containsAll(listOf("false")), set.toImmutableSet().containsAll(listOf("false")))
        assertEquals(set.contains("false"), set.toImmutableSet().contains("false"))
    }
}
