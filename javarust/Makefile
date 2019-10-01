build:
    mvn compile

rust:
    rustc processor.rs

run: rust build
  mvn exec:java -Dexec.mainClass="javarust.Processor"