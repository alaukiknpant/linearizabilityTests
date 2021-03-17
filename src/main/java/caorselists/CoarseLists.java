package caorselists;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


// Adapted from - https://github.com/aditandadit/ConcurrentProgramming-Java/blob/master/miniproject_1/src/main/java/edu/coursera/concurrent/CoarseLists.java

/**
 * Wrapper class for two lock-based concurrent list implementations.
 */
public final class CoarseLists {
    /**
     * An implementation of the ListSet interface that uses Java locks to
     * protect against concurrent accesses.
     * <p>
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java ReentrantLock object
     * to protect against those concurrent accesses. You may refer to
     * SyncList.java for help understanding the list management logic, and for
     * guidance in understanding where to place lock-based synchronization.
     */
    public static final class CoarseList {

        public final class Entry {
            /**
             * The value stored in this list entry.
             */
            public final Integer object;

            /**
             * The next element in this singly linked list.
             */
            public Entry next;

            /**
             * The general constructor used when creating a new list entry.
             *
             * @param setObject Value to store in this item
             */
            Entry(final Integer setObject) {
                this.object = setObject;
            }
        }

        protected final Entry head;




        /*
         * TODO Declare a lock for this class to be used in implementing the
         * concurrent add, remove, and contains methods below.
         */

        private final ReentrantLock lock = new ReentrantLock();

        public CoarseList(Entry head) {
            this.head = new Entry(Integer.MIN_VALUE);
            this.head.next = new Entry(Integer.MAX_VALUE);
        }


        boolean add(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                lock.unlock();
            }
        }

        boolean remove(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                lock.unlock();
            }
        }

        boolean contains(final Integer object) {
            try {
                lock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                lock.unlock();
            }
        }
    }

}