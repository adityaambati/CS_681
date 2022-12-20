package edu.umb.cs681.hw12;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class FileSystem {

    private static FileSystem fileSystem = null;
    public LinkedList<Directory> rootFS;

    private FileSystem() {
        this.rootFS = new LinkedList<Directory>();
    }

    public static FileSystem getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new FileSystem();
        }
        return fileSystem;
    }

    public LinkedList<Directory> getRootDirs() {
        return rootFS;
    }

    public void appendRootDir(Directory dir) {
    	rootFS.add(dir);
    }

    public static void main(String[] args) {
		  Directory root;
		  Directory apps;
		  Directory bin;
		  Directory home;
		  Directory pictures;
		  File x;
		  File y;
		  File a;
		  File b;
		  File c;
		  Link l1;
		  Link l2;
        FileSystem systemRoot = FileSystem.getFileSystem();
		root = new Directory(null, "root", 0, LocalDateTime.now());
		apps = new Directory(root, "apps", 0, LocalDateTime.now());
		bin = new Directory(root, "bin", 0, LocalDateTime.now());
		home = new Directory(root, "home", 0, LocalDateTime.now());
		pictures = new Directory(home, "pictures", 0, LocalDateTime.now());
		x = new File(apps, "x", 34, LocalDateTime.now());
		y = new File(bin, "y", 11, LocalDateTime.now());
		a = new File(pictures, "a", 55, LocalDateTime.now());
		b = new File(pictures, "b", 21, LocalDateTime.now());
		c = new File(home, "c", 45, LocalDateTime.now());
		l1 = new Link(home, "l1", 0, LocalDateTime.now(), bin);
		l2 = new Link(pictures, "l2", 0, LocalDateTime.now(), y);
		root.appendChild(apps);
		root.appendChild(bin);
		root.appendChild(home);
		apps.appendChild(x);
		apps.appendChild(y);
		home.appendChild(pictures);
		home.appendChild(c);
		home.appendChild(l1);
		pictures.appendChild(a);
		pictures.appendChild(b);
		pictures.appendChild(l2);
		
        systemRoot.appendRootDir(root);
		systemRoot.appendRootDir(pictures);
        systemRoot.appendRootDir(apps);
        systemRoot.appendRootDir(bin);
        systemRoot.appendRootDir(home);

        // create 14 threads
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i =0; i< 14; i++) {
            threads.add(new Thread(() -> {
                FileSystem fs = FileSystem.getFileSystem();
                fs.getRootDirs().forEach((Directory d) -> {
                    System.out.println(d.getTotalSize());
                });
                System.out.println(systemRoot.getFileSystem().hashCode());
                systemRoot.getRootDirs().forEach((Directory d) -> {
                    System.out.println(d.getName());
                });
                // files names in all the directories
                fs.getRootDirs().forEach((Directory d) -> {
                    d.getFiles().forEach((File f) -> {
                        System.out.println(f.getName());
                    });
                });

                // count files in all the directories
                fs.getRootDirs().forEach((Directory d) -> {
                    System.out.println(d.countChildren());
                });

            }));
        }
        
        for(int i = 0; i < 14; i++) {
        	threads.get(i).start();
        }
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread t : threads) {
            t.interrupt();
            try{
            t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
  

}
