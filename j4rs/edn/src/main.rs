extern crate j4rs;

use std::convert::TryFrom;
use j4rs::{JvmBuilder, ClasspathEntry, InvocationArg};

fn main() {
    // Create a JVM
    let entry = ClasspathEntry::new("/Users/julianaomiboeira/Documents/ffi/j4rs/edn/cheshire/target/uberjar/cheshire-0.1.0-SNAPSHOT-standalone.jar");
    let jvm = JvmBuilder::new()
            .classpath_entry(entry)
            .build()
            .unwrap();

    let str_instance = String::from("{\"hello\":\"world\"}");

     // Create a package edn instance
    let edn_instance = jvm.create_instance(
        "edn.core.cheshire",
        &Vec::new(),
    ).unwrap();

    let json_instance = jvm.invoke(
        &edn_instance,       // The String instance created above
        "parseJsonString",              // The method of the String instance to invoke
        &[InvocationArg::try_from(str_instance.clone()).unwrap()],            // The `InvocationArg`s to use for the invocation - empty for this example
    ).unwrap();

    // If we need to transform an `Instance` to Rust value, the `to_rust` should be called
    let rust_edn: String = jvm.to_rust(json_instance).unwrap();
    println!("Edn representation of {} is {}", str_instance, rust_edn);

}

