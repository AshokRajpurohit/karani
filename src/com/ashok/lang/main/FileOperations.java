package com.ashok.lang.main;

import com.ashok.lang.inputs.ExcelReader;
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
    private static Output out;
    private static ExcelReader in;
    private static String path =
            "D:\\Java Projects\\karani\\src\\com\\ashok\\lang\\neustar\\";

    public static void main(String[] args) throws IOException {
//        in = new ExcelReader(path + "output.txt");
//        out = new Output(path + "output.txt");


//        String path =
//                "D:\\Java Projects\\karani\\src\\com\\ashok\\lang\\main\\", input =
        //            "input_file.in", output = "output_file.out";
        //        in = new InputReader(path + input);
        //        out = new Output(path + output);

        FileOperations a = new FileOperations();
        a.solve();
        in.close();
//        try {
//            a.solve();
//        } catch (IOException ioe) {
//         do nothing
//        }
        out.close();
    }

    private void solve() throws IOException {
        path =
                "D:\\Java Projects\\karani\\src\\com\\ashok\\spoj\\";

        LinkedList<String> packagePath = new LinkedList<String>();
        packagePath.add("package com.ashok.spoj");

        String packageString = "package com.ashok.spoj;";

//        File org = new File(path + "template\\"), dest = new File(path + "Template\\");
//        System.out.println("renaming the file succeded?: " + org.renameTo(dest));

//        org.mkdir();
//        org.

//        System.out.println("Original File Name: " + org);
//        System.out.println("Finally file name: " + dest);

        File folder = new File(path);
        File[] files = folder.listFiles();

        for (File file : files)
//            System.out.println(file + " is directory: " + file.isDirectory());
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

//        if (file != null)
//            return;

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

    private static void printFile(File file) {
        System.out.println(file);

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        for (File f : file.listFiles())
            printFile(f);

        System.out.println();
    }
}
