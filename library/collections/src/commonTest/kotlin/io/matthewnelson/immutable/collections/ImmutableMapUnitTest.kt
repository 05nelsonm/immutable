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
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ImmutableMapUnitTest {

    private val map = mapOf("Hello" to "World", "H" to "W")

    @Test
    fun givenMap_whenToImmutableMap_thenReturnsImmutableMap() {
        assertNotEquals("ImmutableMap", map::class.simpleName)
        assertEquals("ImmutableMap", map.toImmutableMap()::class.simpleName)
        assertEquals("ImmutableMap", immutableMapOf("Hello" to "World")::class.simpleName)
    }

    @Test
    fun givenEmpty_whenToImmutableMap_thenReturnsEmptyMap() {
        assertEquals("EmptyMap", mapOf<String, String>().toImmutableMap()::class.simpleName)
        assertEquals("EmptyMap", immutableMapOf<String, String>()::class.simpleName)
    }

    @Test
    fun givenMap_whenToImmutableMap_thenInitialMapIsCopied() {
        val mutable = map.toMutableMap()
        val immutable = mutable.toImmutableMap()
        assertEquals(mutable, immutable)
        mutable["aaa"] = "bbb"

        assertNotEquals(mutable, immutable)
    }

    @Test
    fun givenImmutableMap_whenEntries_thenAreImmutable() {
        val entries = map.toImmutableMap().entries
        assertEquals("ImmutableSet", entries::class.simpleName)
        assertEquals("ImmutableMapEntry", entries.first()::class.simpleName)
        assertEquals(map.entries, entries)
    }

    @Test
    fun givenImmutableMap_whenKeys_thenAreImmutable() {
        assertEquals("ImmutableSet", map.toImmutableMap().keys::class.simpleName)
    }

    @Test
    fun givenImmutableMap_whenValues_thenAreImmutable() {
        assertEquals("ImmutableCollection", map.toImmutableMap().values::class.simpleName)
    }

    @Test
    fun givenImmutableMap_whenEqualsHashCodeToString_thenIsSameAsUnderlying() {
        assertEquals(map, map.toImmutableMap())
        assertEquals(map.hashCode(), map.toImmutableMap().hashCode())
        assertEquals(map.toString(), map.toImmutableMap().toString())
    }

    @Test
    fun givenImmutableMap_whenSize_thenIsSameAsUnderlying() {
        assertEquals(map.size, map.toImmutableMap().size)
    }

    @Test
    fun givenImmutableMap_whenContainsKey_thenIsSameAsUnderlying() {
        assertEquals(map.containsKey("Hello"), map.toImmutableMap().containsKey("Hello"))
    }

    @Test
    fun givenImmutableMap_whenContainsValue_thenIsSameAsUnderlying() {
        assertEquals(map.containsKey("World"), map.toImmutableMap().containsKey("World"))
    }
}
