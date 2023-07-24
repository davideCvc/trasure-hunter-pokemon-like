import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * RecordAccount
 */
public class RecordAccount implements List<Integer> {
    private String name;
    private LinkedList<Integer> values;

    public RecordAccount(String name) {
        this.name = name;
        this.values = new LinkedList<>();
    }

    public RecordAccount(String name, LinkedList<Integer> list) {
        this.name = name;
        this.values = list;
    }

    public String getName() {
        return this.name;
    }

    public LinkedList<Integer> getValues() {
        return this.values;
    }

    @Override
    public boolean equals(Object otherAccount) {
        return this.name.equals(((RecordAccount) otherAccount).name);
    }

    @Override
    public String toString() {
        return "[" + name + ", values=" + values + "]";
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.values.contains(o);
    }

    @Override
    public Iterator iterator() {
        return this.values.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.values.toArray();
    }

    @Override
    public Integer[] toArray(Object[] a) {
        return (Integer[]) this.values.toArray(a);
    }

    @Override
    public boolean add(Integer e) {
        return this.values.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.values.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return this.values.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return this.values.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return this.values.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return this.values.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return this.values.retainAll(c);
    }

    @Override
    public void clear() {
        this.values.clear();
    }

    @Override
    public Integer get(int index) {
        return this.values.get(index);
    }

    @Override
    public Integer set(int index, Integer element) {
        return this.values.set(index, element);
    }

    @Override
    public void add(int index, Integer element) {
        this.values.add(index, element);
    }

    @Override
    public Integer remove(int index) {
        return this.values.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.values.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.values.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return this.values.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return this.values.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return this.values.subList(fromIndex, toIndex);
    }
}