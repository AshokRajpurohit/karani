package com.ashok.lang.dsa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This is implemantion of Ternary Search Tree (TST) Data Structure.
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 * @link https://en.wikipedia.org/wiki/Ternary_search_tree
 */
public class GenericTST<T> {
    private GenericTST left, right, equal;
    private boolean end = false;
    private Character ch;
    private int count = 0;
    private java.util.List<T> list = new LinkedList<>();
    private static final GenericTST<Object> INVALID_VALUE = new GenericTST<>("a", new Object());
    private static final List emptyList = Collections.unmodifiableList(new LinkedList<>());

    public GenericTST(String s, T t) {
        this(s, 0, t);
    }

    /**
     * initializes the instance.
     *
     * @param s   String s you want to initialize the instance.
     * @param pos start pos in string s to be inserted in instance.
     */
    private GenericTST(String s, int pos, T t) {
        if (pos == s.length())
            return;

        if (ch == null) {
            ch = s.charAt(pos);
            if (pos == s.length() - 1) {
                end = true;
                count++;
                list.add(t);
            } else
                equal = new GenericTST(s, pos + 1, t);
        } else {
            if (ch < s.charAt(pos)) {
                if (right == null)
                    right = new GenericTST(s, pos, t);
                else
                    right.add(s, pos);
            } else if (ch == s.charAt(pos)) {
                if (pos == s.length() - 1) {
                    end = true;
                    count++;
                } else if (equal == null) {
                    equal = new GenericTST(s, pos + 1, t);
                } else {
                    equal.add(s, pos + 1);
                }
            } else {
                if (left == null)
                    left = new GenericTST(s, pos, t);
                else
                    left.add(s, pos);
            }
        }
    }

    public boolean contains(String s) {
        return !find(s).isEmpty();
    }

    private GenericTST<T> find(String s, int pos) {
        if (pos == s.length())
            return this;

        if (s.charAt(pos) == this.ch) {
            pos++;
            if (pos == s.length())
                return this;
            if (equal == null)
                return (GenericTST<T>) INVALID_VALUE;
            return equal.find(s, pos);
        }

        if (s.charAt(pos) > ch) {
            if (right == null)
                return (GenericTST<T>) INVALID_VALUE;
            return right.find(s, pos);
        }

        if (left == null)
            return (GenericTST<T>) INVALID_VALUE;

        return left.find(s, pos);
    }

    public List<T> find(String s) {
        GenericTST<T> node = find(s, 0);
        if (node == INVALID_VALUE) return emptyList;
        return node.list;
    }

    /**
     * adds the given string to the TST structure.
     *
     * @param s
     */

    public void add(String s, T t) {
        this.add(s, 0, t);
    }

    /**
     * to add new string into existing TST instance.
     *
     * @param s   String s to be added to instance.
     * @param pos start char pos in String s.
     */
    private void add(String s, int pos, T t) {
        if (s.charAt(pos) == this.ch) {
            if (pos == s.length() - 1) {
                this.end = true;
                count++;
                list.add(t);
                return;
            } else {
                if (equal == null)
                    equal = new GenericTST(s, pos + 1, t);
                else
                    equal.add(s, pos + 1, t);
                return;
            }
        } else if (s.charAt(pos) < this.ch) {
            if (left == null) {
                left = new GenericTST(s, pos, t);
                return;
            } else
                left.add(s, pos, t);
        } else {
            if (right == null) {
                right = new GenericTST(s, pos, t);
                return;
            } else {
                right.add(s, pos, t);
                return;
            }
        }
        return;
    }

    public String toString() {
        return print();
    }

    /**
     * appends all the strings to sb lexicographical order.
     *
     * @param sb StringBuilder to which all the strings to be appended.
     */

    public void print(StringBuilder sb) {
        StringBuilder sbuf = new StringBuilder();
        print(sb, sbuf);
    }

    /**
     * returns all the strings in lexicographical order.
     *
     * @return all the strings in lexicographical order seperated by new line character.
     */

    public String print() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbuf = new StringBuilder();
        print(sb, sbuf);
        return sb.toString();
    }

    /**
     * print function adds all the strings in sorted order to StringBuilder sb.
     *
     * @param sb   StringBuilder to which all the strings to be appended.
     * @param sbuf used to get strings.
     */
    private void print(StringBuilder sb, StringBuilder sbuf) {
        int clen = sbuf.length();
        if (left != null) {
            left.print(sb, sbuf);
            sbuf.delete(clen, sbuf.length());
        }
        sbuf.append(ch);
        for (int i = 0; i < this.count; i++) {
            sb.append(sbuf).append('\n');
        }

        if (equal != null)
            equal.print(sb, sbuf);

        sbuf.deleteCharAt(clen);

        if (right != null) {
            right.print(sb, sbuf);
        }
    }

    public String[] toArrays() {
        ArrayList<String> al = new ArrayList<String>();
        StringBuilder sbuf = new StringBuilder();
        getList(al, sbuf);
        String[] res = new String[2];
        return al.toArray(res);
    }

    public ArrayList<String> toList() {
        ArrayList<String> al = new ArrayList<String>();
        StringBuilder sbuf = new StringBuilder();
        getList(al, sbuf);
        return al;
    }

    private void getList(ArrayList<String> al, StringBuilder sbuf) {
        int clen = sbuf.length();
        if (left != null) {
            left.getList(al, sbuf);
            sbuf.delete(clen, sbuf.length());
        }

        sbuf.append(ch);
        for (int i = 0; i < count; i++) {
            al.add(sbuf.toString());
        }

        if (equal != null)
            equal.getList(al, sbuf);

        sbuf.deleteCharAt(clen);

        if (right != null) {
            right.getList(al, sbuf);
        }
    }

    private void getList(List<T> li) {
        if (!list.isEmpty()) li.addAll(list);
        if (left != null) left.getList(li);
        if (right != null) right.getList(li);
        if (equal != null) equal.getList(li);
    }

    private List<T> findAllStartingWith(String name) {
        GenericTST<T> node = find(name, 0);
        LinkedList<T> values = new LinkedList<>();
        node.getList(values);
        return values;
    }

    public void remove(String s) {
        remove(s, 0);
        GenericTST temp = this;
        int i = 0;
        while (i < s.length() && temp != null) {
            if (temp.ch == s.charAt(i)) {
                if (i == s.length() - 1) {
                    if (temp.count > 0)
                        temp.count--;
                    else {
                        temp.left = null;
                        temp.equal = null;
                        temp.right = null;
                    }
                    return;
                }
                temp = temp.equal;
                i++;
            } else if (temp.ch < s.charAt(i))
                temp = temp.right;
            else
                temp = temp.left;
        }
    }

    private void remove(String s, int index) {
        if (index == s.length())
            return;
        this.contains(s);
    }

    public void addEf(String s) {
        GenericTST temp = this;
        int i = 0;
        while (i < s.length()) {
            if (temp.ch == s.charAt(i)) {
                if (i == s.length() - 1) {
                    temp.count++;
                    return;
                }
                if (temp.equal == null) {
                    temp.equal = new GenericTST(s, i + 1);
                    return;
                }
            } else if (s.charAt(i) < temp.ch) {
                if (temp.left == null) {
                    temp.left = new GenericTST(s, i);
                    return;
                } else
                    temp = temp.left;
            } else {
                if (temp.right == null) {
                    temp.right = new GenericTST(s, i);
                    return;
                } else
                    temp = temp.right;
            }
        }
        /*
        if (s.length() == 1) {
            if (temp.ch == s.charAt(0)) {
                temp.count++;
                return;
            }
            if (temp.ch < s.charAt(0)) {
                if (temp.left == null) {
                    temp.left = new TST(s, 0);
                }
            }
        }
        */
    }
}
