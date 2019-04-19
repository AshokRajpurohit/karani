package com.ashok.lang.concurrency;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.IOException;
import java.util.Arrays;

/**
 * Problem Name: Link:
 * 
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class TestSingleThreadInputOutput {
	private static final String path = "C:/Projects/karani/src/com/ashok/lang/template/";
	private static InputReader in = new InputReader();
	private static Output out;

	public static void main(String[] args) throws IOException {
		TestSingleThreadInputOutput a = new TestSingleThreadInputOutput();
		long time = System.currentTimeMillis();
		try {
			out = new Output(path + "Output1.out");
			in = new InputReader(path + "Input.in");
			a.solve();
		} catch (IOException ioe) {
			// do nothing
		}
		System.out.println(System.currentTimeMillis() - time);
		out.close();
	}

	private static void play() {

	}

	private void solve() throws IOException {
		int t = in.readInt();
		while (t > 0) {
			t--;

			int n = in.readInt();
			long[] ar = in.readLongArray(n);

			Arrays.sort(ar);
			int q = in.readInt();

			while (q > 0) {
				q--;

				long num = in.readLong();
				boolean found = ar[n >> 1] == num;
				out.println(found);
			}

		}
	}
}
