package com.ashok.lang.dsa;

import java.util.ArrayList;

/**
 * This is implemantion of Ternary Search Tree (TST) Data Structure.
 * @link https://en.wikipedia.org/wiki/Ternary_search_tree
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class GenericTST {
    private GenericTST left, right, equal;
    private boolean end = false;
    private Character ch;
    private int count = 0;

    public GenericTST(String s) {
        this(s, 0);
    }

    /**
     * initializes the instance with first string and then add the remaining
     * strings.
     * @param ar
     */

    public GenericTST(String[] ar) {
        this(ar[0], 0);
        for (int i = 1; i < ar.length; i++)
            this.add(ar[i], 0);
    }

    /**
     * initializes the instance.
     * @param s String s you want to initialize the instance.
     * @param pos   start pos in string s to be inserted in instance.
     */
    private GenericTST(String s, int pos) {
        if (pos == s.length())
            return;

        if (ch == null) {
            ch = s.charAt(pos);
            if (pos == s.length() - 1) {
                end = true;
                count++;
            } else
                equal = new GenericTST(s, pos + 1);
        } else {
            if (ch < s.charAt(pos)) {
                if (right == null)
                    right = new GenericTST(s, pos);
                else
                    right.add(s, pos);
            } else if (ch == s.charAt(pos)) {
                if (pos == s.length() - 1) {
                    end = true;
                    count++;
                } else if (equal == null) {
                    equal = new GenericTST(s, pos + 1);
                } else {
                    equal.add(s, pos + 1);
                }
            } else {
                if (left == null)
                    left = new GenericTST(s, pos);
                else
                    left.add(s, pos);
            }
        }
    }

    public boolean contains(String s) {
        return contains(s, 0);
    }

    private boolean contains(String s, int pos) {
        if (pos == s.length())
            return true;

        if (s.charAt(pos) == this.ch) {
            pos++;
            if (pos == s.length())
                return true;
            if (equal == null)
                return false;
            return equal.contains(s, pos);
        }

        if (s.charAt(pos) > ch) {
            if (right == null)
                return false;
            return right.contains(s, pos);
        }

        if (left == null)
            return false;

        return left.contains(s, pos);
    }

    /**
     * adds the given string to the TST structure.
     * @param s
     */

    public void add(String s) {
        this.add(s, 0);
    }

    /**
     * adds the array of strings to existing instance.
     * @param ar
     */

    public void add(String[] ar) {
        for (int i = 0; i < ar.length; i++)
            add(ar[i], 0);
    }

    /**
     * to add new string into existing TST instance.
     * @param s String s to be added to instance.
     * @param pos start char pos in String s.
     */
    private void add(String s, int pos) {
        if (s.charAt(pos) == this.ch) {
            if (pos == s.length() - 1) {
                this.end = true;
                count++;
                return;
            } else {
                if (equal == null)
                    equal = new GenericTST(s, pos + 1);
                else
                    equal.add(s, pos + 1);
                return;
            }
        } else if (s.charAt(pos) < this.ch) {
            if (left == null) {
                left = new GenericTST(s, pos);
                return;
            } else
                left.add(s, pos);
        } else {
            if (right == null) {
                right = new GenericTST(s, pos);
                return;
            } else {
                right.add(s, pos);
                return;
            }
        }
        return;
    }

    public String toString() {
        return print();
    }

    /**
     *  appends all the strings to sb lexicographical order.
     * @param sb    StringBuilder to which all the strings to be appended.
     */

    public void print(StringBuilder sb) {
        StringBuilder sbuf = new StringBuilder();
        print(sb, sbuf);
    }

    /**
     *  returns all the strings in lexicographical order.
     * @return  all the strings in lexicographical order seperated by new line character.
     */

    public String print() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbuf = new StringBuilder();
        print(sb, sbuf);
        return sb.toString();
    }

    /**
     *  print function adds all the strings in sorted order to StringBuilder sb.
     * @param sb    StringBuilder to which all the strings to be appended.
     * @param sbuf  used to get strings.
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
