package com.ashok.codechef.marathon.year16.oct16;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Problem Name: Chef and Two String Link:
 * https://www.codechef.com/OCT16/problems/CHEFTWOS
 * 
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
class CHEFTWOS {
	private static PrintWriter out = new PrintWriter(System.out);
	private static InputReader in = new InputReader();
	private static final int mod = 1000000007;
	private static State[] states = new State[5];

	static {
		for (State state : State.values())
			states[state.a + state.b] = state;
	}

	public static void main(String[] args) throws IOException {
		solve();
		in.close();
		out.close();
	}

	private static void solve() throws IOException {
		int t = in.readInt();

		while (t > 0) {
			t--;

			out.println(process(in.read().toCharArray(), in.read()
					.toCharArray()));
		}
	}

	private static long process(char[] a, char[] b) {
		if (a.length == 1)
			return 2;

		long[] res = new long[5], temp = new long[5];

		res[charToInt(a[0])]++;
		res[charToInt(b[0])]++;

		int[] stateMap = new int[5], copyStateMap = new int[5];
		stateMap[charToInt(a[0])] = charToInt(b[0]);
		stateMap[charToInt(b[0])] = charToInt(a[0]);

		boolean first, second, both = false;
		for (int i = 1; i < a.length; i++) {
			both = false;
			for (int j = 1; j < 5; j++) {
				if (res[j] == 0)
					continue;

				int k = nextStateIndex(j, charToInt(a[i]));
				int k1 = nextStateIndex(stateMap[j], charToInt(b[i]));

				if (!both) {
					first = k == 1 || k == 2;
					second = k1 == 1 || k1 == 2;
					both = first && second;
				}

				boolean cont = k1 != 0 && k != 0;

				if (cont) {
					copyStateMap[k] = k1;
					temp[k] += res[j];

					if (temp[k] >= mod)
						temp[k] -= mod;
				}

				k = nextStateIndex(j, charToInt(b[i]));
				k1 = nextStateIndex(stateMap[j], charToInt(a[i]));

				if (!cont)
					cont = k != 0 && k1 != 0;

				if (!cont)
					return 0;

				cont = k != 0 && k1 != 0;

				if (cont) {
					copyStateMap[k] = k1;
					temp[k] += res[j];

					if (temp[k] >= mod)
						temp[k] -= mod;

				}

				if (both)
					continue;

				first = k == 1 || k == 2;
				second = k1 == 1 || k1 == 2;
				both = first && second;
			}

			if (invalid(temp))
				return 0;

			copy(temp, res);
			copy(copyStateMap, stateMap);
			clear(temp);
			clear(copyStateMap);
		}

		if (!both)
			return 0;

		return (res[1] + res[2]);
	}

	private static boolean invalid(long[] ar) {
		for (int i = 1; i < 5; i++)
			if (ar[i] != 0)
				return false;

		return true;
	}

	private static int charToInt(char ch) {
		if (ch == '1')
			return 1;

		return 2;
	}

	private static void clear(long[] ar) {
		for (int i = 0; i < ar.length; i++)
			ar[i] = 0;
	}

	private static void clear(int[] ar) {
		for (int i = 0; i < ar.length; i++)
			ar[i] = 0;
	}

	private static void copy(long[] source, long[] target) {
		for (int i = 0; i < source.length; i++)
			target[i] = source[i];
	}

	private static void copy(int[] source, int[] target) {
		for (int i = 0; i < source.length; i++)
			target[i] = source[i];
	}

	private static int nextStateIndex(int stateIndex, int value) {
		return State.stateMap[stateIndex][value];
	}

	/**
	 * Enum {@code State} has two state variables, a and b. if the string is
	 * ending at b then a is zero (to indicate the end state, although it is
	 * invalid value). If a is not zero then a and b represent the last two
	 * point states when b is not the end point. The valid states can be
	 * <p>
	 * (2, 1) when it is like (**221) and from b(1) the dog jumps to a(2).
	 * 
	 * (2, 2) when it is like (**22) and dog is at the first 2 and second 2 is
	 * still unvisited.
	 */
	enum State {
		ONE(0, 1), TWO(0, 2), THREE(2, 1), FOUR(2, 2), INVALID(0, 0);

		static int[][] stateMap = new int[5][3];

		static {
			for (State state : State.values()) {
				stateMap[state.hash()][1] = state.nextState(1).hash();
				stateMap[state.hash()][2] = state.nextState(2).hash();
			}
		}

		final int a, b; // state variables

		State(int a, int b) {
			this.a = a;
			this.b = b;
		}

		public int hash() {
			return a + b;
		}

		State nextState(int v) {
			if (v == 1) {
				switch (this) {
				case ONE:
					return ONE;
				case THREE:
					return ONE;
				case FOUR:
					return THREE;
				default:
					return INVALID;
				}
			}

			switch (this) {
			case ONE:
				return TWO;
			case TWO:
				return FOUR;
			case THREE:
				return TWO;
			default:
				return INVALID;
			}
		}
	}

	final static class InputReader {
		InputStream in;
		protected byte[] buffer = new byte[8192];
		protected int offset = 0;
		protected int bufferSize = 0;

		public InputReader() {
			in = System.in;
		}

		public void close() throws IOException {
			in.close();
		}

		public int readInt() throws IOException {
			int number = 0;
			int s = 1;
			if (offset == bufferSize) {
				offset = 0;
				bufferSize = in.read(buffer);
			}
			if (bufferSize == -1)
				throw new IOException("No new bytes");
			for (; buffer[offset] < 0x30 || buffer[offset] == '-'; ++offset) {
				if (buffer[offset] == '-')
					s = -1;
				if (offset == bufferSize - 1) {
					offset = -1;
					bufferSize = in.read(buffer);
				}
			}
			for (; offset < bufferSize && buffer[offset] > 0x2f; ++offset) {
				number = (number << 3) + (number << 1) + buffer[offset] - 0x30;
				if (offset == bufferSize - 1) {
					offset = -1;
					bufferSize = in.read(buffer);
				}
			}
			++offset;
			return number * s;
		}

		public String read() throws IOException {
			StringBuilder sb = new StringBuilder();
			if (offset == bufferSize) {
				offset = 0;
				bufferSize = in.read(buffer);
			}

			if (bufferSize == -1 || bufferSize == 0)
				throw new IOException("No new bytes");

			for (; buffer[offset] == ' ' || buffer[offset] == '\t'
					|| buffer[offset] == '\n' || buffer[offset] == '\r'; ++offset) {
				if (offset == bufferSize - 1) {
					offset = -1;
					bufferSize = in.read(buffer);
				}
			}
			for (; offset < bufferSize; ++offset) {
				if (buffer[offset] == ' ' || buffer[offset] == '\t'
						|| buffer[offset] == '\n' || buffer[offset] == '\r')
					break;
				if (Character.isValidCodePoint(buffer[offset])) {
					sb.appendCodePoint(buffer[offset]);
				}
				if (offset == bufferSize - 1) {
					offset = -1;
					bufferSize = in.read(buffer);
				}
			}
			return sb.toString();
		}
	}
}
