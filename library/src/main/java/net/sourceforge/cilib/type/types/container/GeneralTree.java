/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.type.types.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sourceforge.cilib.util.Cloneable;

/**
 * Implementation of the General Tree data structure.
 * @param <E> The {@linkplain Comparable} and {@linkplain Cloneable} type.
 */
public class GeneralTree<E extends Comparable<? super E> & Cloneable> extends AbstractTree<E> {

    private static final long serialVersionUID = 3453326928796685749L;
    private List<Tree<E>> subTrees;

    /**
     * Create an instance of a {@linkplain GeneralTree} with a {@code null} key value
     * and zero subtrees.
     */
    public GeneralTree() {
        key = null;
        subTrees = new ArrayList<Tree<E>>();
    }

    /**
     * Create an instance of a {@linkplain GeneralTree} with {@code element} defined
     * as the key value and an empty set of subtrees.
     * @param element The <code>element</code> to set as the key value for the {@linkplain GeneralTree}.
     */
    public GeneralTree(E element) {
        this.key = element;
        this.subTrees = new ArrayList<Tree<E>>();
    }

    /**
     * Copy constructor. Create a deep copy of the given {@linkplain GeneralTree}.
     * @param copy The {@linkplain GeneralTree} to copy.
     */
    @SuppressWarnings("unchecked")
    public GeneralTree(GeneralTree<E> copy) {
        this.key = (E) copy.key.getClone();
        this.subTrees = new ArrayList<Tree<E>>();

        for (Tree<E> tree : copy.subTrees) {
            this.subTrees.add(tree.getClone());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeneralTree<E> getClone() {
        return new GeneralTree<E>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (this.getClass() != obj.getClass())) {
            return false;
        }

        GeneralTree<?> other = (GeneralTree<?>) obj;

        return this.key.equals(other.key) && this.subTrees.equals(other.subTrees);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.key == null ? 0 : this.key.hashCode());
        hash = 31 * hash + (this.subTrees == null ? 0 : this.subTrees.hashCode());
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addSubTree(Tree<E> subtree) {
        if (subtree == null) {
            throw new IllegalArgumentException("Cannot add a null object as a child of a tree");
        }

        if (getKey() == null) {
            throw new IllegalStateException("Cannot add a subtree to a tree with a null for the key value");
        }

        this.subTrees.add(subtree);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean add(E element) {
        Tree<E> subTree = new GeneralTree<E>(element);
        return addSubTree(subTree);
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        this.subTrees.clear();
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains(E element) {
        for (Tree<E> e : this.subTrees) {
            if (e.getKey().equals(element)) {
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove(E element) {
        for (Tree<E> tree : this.subTrees) {
            if (tree.getKey().equals(this.key)) {
                this.subTrees.remove(tree);
                return true;
            }
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public E remove(int index) {
        if (index >= this.subTrees.size()) {
            throw new IndexOutOfBoundsException("Invalid index provided for sub tree: " + index);
        }

        return this.subTrees.remove(index).getKey();
    }

    /**
     * {@inheritDoc}
     */
    public Tree<E> removeSubTree(E element) {
        int count = -1;
        for (Tree<E> subtree : this.subTrees) {
            count++;
            if (subtree.getKey().equals(element)) {
                break;
            }
        }

        if (count >= 0) {
            return removeSubTree(count);
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Tree<E> removeSubTree(int index) {
        if (index >= this.subTrees.size()) {
            throw new IndexOutOfBoundsException("Invalid index provided for sub tree: " + index);
        }

        return this.subTrees.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    public Tree<E> getSubTree(E element) {
        for (Tree<E> tree : this.subTrees) {
            if (tree.getKey().equals(element)) {
                return tree;
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        throw new UnsupportedOperationException("Implementation needed");
    }

    /**
     * {@inheritDoc}
     */
    public Tree<E> getSubTree(int index) {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        return this.subTrees.get(index);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isLeaf() {
        return this.subTrees.size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    public int getDegree() {
        return this.subTrees.size();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
