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

class ImmutableListUnitTest {

    private val list = listOf("Hello", "World")

    @Test
    fun givenList_whenToImmutableList_thenReturnsImmutableList() {
        assertNotEquals("ImmutableList", list::class.simpleName)
        assertEquals("ImmutableList", list.toImmutableList()::class.simpleName)
        assertEquals("ImmutableList", immutableListOf("Hello", "World")::class.simpleName)
    }

    @Test
    fun givenEmpty_whenToImmutableList_thenReturnsEmptyList() {
        assertEquals("EmptyList", listOf<String>().toImmutableList()::class.simpleName)
        assertEquals("EmptyList", immutableListOf<String>()::class.simpleName)
    }

    @Test
    fun givenImmutableList_whenListIterator_thenReturnsImmutableIterators() {
        assertEquals("ImmutableListIterator", list.toImmutableList().listIterator()::class.simpleName)
        assertEquals("ImmutableListIterator", list.toImmutableList().listIterator(index = 1)::class.simpleName)
    }

    @Test
    fun givenList_whenToImmutableList_thenInitialListIsCopied() {
        val mutable = list.toMutableList()
        val immutable = mutable.toImmutableList()
        assertContentEquals(mutable, immutable)
        assertEquals(mutable, immutable)

        mutable.add("Something")
        assertNotEquals(mutable, immutable)
    }

    @Test
    fun givenImmutableList_whenSubList_thenReturnsImmutableList() {
        assertEquals("ImmutableList", list.toImmutableList().subList(0, list.size)::class.simpleName)
        assertEquals("ImmutableList", list.toImmutableList().subList(0, 1)::class.simpleName)
        assertEquals("EmptyList", list.toImmutableList().subList(0, 0)::class.simpleName)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableList_whenIterator_thenReturnsImmutableIterator() {
        assertEquals("ImmutableIterator", list.toImmutableList().iterator()::class.simpleName)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableList_whenEqualsHashCodeToString_thenIsSameAsUnderlying() {
        assertEquals(list, list.toImmutableList())
        assertContentEquals(list, list.toImmutableList())
        assertEquals(list.hashCode(), list.toImmutableList().hashCode())
        assertEquals(list.toString(), list.toImmutableList().toString())
    }

    // ImmutableCollection
    @Test
    fun givenImmutableList_whenSize_thenIsSameAsUnderlying() {
        assertEquals(list.size, list.toImmutableList().size)
    }

    // ImmutableCollection
    @Test
    fun givenImmutableList_whenContainsAll_thenIsSameAsUnderlying() {
        assertEquals(list.containsAll(list), list.toImmutableList().containsAll(list))
        assertEquals(list.containsAll(listOf("false")), list.toImmutableList().containsAll(listOf("false")))
        assertEquals(list.contains("false"), list.toImmutableList().contains("false"))
    }
}
