extern crate walkdir;

use std::env;
use std::path::{Path, PathBuf};

fn main() {
    // if cfg!(feature = "libjvm") {
        let libjvm_path = env::var("JAVA_HOME").ok().and_then(find_libjvm);
        println!("{:?}", libjvm_path);
        match libjvm_path {
            Some(path) => println!("cargo:rustc-link-search=native={}", path.display()),
            None => panic!("Failed to find libjvm.so. Try setting JAVA_HOME"),
        }
        println!("cargo:rustc-link-lib=dylib=jvm");
    // }
}

fn find_libjvm(path: impl AsRef<Path>) -> Option<PathBuf> {
    walkdir::WalkDir::new(path)
        .follow_links(true)
        .into_iter()
        .filter_map(Result::ok)
        .filter(|entry| entry.file_name().to_str().unwrap_or("") == java_lib_name())
        .filter_map(|entry| entry.path().parent().map(Into::into))
        .next()
}

// TODO(#15): support Android.
fn java_lib_name() -> &'static str {
    if cfg!(target_os = "linux") {
        "libjvm.so"
    } else if cfg!(target_os = "windows") {
        "jvm.dll"
    } else {
        "libjvm.dylib"
    }
}