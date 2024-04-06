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
@file:JvmName("Immutable")

package io.matthewnelson.immutable.collections

import kotlin.jvm.JvmName

@JvmName("listOf")
public fun <T> Collection<T>.toImmutableList(): List<T> {
    if (isEmpty()) return emptyList()
    if (this is ImmutableList<T>) return this
    return ImmutableList(toList())
}

@JvmName("listOf")
public fun <T> immutableListOf(vararg elements: T): List<T> {
    if (elements.isEmpty()) return emptyList()
    return ImmutableList(elements.toList())
}

@JvmName("mapOf")
public fun <K, V> Map<K, V>.toImmutableMap(): Map<K, V> {
    if (isEmpty()) return emptyMap()
    if (this is ImmutableMap<K, V>) return this
    return ImmutableMap(toMap())
}

@JvmName("mapOf")
public fun <K, V> immutableMapOf(vararg pairs: Pair<K, V>): Map<K, V> {
    if (pairs.isEmpty()) return emptyMap()
    return ImmutableMap(pairs.toMap())
}

@JvmName("setOf")
public fun <T> Collection<T>.toImmutableSet(): Set<T> {
    if (isEmpty()) return emptySet()
    if (this is ImmutableSet<T>) return this
    return ImmutableSet(toSet())
}

@JvmName("setOf")
public fun <T> immutableSetOf(vararg elements: T): Set<T> {
    if (elements.isEmpty()) return emptySet()
    return ImmutableSet(elements.toSet())
}

private open class ImmutableCollection<T, D: Collection<T>>(
    protected val delegate: D
): Collection<T> {
    final override val size: Int get() = delegate.size
    final override fun isEmpty(): Boolean = delegate.isEmpty()
    final override operator fun iterator(): Iterator<T> = ImmutableIterator(delegate.iterator())
    final override fun containsAll(elements: Collection<T>): Boolean = delegate.containsAll(elements)
    final override operator fun contains(element: T): Boolean = delegate.contains(element)

    final override fun equals(other: Any?): Boolean = delegate == other
    final override fun hashCode(): Int = delegate.hashCode()
    final override fun toString(): String = delegate.toString()
}

private class ImmutableList<T>(
    delegate: List<T>
): ImmutableCollection<T, List<T>>(delegate), List<T> {
    override operator fun get(index: Int): T = delegate[index]
    override fun indexOf(element: T): Int = delegate.indexOf(element)
    override fun lastIndexOf(element: T): Int = delegate.lastIndexOf(element)
    override fun listIterator(): ListIterator<T> = ImmutableListIterator(delegate.listIterator())
    override fun listIterator(index: Int): ListIterator<T> = ImmutableListIterator(delegate.listIterator(index))
    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        if (fromIndex == 0 && toIndex == size) return this
        val subList = delegate.subList(fromIndex, toIndex)
        if (subList.isEmpty()) return emptyList()
        return ImmutableList(subList)
    }
}

private class ImmutableSet<T>(
    delegate: Set<T>
): ImmutableCollection<T, Set<T>>(delegate), Set<T>

private class ImmutableMap<K, V>(
    private val delegate: Map<K, V>
): Map<K, V> {

    override val entries: Set<Map.Entry<K, V>> by lazy {
        val entries = delegate.entries
        val set = LinkedHashSet<ImmutableMapEntry<K, V>>(entries.size, 1.0F)
        entries.mapTo(set) { ImmutableMapEntry(it) }
        ImmutableSet(set)
    }
    override val keys: Set<K> by lazy { ImmutableSet(delegate.keys) }
    override val size: Int get() = delegate.size
    override val values: Collection<V> by lazy { ImmutableCollection(delegate.values) }
    override fun isEmpty(): Boolean = delegate.isEmpty()
    override operator fun get(key: K): V? = delegate[key]
    override fun containsValue(value: V): Boolean = delegate.containsValue(value)
    override fun containsKey(key: K): Boolean = delegate.containsKey(key)

    override fun equals(other: Any?): Boolean = delegate == other
    override fun hashCode(): Int = delegate.hashCode()
    override fun toString(): String = delegate.toString()
}

private class ImmutableMapEntry<K, V>(
    private val delegate: Map.Entry<K, V>,
): Map.Entry<K, V> {
    override val key: K get() = delegate.key
    override val value: V get() = delegate.value

    override fun equals(other: Any?): Boolean = delegate == other
    override fun hashCode(): Int = delegate.hashCode()
    override fun toString(): String = delegate.toString()
}

private open class ImmutableIterator<T, D: Iterator<T>>(
    protected val delegate: D
): Iterator<T> {
    final override operator fun hasNext(): Boolean = delegate.hasNext()
    final override operator fun next(): T = delegate.next()

    final override fun equals(other: Any?): Boolean = delegate == other
    final override fun hashCode(): Int = delegate.hashCode()
    final override fun toString(): String = delegate.toString()
}

private class ImmutableListIterator<T>(
    delegate: ListIterator<T>
): ImmutableIterator<T, ListIterator<T>>(delegate), ListIterator<T> {
    override fun hasPrevious(): Boolean = delegate.hasPrevious()
    override fun nextIndex(): Int = delegate.nextIndex()
    override fun previous(): T = delegate.previous()
    override fun previousIndex(): Int = delegate.previousIndex()
}
