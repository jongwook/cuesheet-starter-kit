CueSheet Starter Kit
====================

This project provides the skeleton code to get up and running with [CueSheet](https://github.com/kakao/cuesheet). The source directory has only three files that you can start from:

| File | What it contains |
|------|------------------|
|[`src/main/scala/CueSheetStarter.scala`](src/main/scala/CueSheetStarter.scala) | Example Spark application |
| [`src/main/resources/application.conf`](src/main/resources/application.conf) | Spark and deployment nvironment configurations |
| [`src/main/resources/log4j.properties`](src/main/resources/log4j.properties) | Logging configurations |

Launching a [CueSheet](https://github.com/kakao/cuesheet) application is as simple as running `CueSheetStarter` as a JVM application, either by `sbt "run-main CueSheetStarter"` in the terminal or by right-clicking and launching `object CueSheetStarter` in your IDE.

Deploying to a YARN cluster
===

CueSheet provides two ways to import YARN configuration. The easier option, if you are using Cloudera Manager, is pasting the "Download Client Configuration" link at your YARN or Hive status page:

<img src="http://i.imgur.com/iFxvJda.png" width="500">

Note that you need to Hive configuration link if you are going to use Spark SQL on your Hive database. You can then replace `master = local` in `application.conf` with the download link prepended with "yarn:", like:

    master = "yarn:http://quickstart.cloudera:7180/cmf/services/11/client-config"

Another option is to save the configuration XML files under the resource directory. This does not require the Cloudera Manager website to be running, thus being more suitable for production use. If you downloaded the configuration files at `src/main/resources/cloudera/quickstart`, the `master` option in `application.conf` can be:

    master = "yarn:classpath:cloudera.quickstart"

You can specify `deploy.mode = cluster` in order to submit the application in the cluster mode --- the driver process will also run in the YARN cluster.

`application.conf` uses [the HOCON format](https://github.com/typesafehub/config/blob/master/HOCON.md), where you can specify deployment configurations like how many executors and how much memory to use, as well as any [Spark Configurations](http://spark.apache.org/docs/latest/configuration.html); refer to the comments in [application.conf](src/main/resources/application.conf) for detailed usage.

Going Further
=============

Developing CueSheet applications as a SBT project rather than in-browser notebooks enables you to use all features that IDE provides, including:

- Debugging using breakpoints (only in client mode)
- Code navigation and refactoring
- Static analysis
- Organizing codebase in multiple files and packages
- Easily importing other JVM dependencies

and many others. Refer to [CueSheet README](https://github.com/kakao/cuesheet) for more information.
