# MinTrianglePath

The application uses sbt-native-packager to build a native executable.

After cloning the repository, run `sbt universal:packageBin`.
It's going to create a file `target/universal/mintrianglepath-0.1.0-SNAPSHOT.zip`.
Unzip the file and run `mintrianglepath-0.1.0-SNAPSHOT/bin/mintrianglepath` (or .bat for windows).

Example usage with passing a file contents:

`➜  MinTrianglePath git:(main) ✗ cat ~/data-sets/really_small.txt | target/universal/mintrianglepath-0.1.0-SNAPSHOT/bin/mintrianglepath`

`Minimal path is: 1 + 3 + 2 + 3 = 9`

It is also possible to run the the application from intellij. Simply run Main class. You can type as many rows as you want. The application will calculate minimal path when you say "stop".