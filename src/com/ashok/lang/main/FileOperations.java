package com.ashok.lang.main;

import com.ashok.lang.inputs.InputReader;
import com.ashok.lang.inputs.Output;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Problem Name:
 * Link:
 *
 * @author Ashok Rajpurohit (ashok1113@gmail.com)
 */
public class FileOperations {
    public static void main(String[] args) throws IOException {
        FileOperations a = new FileOperations();
        a.solve();
    }

    private void solve() throws IOException {
        String path =
                "D:\\Java Projects\\karani\\src\\com\\ashok\\codejam\\";

        LinkedList<String> packagePath = new LinkedList<String>();
        packagePath.add("package com.ashok.codejam");

        File folder = new File(path);
        File[] files = folder.listFiles();

        for (File file : files)
            formatFolder(file, packagePath);
    }

    public static void formatFolderPackages(String folder, String rootPackage) throws IOException {
        File directory = new File(folder);
        LinkedList<String> pack = new LinkedList<>();
        pack.add("package " + rootPackage);

        formatFolder(directory, pack);
    }

    private static void formatFolder(File file, LinkedList<String> pathList) throws IOException {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = pathList.iterator();
        sb.append(iterator.next());

        while (iterator.hasNext()) {
            sb.append('.').append(iterator.next());
        }

        sb.append(';');

        if (!file.isDirectory()) {
            formatFile(file, sb.toString());
            return;
        }

        File[] files = file.listFiles();

        if (file != null)
            System.out.println(sb);

        pathList.add(file.getName());
        for (File f : files)
            formatFolder(f, pathList);

        pathList.removeLast();
    }

    private static void formatFile(File file, String packageString) throws IOException {
        if (!file.getName().contains(".java"))
            return;

        StringBuilder sb = new StringBuilder();
        System.out.println(packageString + " for file " + file.getName());

        sb.append(packageString).append('\n');

        InputReader input = new InputReader(file.getPath());
        String ps = input.readLine();
        if (!ps.contains("package"))
            sb.append(ps);

        while (input.hasNext())
            sb.append(input.nextChar());

        input.close();
        Output output = new Output(file);
        output.print(sb);
        output.close();
    }

    public static String classNameToPath(String className) {
        return className.replace('.', '\\');
    }
}
