extern crate j4rs;
extern crate chrono;

use j4rs::JvmBuilder;
use chrono::Duration;

fn main() {
    // Create a JVM
    let jvm = JvmBuilder::new().build().unwrap();

    // Create a java.lang.String instance
    let string_instance = jvm.create_instance(
        "java.lang.String",
        &Vec::new(),
    ).unwrap();

    let boolean_instance = jvm.invoke(
        &string_instance,       // The String instance created above
        "isEmpty",              // The method of the String instance to invoke
        &Vec::new(),            // The `InvocationArg`s to use for the invocation - empty for this example
    ).unwrap();

    // If we need to transform an `Instance` to Rust value, the `to_rust` should be called
    let rust_boolean: bool = jvm.to_rust(boolean_instance).unwrap();
    println!("The isEmpty() method of the java.lang.String instance returned {}", rust_boolean);

    let _static_invocation_result = jvm.invoke_static(
        "java.lang.System",     // The Java class to invoke
        "currentTimeMillis",    // The static method of the Java class to invoke
        &Vec::new(),            // The `InvocationArg`s to use for the invocation - empty for this example
    );

    let rust_time: i64 = jvm.to_rust(_static_invocation_result.unwrap()).unwrap();
    let time = Duration::milliseconds(rust_time);

    println!("Years from 1970 {:?}", time.num_weeks() /  52);
}

