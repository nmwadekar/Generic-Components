package nmw.core.concept;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LRUCache<E> {

	private Deque<E> deque = new LinkedList<E>();

	private int CACHE_SIZE;

	public LRUCache(int size) {

		super();

		CACHE_SIZE = size;
	}

	public static void main(String[] args) {

		LRUCache<LRUCache<?>.Person> r = new LRUCache<LRUCache<?>.Person>(5);

		for (int i = 0; i < 5; i++) {

			r.add(r.new Person(i, "NAME_" + i));
			System.out.println("inserting " + i);
		}

		r.fetch(r.new Person(9, "NAME_" + 9));

		System.out.println(r.deque);

		r.add(r.new Person(8, "NAME_" + 8));

		System.out.println(r.deque);

		r.add(r.new Person(3, "NAME_" + 3));
		r.add(r.new Person(2, "NAME_" + 2));
		r.add(r.new Person(8, "NAME_" + 8));
		r.add(r.new Person(9, "NAME_" + 9));

		System.out.println(r.deque);

	}

	private void add(E elementToAdd) {

		if (deque.contains(elementToAdd))
			deque.remove(elementToAdd);

		if (deque.size() >= CACHE_SIZE) {

			deque.removeLast();
		}

		deque.addFirst(elementToAdd);
	}

	private void fetch(E elementToFetch) {

		try {

			E y = deque.stream().filter(x -> x.equals(elementToFetch)).findFirst().get();

			if (y != null) {

				deque.remove(y);

				deque.push(y);
			}

		} catch (NoSuchElementException e) {

			System.err.println("\nElement not found - " + elementToFetch);
		}
	}

	private class Person {

		int id;
		String name;

		public Person(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		/*
		 * @Override public int hashCode() { final int prime = 31; int result =
		 * 1; result = prime * result + getOuterType().hashCode(); result =
		 * prime * result + id; result = prime * result + ((name == null) ? 0 :
		 * name.hashCode()); return result; }
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("unchecked")
			Person other = (Person) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id != other.id)
				return false;

			return true;
		}

		private LRUCache<?> getOuterType() {
			return LRUCache.this;
		}

		@Override
		public String toString() {
			return "Person [id=" + id + ", name=" + name + "]";
		}

	}
}
