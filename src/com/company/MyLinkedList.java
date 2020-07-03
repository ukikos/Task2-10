package com.company;

import java.lang.reflect.Array;
import java.util.Iterator;

public class MyLinkedList<T> implements Iterable<T>{ //Односвязный список

    private class ListNode { //Элемент односвязного списка

        T value;       //Значение элемента спсика
        ListNode next; //Ссылка на следующий элемент списка

        ListNode(T value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        ListNode(T value) {
            this.value = value;
            this.next = null;
        }

        ListNode() {
            this(null, null);
        }
    }

    private ListNode head = null; //Ссылка на первый элемент списка
    private ListNode tail = null; //Ссылка на последний элемент списка
    private int count = 0;        //Кол-во элементов списка

    /**
     * Добавление элемента в конец списка.
     * @param value Значение элемента
     */
    public void addLast(T value) {
        if (head == null) {
            head = tail = new ListNode(value, null);
        } else {
            tail.next = new ListNode (value, null);
            tail = tail.next;
        }
        count++;
    }

    /**
     * Добавление элемента в конец списка.(если не указан индекс)
     * @param value Значение элемента
     */
    public void add(T value) {
        if (head == null) {
            head = tail = new ListNode(value, null);
        } else {
            tail.next = new ListNode (value, null);
            tail = tail.next;
        }
        count++;
    }

    /**
     * Добавление элемента в список по номеру элемента. (если индекс указан)
     * @param index Номер элемента
     * @param value Значение элемента
     */
    public void add(int index, T value) {
        if (index >= count) {
            add(value);
        } else {
            ListNode node = head;
            for (int i = 0; i < (index - 1) && node != null; i++) {
                node = node.next;
            }
            node.next = new ListNode(value, node.next);

            count++;
        }
    }

    /**
     * Добавление элемента в начало списка
     * @param value Значение элемента
     */
    public void addFirst(T value) {
        ListNode element = new ListNode(value);
        if (head == null) {
            head = tail = element;
        } else {
            element.next = head;
            head = element;
        }
        count++;
    }

    /**
     * Найти объект элемента по индексу
     * @param index Номер элемента
     * @return Объект элемента
     * @throws Exception ex
     */
    public ListNode nodeByIndex(int index) throws Exception {
        ListNode node = head;
        for (int i = 0; i < index && node != null; i++ ) {
            node = node.next;
        }
        if (node == null) {
            throw new Exception("Элемент не существует");
        } else {
            return node;
        }
    }

    /**
     * Получить значение первого элемента
     * @return Значение первого элемента
     * @throws Exception ex
     */
    public T getFirst() throws Exception {
         ListNode first = head;
         if (first == null) {
             throw new Exception("Список пуст");
         }
         return first.value;
    }

    /**
     * Получить значение последнего элемента
     * @return Значение последнего элемента
     * @throws Exception ex
     */
    public T getLast() throws Exception {
        ListNode last = tail;
        if (last == null) {
            throw new Exception("Список пуст");
        }
        return last.value;
    }

    /**
     * Получить значение элемента по индексу
     * @param index Номер элемента
     * @return Значение элемента
     * @throws Exception ex
     */
    public T getByIndex(int index) throws Exception {
        if (index < 0) {
            throw new Exception("Неверный индекс!");
        }
        if (head == null) {
            throw new Exception("Список пуст!");
        }
        ListNode node = head;
        for (int i = 0; i < index && node != null; i++ ) {
            node = node.next;
        } if (node == null) {
            throw new Exception("Элемент не существует");
        } else {
            return node.value;
        }
    }

    /**
     * Вернуть значение объекта элемента
     * @param node Объект элемента
     * @return Значение элемента
     */
    public T getByNode(ListNode node) {
        return node.value;
    }

    /**
     * Удалить первый элемент списка
     * @throws Exception ex
     */
    public void removeFirst() throws Exception {
        if (count < 1) {
            throw new Exception("Список пуст");
        }
        ListNode first = head;
        head = head.next;
        first.next = null;
        first.value = null;
        if (head == null) {
            this.tail = null;
        }
        count--;
    }

    /**
     * Удалить последний элемент списка
     * @throws Exception ex
     */
    public void removeLast() throws Exception {
        if (count < 1) {
            throw new Exception("Список пуст");
        }
        if (count == 1) {
            tail.value = null;
            head = tail = null;
        } else {
            ListNode last = tail;
            tail = nodeByIndex(count - 2);
            tail.next = null;
            last.value = null;
        }
        count--;
    }

    /**
     * Удалить элемент по индексу
     * @param index Номер элемента
     * @throws Exception ex
     */
    public void remove(int index) throws Exception {
        if (count < 1) {
            throw new Exception("Список пуст");
        }
        if (count == 1) {
            head = null;
            tail = null;
            count--;
        } else if (index == 0) {
            head = head.next;
            count--;
        } else {
            ListNode element = nodeByIndex(index);
            ListNode prevElement = nodeByIndex(index - 1);
            if (element == tail) {
                prevElement.next = null;
                tail = prevElement;
            } else {
                prevElement.next = element.next;
            }
            element.next = null;
            element.value = null;
            count--;
        }
    }

    public T[] toArrayInner(T... dummy) {
        Class runtimeType = dummy.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(runtimeType, count);

        ListNode node = head;
        for (int i = 0; i < result.length; i++) {
            result[i] = getByNode(node);
            node = node.next;
        }
        return result;
    }

    /**
     * Список в массив
     * @return array
     */
    public T[] toArray() {
        return toArrayInner();
    }

    /**
     * Итератор
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private ListNode curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                T res = curr.value;
                curr = curr.next;
                return res;
            }
        };
    }

    /**
     * Возвращает кол-во элементов списка
     * @return Кол-во элементов списка
     */
    public int getSize() {
        return count;
    }

    /**
     * Возвращает первый элемент списка
     * @return head
     */
    public ListNode getHead() {
        return head;
    }

    /**
     * Возвращает последний элемент списка
     * @return tail
     */
    public ListNode getTail() {
        return tail;
    }


    /**
     * Возвращает список, где вместо пары элементов записана их сумма.
     * @param list Входной список
     * @throws Exception ex
     */
    public void taskProcess(MyLinkedList<Integer> list) throws Exception {
        MyLinkedList<Integer> resultList = new MyLinkedList<>();
        int temp = 0;
        int resCount = 0;
        for (int i = 0; i < count; i++) {
            temp += list.getByIndex(i);
            if (i % 2 == 1) {
                resultList.add(temp);
                resCount++;
                temp = 0;
            }
            if ((i == count - 1) && i % 2 != 1) {
                resultList.add(temp);
                resCount++;
            }
        }
        list.head = resultList.getHead();
        list.tail = resultList.getTail();
        list.count = resCount;
    }

}
