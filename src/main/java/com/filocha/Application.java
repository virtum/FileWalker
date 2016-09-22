// package com.filocha;
//
// import java.io.File;
// import java.util.Iterator;
//
// public class Application {
//
// public static void main(String[] args) {
//
// FileBranchAdapter root = new FileBranchAdapter(new File("c:/Temp/"));
//
// Iterable<Leaf> items = TreeUtil.convert(root);
// Iterator<Leaf> iterator = items.iterator();
//
// StreamUtils utils = new StreamUtils();
// utils.asStream(iterator)
// .forEach(it -> System.out.println(it.getName()));
// }
// }
