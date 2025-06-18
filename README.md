# MinTrianglePath

The application uses sbt-native-packager to build a native executable.

After cloning the repository, run `sbt universal:packageBin`.
It's going to create a file `target/universal/mintrianglepath-0.1.0-SNAPSHOT.zip`.
Unzip the file and run `mintrianglepath-0.1.0-SNAPSHOT/bin/mintrianglepath` (or .bat for windows).

Example usage with passing a file contents:

```
➜  MinTrianglePath git:(main) ✗ cat ~/data-sets/data_small.txt | target/universal/mintrianglepath-0.1.0-SNAPSHOT/bin/mintrianglepath 
[    1] 1
[    2] 1 2
[    3] 1 2 3
[    4] 1 2 3 4
[    5] 1 2 3 4 5
[    6] 1 2 3 4 5 6
[    7] 1 2 3 4 5 6 7
[    8] 1 2 3 4 5 6 7 8
[    9] 1 2 3 4 5 6 7 8 9
[   10] 1 2 3 4 5 6 7 8 9 10
 ...
[   50] ...
Minimal path is: 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 = 50
```

It is also possible to run the the application from intellij. Simply run Main class. You can type as many rows as you want. The application will calculate minimal path when you say "stop".