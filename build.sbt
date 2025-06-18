ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect"                   % "3.6.1",
  "org.typelevel" %% "cats-core"                     % "2.13.0",
  "org.typelevel" %% "cats-parse"                    % "1.0.0",
  "co.fs2"        %% "fs2-core"                      % "3.12.0",
  "co.fs2"        %% "fs2-io"                        % "3.12.0",
  "org.scalatest" %% "scalatest"                     % "3.2.19" % Test,
  "org.scalamock" %% "scalamock"                     % "6.1.1"  % Test,
  "org.typelevel" %% "cats-effect-testing-scalatest" % "1.6.0"  % Test
)

lazy val root = (project in file("."))
  .settings(
    name             := "MinTrianglePath",
    idePackagePrefix := Some("io.gt.mintrianglepath")
  )

mainClass in Compile := Some("io.gt.mintrianglepath.Main")
enablePlugins(JavaAppPackaging)